package view;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class RoomView extends View{
	@FXML private Button closeButton;
	// For display
	@FXML private ListView roomList = new ListView();
	// Imported from database
	private ArrayList<model.Room> roomArray = new ArrayList<model.Room>();
	private ObservableList<model.Room> roomData = FXCollections.observableArrayList();

    
	public void setRooms(ArrayList<model.Room> arr) {
		//TODO hide header
		roomArray = arr;
		roomData.setAll(roomArray);
		roomList.setItems(roomData);
	}
    
	@Override
	public Stage display() throws Exception {
		Stage stage = new Stage();
		Scene scene = new Scene (parent);
		stage.setTitle("Room view");
		stage.setScene(scene);
		return stage;
	}


}
