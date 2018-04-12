package view;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckInView extends View{
	@FXML private Button checkInButton;
	@FXML private TextField searchField;
	// For display
	@FXML
	private ListView guestList = new ListView();
	// Imported from database
	private ArrayList<model.Guest> guestArray = new ArrayList<model.Guest>();
	private ObservableList<model.Guest> data = FXCollections.observableArrayList();
	
    @FXML
    public void buttonClick() {
    	//TODO get selected guest and call method from controller
    }
	
	public void setList(ArrayList<model.Guest> arr) {
		// TODO hide header
		guestArray = arr;
		data.setAll(guestArray);
		guestList.setItems(data);
	}

	@Override
	public Stage display() throws Exception {
		Stage stage = new Stage();
		Scene scene = new Scene (parent);
		stage.setTitle("Check In");
		stage.setScene(scene);
		return stage;
	}
}
