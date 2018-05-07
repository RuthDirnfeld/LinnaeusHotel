package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	@FXML
	private Button mngRooms;
	
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		return stage; 
	}

	
	@FXML
	public void initialize() {
		cityChoice.setItems(cityList);
	}
	
	public void setCity(String city) {
		for (int i =0; i < cityList.size(); i++) {
			if (cityList.get(i).toLowerCase().equals(city.toLowerCase())) {
				cityChoice.getSelectionModel().select(i);
			}
		}
	}
	
	public void setIp(String ip) {
		address.setText(ip);
	}
	
	public void onAcceptClick() {
		if(checkInput()){
		System.out.println("Accept");
		System.out.println(address.getText());
		if (((OptionsController) controller).createConfigFile(cityChoice.getValue(), address.getText())) {
			if (((OptionsController) controller).connectToDatabase()) {
			stage.close();
			}
			else {
				showError("Can't connect to your database!", "Make sure you provided a valid IP.");
			}
		}
		else {
			showError("Wrong Ip!", "Your provided IP is wrong!");
		}
		}
	}
	public void onClickCancel() {
		stage.close();
	}
	public void onClickManageRooms() throws Exception {
		((OptionsController) controller).getApp().getRoomController().getRoomView().show();
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
