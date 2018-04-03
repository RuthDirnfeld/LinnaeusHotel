package view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RoomView extends Application{
	@FXML private Button closeButton;
	@FXML private ChoiceBox floorSelector;
	// For display
	@FXML private ListView roomList = new ListView();
	// Imported from database
	private ArrayList<model.Room> roomArray = new ArrayList<model.Room>();
	private ObservableList<model.Room> roomData = FXCollections.observableArrayList();
	private ObservableList floorData = FXCollections.observableArrayList();
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomView.fxml"));
	
    
    public void setFloors(int[] arr) {
    	floorData.setAll(arr);
    	floorSelector.setItems(floorData);
    }
    
	public void setRooms(ArrayList<model.Room> arr) {
		//TODO hide header
		roomArray = arr;
		roomData.setAll(roomArray);
		roomList.setItems(roomData);
	}
    
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = null;
		try {
			scene = new Scene (loader.load());
	        stage.setTitle("Room view");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}
