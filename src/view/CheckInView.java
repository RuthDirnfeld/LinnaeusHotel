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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CheckInView extends Application{
	@FXML private Button checkInButton;
	@FXML private TextField searchField;
	// For display
	@FXML private ListView guestList = new ListView();
	// Imported from database
	private ArrayList<model.Guest> guestArray = new ArrayList<model.Guest>();
	private ObservableList<model.Guest> data = FXCollections.observableArrayList();
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckInView.fxml"));
	
    @FXML
    public void buttonClick() {
    	//TODO get selected guest and call method from controller
    }
	
	public void setList(ArrayList<model.Guest> arr) {
		//TODO hide header
		guestArray = arr;
		data.setAll(guestArray);
		guestList.setItems(data);
	}
    
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = null;
		try {
			scene = new Scene (loader.load());
	        stage.setTitle("Check In");
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
