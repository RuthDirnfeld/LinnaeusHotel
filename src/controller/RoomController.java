package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Room;
import model.RoomState;
import view.RoomPreferencesView;
import view.RoomView;

public class RoomController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlRoom = new FXMLLoader((getClass().getResource("../view/RoomView.fxml")));
	private FXMLLoader fxmlRoomPref = new FXMLLoader((getClass().getResource("../view/RoomPreferencesView.fxml")));

	// Views RoomController is responsible for
	private RoomView roomView;
	private RoomPreferencesView roomPrefView;
	
	// Parent objects to use when initializing each view's scenes
	private Parent roomParent;
	private Parent roomPrefParent;

	// Variables for this class only
	private ArrayList<Room> rooms = new ArrayList<Room>();

	public RoomController() {
		// Setting parents as fxml files
		try {
			roomParent = fxmlRoom.load();
			roomPrefParent = fxmlRoomPref.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Connect fxml files with view classes
		roomView = fxmlRoom.getController();
		roomPrefView = fxmlRoomPref.getController();

		// Setting parent/root objects for each view
		roomView.setParent(roomParent);
		roomPrefView.setParent(roomPrefParent);

		// Setting this class to be a controller of each view this
		// controller is responsible for
		roomView.setController(this);
		roomPrefView.setController(this);
		
		// Setting names of stages for each view class
		roomView.setStage("Room view");
		roomPrefView.setStage("Room Preferences View");
	}

	/**
	 * Updates room list and returns a stage of Room View UI.
	 * @return
	 * @throws Exception
	 */
	public Stage getRoomView() throws Exception {
		updateRoomList();
		return roomView.display();
	}

	public Stage getRoomPrefView() throws Exception {
		return roomPrefView.display();
	}

	/**
	 * Creates new room object and stores it in the database
	 * @param roomNum
	 * @param singleRoom
	 * @param doubleRoom
	 * @param doubleKingRoom
	 * @param apartment
	 * @param largeRooms
	 * @param view
	 * @param smokerRoom
	 * @param price
	 * @param roomstate
	 */
	public void createRoom(String roomNum, boolean singleRoom, boolean doubleRoom, boolean doubleKingRoom,
			boolean apartment, boolean largeRooms, boolean view, boolean smokerRoom, String price, RoomState roomstate) {
		Room room = new Room(roomNum, singleRoom, doubleRoom, doubleKingRoom, apartment, largeRooms, view, smokerRoom,
				price,roomstate);
		database.writeRoom(room);
	}
	
	/**
	 * Changes single room's state to reserved in the database
	 * @param room
	 */
	public void reserveRoom(String room){
		if (database.findRooms(room).get(0) != null) {
			// Find certain room
			Room temp = app.getDatabase().findRooms(room).get(0);
			temp.setRoomState(RoomState.reserved);
			database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		}
	}
	
	/**
	 * Changes single room's state to allocated in the database
	 * @param room
	 */
	public void allocateRoom(String room){
		if (database.findRooms(room).get(0) != null) {
			// Find certain room
			Room temp = database.findRooms(room).get(0);
			temp.setRoomState(RoomState.allocated);
			database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		}
	}
	
	/**
	 * Changes single room's state to free
	 * @param room
	 */
	public void freeRoom(String room){
		if(database.findReservationByRoom(room).isEmpty()) {
			// Find certain room
			Room temp = database.findRooms(room).get(0);
			temp.setRoomState(RoomState.free);
			database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		}
	}

	/**
	 * Updates list of rooms (by retrieving them from the database) 
	 * in case there has been any change
	 * 
	 */
	public void updateRoomList() {
		rooms = database.findRooms();
		roomView.setTable(rooms);
		roomView.initialize();
	}
}
