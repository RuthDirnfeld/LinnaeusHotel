package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Options.City;
import model.Room;
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
			boolean apartment, boolean largeRooms, boolean view, boolean smokerRoom, String price) {
		Room room = new Room(roomNum, singleRoom, doubleRoom, doubleKingRoom, apartment, largeRooms, view, smokerRoom,
				price);
		this.database = app.getDatabase();
		database.writeRoom(room);
	}

	// True if all rooms, false if free only
	public void updateRoomList(boolean b) {
		this.database = app.getDatabase();
		if (!b) {
			ArrayList<Room> tempRooms = database.findFreeRooms();
			ArrayList<Room> freeRooms = new ArrayList<Room>();
			for (Room r : tempRooms) {
				if (app.getOptions().getCurrentCity().equals(City.VAXJO)) {
					if (r.getRoomNum().contains("V")) {
						freeRooms.add(r);
					}
				}
				else {
					if (r.getRoomNum().contains("K")) {
						freeRooms.add(r);
					}
				}
			}
			rooms.clear();
			rooms = freeRooms;
		}
		else {
			rooms.clear();
			rooms = database.findRooms();
		}
		roomView.setTable(rooms);
		roomView.initialize();
	}
}
