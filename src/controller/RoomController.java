package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.RoomView;

public class RoomController extends Controller{
	private FXMLLoader fxmlRoom = new FXMLLoader((getClass().getResource("../view/RoomView.fxml")));
	
	private RoomView roomView;
	private Parent roomParent;
	
	public RoomController() {
		try {
			roomParent = fxmlRoom.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		roomView = fxmlRoom.getController(); 
		roomView.setParent(roomParent);
		roomView.setController(this);
		roomView.setStage("Room view");
	}
	
	public Stage getRoomView() throws Exception {
		return roomView.display();
	}

}
