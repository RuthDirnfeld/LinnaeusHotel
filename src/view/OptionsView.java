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
	@FXML private TextField address; 
	@FXML private ChoiceBox<String> cityChoice; 
	@FXML private Button acceptButton; 
	@FXML private Button cancelButton; 
	@FXML private Button mngRooms;

	private ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		return stage; 
	}

	
	@FXML
	public void initialize() {
		cityChoice.setItems(cityList);
	}
	
	/**
	 * If there is a chosen city in the config file,
	 * the city will be selected when options window opens.
	 * @param city
	 */
	public void setCity(String city) {
		for (int i =0; i < cityList.size(); i++) {
			if (cityList.get(i).toLowerCase().equals(city.toLowerCase())) {
				cityChoice.getSelectionModel().select(i);
			}
		}
	}
	
	/**
	 * Set text of the IP field if the IP
	 * already exists in the config
	 * @param ip
	 */
	public void setIp(String ip) {
		address.setText(ip);
	}
	
	@FXML
	/**
	 * Method called when "accept" button is clicked
	 */
	public void onAcceptClick() {
		if(checkInput()){
			// Check if provided input is correct
			if (((OptionsController) controller).isIpValid(address.getText().split(":")[0])
					&& ((OptionsController) controller).isPortValid(address.getText().split(":")[1])) {
				// Set dabatase address, which attempts to connect to database as well
				if (((OptionsController) controller).getApp().getNetController().setDatabaseAddress(address.getText().split(":")[0], address.getText().split(":")[1] )) {
					// All good, write to config
					((OptionsController) controller).updateConfigFile(cityChoice.getValue(), address.getText());
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
	
	/**
	 * Opens room management list
	 * @throws Exception
	 */
	public void onClickManageRooms() throws Exception {
		((OptionsController) controller).getApp().getRoomController().getRoomView().show();
		((OptionsController) controller).getApp().getRoomController().updateRoomList();
	}
	
	/**
	 * Helper method to check options input
	 * @return
	 */
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
	
	/**
	 * Helper method to show information/error message
	 * @param title
	 * @param message
	 */
	private static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	
}
