package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OptionsView extends View {
	@FXML
	private TextField address; 
	@FXML 
	private ChoiceBox<String> cityChoice; 
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		return stage; 
	}

	
	@FXML 
	private void initialize() {
		cityChoice.setItems(cityList);
	}
	
	public void onAcceptClick() {
		System.out.println("Accept");
		System.out.println(address.getText());
	}
	public void onClickCancel() {
		System.out.println("Cancel");
	}
	public void onClickManageRooms() {
		System.out.println("Manage Rooms");
	}
	public void onClickManageFloors() {
		System.out.println("Manage Floors");
	}
	
}
