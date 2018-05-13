package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import model.Reservation;

public class Database {
	
	private String dbAddress = "";
	private int dbPort = 0;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<BasicDBObject> guests;
	private MongoCollection<BasicDBObject> rooms;
	private MongoCollection<BasicDBObject> reservations;
	private MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
	private Logger logger;
	private MongoClientURI uri;
	
	public Database(String address, int port) {
		this.dbAddress = address;
		this.dbPort = port;
	}
	
	/**
	 * Initial set up of the database.
	 * @return
	 */
	public boolean setUpDatabase() {
		boolean connection = updateConnection();
		if (!connection) {
			return false;
		}
		return true;
	}

	/**
	 * Can be used to attempt to connect to newly
	 * provided IP
	 * @return
	 */
	public boolean updateConnection() {
		// Sets up timeout
		setUpOptions();
		// Creates URI string needed to connect to the database
		String uriString = "mongodb://";
		uriString = uriString + "admin"+":"+"group8" 
				+"@"+dbAddress+":"+dbPort;
		uri = new MongoClientURI(uriString, optionsBuilder);
		// Inits new MongoClient with earlier created URI
		client = new MongoClient(uri);
		// Used to lessen the spam in the console
		setUpLogger();
		// If timeout is reached, can't connect to the 
		// database
		try {
			client.getAddress();
		}
		catch (MongoTimeoutException e) {
			return false;
		}
		
		// Sets up collections
		setUpDb();
		return true;
	}
	
	public void setPort(int port) {
		this.dbPort = port;
	}
	
	public void setIp(String ip) {
		this.dbAddress = ip;
	}
	
