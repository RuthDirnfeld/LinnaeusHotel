package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import model.Reservation;
import model.Room;

public class Database {
	
	private String dbAddress = "178.62.236.241";
	private int dbPort = 27017;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<BasicDBObject> guests;
	private MongoCollection<BasicDBObject> rooms;
	private MongoCollection<BasicDBObject> reservations;
	private MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
	private MongoClientOptions options;
	private Logger logger;
	
	public Database(String address, int port) {
		this.dbAddress = address;
		this.dbPort = port;
	}
	
	public boolean setUpDatabase() {
		setUpOptions();
		boolean connection = updateConnection();
		if (!connection) {
			return false;
		}
		return true;
	}

	public boolean updateConnection() {
		client = new MongoClient(new ServerAddress(dbAddress, dbPort), options);
		setUpLogger();
		try {
			client.getAddress();
		}
		catch (MongoTimeoutException e) {
			return false;
		}
		setUpDb();
		return true;
	}
	
	public void setPort(int port) {
		this.dbPort = port;
	}
	
	public void setIp(String ip) {
		this.dbAddress = ip;
	}
	
	public void writeGuest (model.Guest guest) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(guest));
		guests.insertOne(obj);
	}
	
	public void writeRoom (model.Room room) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(room));
		rooms.insertOne(obj);
	}
	
	public void writeReservation (model.Reservation reservation) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(reservation));
		reservations.insertOne(obj);
	}
	
	//Update room status (free, allocated, reserved)
	public void updateRoomState (String roomNr, model.RoomState state) {
		Document old = new Document("roomNum", roomNr);
		Document newRoom = new Document ("$set", new Document("RoomState", state));
		rooms.updateOne(old, newRoom);
	}
	
	public void updateGuestFavRoom(String room, model.Guest guest) {
		Document old = new Document();
		old.put("name", guest.getName());
		old.put("creditNumer", guest.getCreditNumber());
		
		Document newGuest = new Document("$set", new Document("favRoom", room));
		guests.updateOne(old, newGuest);
	}
	
	public void updateReservationState (model.Reservation res) {
		findReservations();
		Document old = new Document();
		old.put("guestName", res.getGuestName());
		old.put("room", res.getRoom());
		Document newRes = new Document("$set", new Document("checkedIn", true));
		reservations.updateOne(old, newRes);
	}
	
	// Returns list of guests with specified name
	public ArrayList<model.Guest> findGuestByName(String name) {
		ArrayList<model.Guest> guestArray = new ArrayList<model.Guest>();
	    FindIterable<BasicDBObject> cursor = guests.find(new Document("name", name));
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		   while(it.hasNext()) {
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
	
	// Returns list of reservation objects
	public ArrayList<model.Reservation> findReservedRooms() {
		ArrayList<model.Reservation> reservationArr = new ArrayList<model.Reservation>();
	    FindIterable<BasicDBObject> cursor = reservations.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		   while(it.hasNext()) {
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
	
	//Finds checked in reservations for check-out 
	public ArrayList<model.Reservation> findCheckedInReservations() {
		ArrayList<model.Reservation> reservationArr = new ArrayList<model.Reservation>();
	    FindIterable<BasicDBObject> cursor = reservations.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
		   while(it.hasNext()) {
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
	
	// Returns list of free rooms
/*	public ArrayList<model.Room> findFreeRooms(model.Room room) {
		ArrayList<model.Room> foundRooms = retrieveFreeRooms(room.getLocation(), room.getNumBeds());
		if (foundRooms.size() > 0) {
			for (Iterator<Room> it = foundRooms.iterator(); it.hasNext() ;){
				model.Room curRoom = it.next();
				if (curRoom.isSingle() != room.isSingle()) {
					it.remove();
				}
				if (curRoom.isSmoker() != room.isSmoker()) {
					it.remove();
				}
			}
			
			foundRooms.sort(new PriceComparator());
		}
		else {
			foundRooms = null;
		}
		
		return foundRooms;
	}*/
	
	// Returns all rooms
	public ArrayList<model.Room> findRooms() {
		ArrayList<model.Room> foundRooms = new ArrayList<model.Room>();
	    FindIterable<BasicDBObject> cursor = rooms.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	BasicDBObject dbobj = it.next();
			model.Room foundRoom = (new Gson()).fromJson(dbobj.toString(), model.Room.class);
			foundRooms.add(foundRoom);			
	    }
	    
		return foundRooms;
	} 
	
	public ArrayList<model.Guest> findGuests() {
		ArrayList<model.Guest> foundGuests = new ArrayList<model.Guest>();
	    FindIterable<BasicDBObject> cursor = guests.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	BasicDBObject dbobj = it.next();
			model.Guest foundGuest = (new Gson()).fromJson(dbobj.toString(), model.Guest.class);
			foundGuests.add(foundGuest);			
	    }
	    
		return foundGuests;
	} 
	
	public ArrayList<model.Reservation> findReservations() {
		ArrayList<model.Reservation> foundReservations = new ArrayList<model.Reservation>();
	    FindIterable<BasicDBObject> cursor = reservations.find();
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    while (it.hasNext()) {
	    	BasicDBObject dbobj = it.next();
			model.Reservation foundReservation = (new Gson()).fromJson(dbobj.toString(), model.Reservation.class);
			foundReservations.add(foundReservation);			
	    }
	    
		return foundReservations;
	} 
	public void deleteReservation(model.Reservation reservation) {
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(reservation));
		reservations.deleteOne(obj);
		
	}
	
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
	
	private void setUpOptions(){
		// Makes sure it times out after 4 seconds of trying to connect to the server
			optionsBuilder.serverSelectionTimeout(4000);
			options = optionsBuilder.build();
	}
	
	private void setUpLogger() {
		// Set to log only severe messages
		logger = Logger.getLogger("org.mongodb.driver");
		logger.setLevel(Level.SEVERE);
	}
	
	private ArrayList<model.Room> retrieveFreeRooms(String city, String numBeds) {
		// Looking for free rooms in specified city with provided number of beds.
		// Document class is used to provide what to search in database
		Document query = new Document();
		query.append("location", city);
		query.append("numBeds", numBeds);
		query.append("state", "free");
		
		ArrayList<model.Room> roomArray = new ArrayList<model.Room>();
	    FindIterable<BasicDBObject> cursor = rooms.find(query);
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    // If array is empty, means the city doesn't have any of that kind of rooms.
	    // Search other city
	    if (!it.hasNext()) {
	    	query = new Document();
	    	query.append("numBeds", numBeds);
	    	cursor = rooms.find(query);
	    	it = cursor.iterator();
	    	while (it.hasNext()) {
		    	BasicDBObject dbobj = it.next();
				model.Room foundRoom = (new Gson()).fromJson(dbobj.toString(), model.Room.class);
				roomArray.add(foundRoom);			
		    }
	    }
	    else {
		    while (it.hasNext()) {
		    	BasicDBObject dbobj = it.next();
				model.Room foundRoom = (new Gson()).fromJson(dbobj.toString(), model.Room.class);
				roomArray.add(foundRoom);			
		    }
	    }
	    
		return roomArray;
	}
	
	
	/**
	 * Check if database has not been initialized
	 * @return true if db is empty
	 */
	private boolean isDbEmpty() {
		return !(client.listDatabaseNames().iterator().hasNext());
	}
	
	// For whatever reason
	public void closeConnection() {
		client.close();
	}

	public ArrayList<Reservation> findReservationByName(String guestName) {
		ArrayList<model.Reservation> resArray = new ArrayList<model.Reservation>();
	    FindIterable<BasicDBObject> cursor = reservations.find(new Document("guestName", guestName));
	    MongoCursor<BasicDBObject> it = cursor.iterator();
	    try {
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

}



class PriceComparator implements Comparator<model.Room> {

	@Override
	public int compare(model.Room arg0, model.Room arg1) {
		int price1 = Integer.parseInt(arg0.getPrice());
		int price2 = Integer.parseInt(arg1.getPrice());
		
		return price1 < price2? -1 : price1 == price2 ? 0 : 1;
	}
	
}
