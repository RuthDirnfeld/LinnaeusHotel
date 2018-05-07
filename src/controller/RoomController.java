package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Options.City;
import model.Room;
import model.RoomState;
import utils.Database;
import view.RoomPreferencesView;
import view.RoomView;

public class RoomController extends Controller {
	private FXMLLoader fxmlRoom = new FXMLLoader((getClass().getResource("../view/RoomView.fxml")));
	private FXMLLoader fxmlRoomPref = new FXMLLoader((getClass().getResource("../view/RoomPreferencesView.fxml")));

	private RoomView roomView;
	private RoomPreferencesView roomPrefView;
	private Parent roomParent;
	private Parent roomPrefParent;
	private Database database;
	private ArrayList<Room> rooms = new ArrayList<Room>();
	Controller controller = null;

	public RoomController() {
		try {
			roomParent = fxmlRoom.load();
			roomPrefParent = fxmlRoomPref.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		roomView = fxmlRoom.getController();
		roomPrefView = fxmlRoomPref.getController();

		roomView.setParent(roomParent);
		roomPrefView.setParent(roomPrefParent);

		roomView.setController(this);
		roomPrefView.setController(this);

		roomView.setStage("Room view");
		roomPrefView.setStage("Room Preferences View");
	}

	public Stage getRoomView() throws Exception {
		this.database = app.getDatabase();
		rooms = database.findRooms();
		roomView.setTable(rooms);
		roomView.initialize();
		return roomView.display();
	}

	public Stage getRoomPrefView() throws Exception {
		return roomPrefView.display();
	}

	public void createRoom(String roomNum, boolean singleRoom, boolean doubleRoom, boolean doubleKingRoom,
			boolean apartment, boolean largeRooms, boolean view, boolean smokerRoom, String price, RoomState roomstate) {
		Room room = new Room(roomNum, singleRoom, doubleRoom, doubleKingRoom, apartment, largeRooms, view, smokerRoom,
				price,roomstate);
		this.database = app.getDatabase();
		database.writeRoom(room);
	}
	
	public void reserveRoom(String room){
		Room temp = this.database.findRooms(room).get(0);
		temp.setRoomState(RoomState.reserved);
		this.database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		
	}
	
	public void allocateRoom(String room){
		Room temp = this.database.findRooms(room).get(0);
		temp.setRoomState(RoomState.allocated);
		this.database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		
	}
	public void freeRoom(String room){
		Room temp = this.database.findRooms(room).get(0);
		System.out.println(temp.getRoomNum());
		temp.setRoomState(RoomState.free);
		this.database.updateRoomState(temp.getRoomNum(),temp.getRoomState());
		
	}

	// True if all rooms, false if free only
	public void updateRoomList(boolean b) {
		this.database = app.getDatabase();
		if (!b) {
			rooms.clear();
			rooms = database.findFreeRooms();
		}
		else {
			rooms.clear();
			rooms = database.findRooms();
		}
		roomView.setTable(rooms);
		roomView.initialize();
	}
}
