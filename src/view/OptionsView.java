package view;

import controller.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controller.OptionsController;

public class OptionsView extends View {
	@FXML
	private TextField address; 
	@FXML 
	private ChoiceBox<String> cityChoice; 
	@FXML 
	private Button acceptButton; 
	@FXML 
	private Button cancelButton; 
	
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		return stage; 
	}

	
	@FXML
	public void initialize() {
		cityChoice.setItems(cityList);
	}
	
	public void onAcceptClick() {
		if(checkInput()){
		System.out.println("Accept");
		System.out.println(address.getText());
		((OptionsController) controller).createConfigFile(cityChoice.getValue(), address.getText());
		Stage stage = (Stage) acceptButton.getScene().getWindow();
		stage.close();
		}
	}
	public void onClickCancel() {
		System.out.println("Cancel");
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	public void onClickManageRooms() {
		System.out.println("Manage Rooms");
	}
	public void onClickManageFloors() {
		System.out.println("Manage Floors");
	}
	
	private boolean checkInput() {
		if(address.getText().isEmpty()) {
		showError("No IP address", "Please specify IP address");
		return false; 
		}
		if(cityChoice.getSelectionModel().isEmpty()) {
		showError("No city selected", "Please select a city");
		return false;
		}
		return true;
	}
	public static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	
}