	/**
	 * Creates new guest in the database
	 * @param guest
	 */
	public void writeGuest (model.Guest guest) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(guest));
		guests.insertOne(obj);
	}
	
	/**
	 * Creates new room in the database
	 * @param room
	 */
	public void writeRoom (model.Room room) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(room));
		rooms.insertOne(obj);
	}
	
	/**
	 * Creates new reservation in the database
	 * @param reservation
	 */
	public void writeReservation (model.Reservation reservation) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(reservation));
		reservations.insertOne(obj);
	}
	
	/**
	 * Updates room status (free, allocated, reserved)
	 * @param roomNr
	 * @param state
	 */
	public void updateRoomState (String roomNr, model.RoomState state) {
		Document old = new Document();
		// query to find old room (only one with that number can exist)
		old.put("roomNum", roomNr);
		Document newRoom = new Document ("$set", new Document("roomState", state.name()));
		rooms.updateOne(old, newRoom);
	}
	
	/**
	 * Updates guest's favorite room, but can be changed to updating
	 * any guest's information
	 * @param room
	 * @param guest
	 */
	public void updateGuestFavRoom(String room, model.Guest guest) {
		Document old = new Document();
		// Finding guest by name and credit card number (in case there are
		// several guests with same name)
		old.put("name", guest.getName());
		old.put("creditNumber", guest.getCreditNumber());
		// Updating favorite room
		Document newGuest = new Document("$set", new Document("favRoom", room));
		// Push update
		guests.updateOne(old, newGuest);
	}
	
	/**
	 * Reservation can have checkedIn set to true or false. This method
	 * updates this variable.
	 * @param res
	 */
	public void updateReservationState (model.Reservation res) {
		findReservations();
		Document old = new Document();
		old.put("guestName", res.getGuestName());
		old.put("room", res.getRoom());
		Document newRes = new Document("$set", new Document("checkedIn", true));
		reservations.updateOne(old, newRes);
	}
	
	/**
	 * Looks in the database for a guest with specified name
	 * @param name
	 * @return
	 */
	public ArrayList<model.Guest> findGuestByName(String name) {
		//Result array
		ArrayList<model.Guest> guestArray = new ArrayList<model.Guest>();
		//Query
	    FindIterable<BasicDBObject> cursor = guests.find(new Document("name", name));
	    //Iterator to go through query matching results
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		    while(it.hasNext()) {
		    	// Store found object in BasiDBObject with is Gson.
		    	// Then turn it into an object
			    BasicDBObject dbobj = it.next();
			    model.Guest foundGuest = (new Gson()).fromJson(dbobj.toString(), model.Guest.class);
			    guestArray.add(foundGuest);
	        }
	    }
	    finally {
	    	it.close();
	    }
		return guestArray;
	}
	
	/**
	 * Finds checked in reservations, for check-out functionality implementation
	 * @return
	 */
	public ArrayList<model.Reservation> findCheckedInReservations() {
		//Result array
		ArrayList<model.Reservation> reservationArr = new ArrayList<model.Reservation>();
		//Query
	    FindIterable<BasicDBObject> cursor = reservations.find(new Document("checkedIn", true));
	    //Iterator to go through query matching results
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		   while(it.hasNext()) {
		    	// Store found object in BasiDBObject with is Gson.
		    	// Then turn it into an object
			   BasicDBObject dbobj = it.next();
			   model.Reservation foundReservation = (new Gson()).fromJson(dbobj.toString(), model.Reservation.class);
			   reservationArr.add(foundReservation);
	       }
	    }
	    finally {
	    	it.close();
	    }
		return reservationArr;
		
	}
	
	/**
	 * Returns all rooms existing in the database
	 * @return
	 */
	public ArrayList<model.Room> findRooms() {
		// Result array
		ArrayList<model.Room> foundRooms = new ArrayList<model.Room>();
		// Query
	    FindIterable<BasicDBObject> cursor = rooms.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	// Store found object in BasiDBObject with is Gson.
	    	// Then turn it into an object
	    	BasicDBObject dbobj = it.next();
			model.Room foundRoom = (new Gson()).fromJson(dbobj.toString(), model.Room.class);
			foundRooms.add(foundRoom);			
	    }
	    
		return foundRooms;
	}
	
	/**
	 * Finds rooms by the number of room
	 * @param roomNum
	 * @return
	 */
	public ArrayList<model.Room> findRooms(String roomNum) {
		// Results
		ArrayList<model.Room> foundRooms = new ArrayList<model.Room>();
		// Query
	    FindIterable<BasicDBObject> cursor = rooms.find(new Document("roomNum", roomNum));
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	// Store found object in BasiDBObject with is Gson.
	    	// Then turn it into an object
	    	BasicDBObject dbobj = it.next();
			model.Room foundRoom = (new Gson()).fromJson(dbobj.toString(), model.Room.class);
			foundRooms.add(foundRoom);			
	    }
	    it.close();
	    
		return foundRooms;
	} 
	
	/**
	 * Finds all the guests in a database
	 * @return
	 */
	public ArrayList<model.Guest> findGuests() {
		// Result array
		ArrayList<model.Guest> foundGuests = new ArrayList<model.Guest>();
		// Query
	    FindIterable<BasicDBObject> cursor = guests.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	// Store found object in BasiDBObject with is Gson.
	    	// Then turn it into an object
	    	BasicDBObject dbobj = it.next();
			model.Guest foundGuest = (new Gson()).fromJson(dbobj.toString(), model.Guest.class);
			foundGuests.add(foundGuest);			
	    }
	    
		return foundGuests;
	} 
	
	/**
	 * Finds all the reservations in the database
	 * @return
	 */
	public ArrayList<model.Reservation> findReservations() {
		//Results
		ArrayList<model.Reservation> foundReservations = new ArrayList<model.Reservation>();
		//Query
	    FindIterable<BasicDBObject> cursor = reservations.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	// Store found object in BasiDBObject with is Gson.
	    	// Then turn it into an object
	    	BasicDBObject dbobj = it.next();
			model.Reservation foundReservation = (new Gson()).fromJson(dbobj.toString(), model.Reservation.class);
			foundReservations.add(foundReservation);			
	    }
	    
		return foundReservations;
	} 
	
	/**
	 * Finds all the reservations by a certain guest in the database
	 * @param guestName
	 * @return
	 */
	public ArrayList<Reservation> findReservationByName(String guestName) {
		// Results
		ArrayList<model.Reservation> resArray = new ArrayList<model.Reservation>();
		// Query
	    FindIterable<BasicDBObject> cursor = reservations.find(new Document("guestName", guestName));
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		   while(it.hasNext()) {
		    	// Store found object in BasiDBObject with is Gson.
		    	// Then turn it into an object
			   BasicDBObject dbobj = it.next();
			   model.Reservation reservation = (new Gson()).fromJson(dbobj.toString(), model.Reservation.class);
			   resArray.add(reservation);
	       }
	    }
	    finally {
	    	it.close();
	    }
		return resArray;
	}
	
	/**
	 * Finds all the reservations made for a certain room. Uses room number to find them.
	 * @param room
	 * @return
	 */
	public ArrayList<Reservation> findReservationByRoom(String room) {
		// Result
		ArrayList<model.Reservation> resArray = new ArrayList<model.Reservation>();
		// Query
	    FindIterable<BasicDBObject> cursor = reservations.find(new Document("room", room));
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
	    	// Store found object in BasiDBObject with is Gson.
	    	// Then turn it into an object
		    while(it.hasNext()) {
			    BasicDBObject dbobj = it.next();
			    model.Reservation reservation = (new Gson()).fromJson(dbobj.toString(), model.Reservation.class);
			    resArray.add(reservation);
	        }
	    }
	    finally {
	    	it.close();
	    }
		return resArray;
	}
	
	/**
	 * Deletes reservation from the database
	 * @param reservation
	 */
	public void deleteReservation(model.Reservation reservation) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(reservation));
		reservations.deleteOne(obj);
	}
	
	/**
	 * Helper method to set up collections
	 */
	private void setUpDb() {
		//Get hotel database
		database = client.getDatabase("hotel");
		// If database doesn't contain any collections,
		// initialize them
		if (isDbEmpty()) {
			database.createCollection("guests");
			database.createCollection("rooms");
			database.createCollection("reservations");
		}
		// Get collections
		guests = database.getCollection("guests", BasicDBObject.class);
		rooms = database.getCollection("rooms", BasicDBObject.class);
		reservations = database.getCollection("reservations", BasicDBObject.class);
	}
	
	/**
	 * Helper method to set up connection timeout (originally 10 seconds, so too long)
	 */
	private void setUpOptions(){
		// Makes sure it times out after 4 seconds of trying to connect to the server
		optionsBuilder.serverSelectionTimeout(4000);
	}
	
	/**
	 * Helper method to lessen console spam
	 */
	private void setUpLogger() {
		// Set to log only severe messages
		logger = Logger.getLogger("org.mongodb.driver");
		logger.setLevel(Level.SEVERE);
	}
	
	
	/**
	 * Check if database has not been initialized
	 * @return true if db is empty
	 */
	private boolean isDbEmpty() {
		return !(client.listDatabaseNames().iterator().hasNext());
	}

}


/**
 * Class used for sorting room list by the ir prices.
 */
class PriceComparator implements Comparator<model.Room> {

	@Override
	public int compare(model.Room arg0, model.Room arg1) {
		int price1 = Integer.parseInt(arg0.getPrice());
		int price2 = Integer.parseInt(arg1.getPrice());
		
		return price1 < price2? -1 : price1 == price2 ? 0 : 1;
	}
	
}
