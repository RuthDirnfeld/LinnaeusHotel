package view;

import java.util.ArrayList;

import controller.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Guest;
import model.Room;

public class ModifyGuestView extends View {
	@FXML
	private TextField fullName;
	@FXML
	private TextField address;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField passportNumber;
	@FXML
	private TextField creditCardNum;
	@FXML
	private CheckBox smoker;
	@FXML
	private ChoiceBox<String> favRoom;
	@FXML
	private Button cancelButton;
	@FXML
	private Button submitButton;
	
	private ObservableList<String> rooms = FXCollections.observableArrayList();
	
	@Override
	public Stage display()throws Exception {
		return stage;
	}

	public void setUpGuest (Guest guest) {
		ArrayList<Room> foundRooms = ((GuestController) controller).getAllRooms();
		for (Room r : foundRooms) {
			rooms.add(r.getRoomNum());
		}
		fullName.setText(guest.getName());
		address.setText(guest.getAddress());
		phoneNumber.setText(guest.getPhoneNum());
		creditCardNum.setText(guest.getCreditNumber());
		if (guest.isSmoker()) {
			smoker.setSelected(true);
		}
		favRoom.setItems(rooms);
	}
	
	public void onClickConfirm(){
		if(inputCheck()) {
			((GuestController) controller).modifyGuest(fullName.getText(), address.getText(), phoneNumber.getText(), creditCardNum.getText(),
					passportNumber.getText(), smoker.isSelected(), (String) favRoom.getSelectionModel().getSelectedItem());
		}
		((GuestController) controller).refreshGuestList();
		clearAll();
		stage.close();
		
	}

	public void onClickCancel() {
		clearAll();
		stage.close();
	}

	public boolean inputCheck() {
		if (fullName.getText().length() < 3) {
			showError("Name is too short !", "Please enter a name that is longer than 3 characters");
			return false;
		}
		if (passportNumber.getText() == null) {
			showError("No Citizenship", "Please specify the citizenship");
			return false;
		}
		if (address.getText() == null) {
			showError("No Address", "Please specify the address");
			return false;
		}
		if (creditCardNum.getText() == null) {
			showError("No Creditcard number", "Please specify the credit card number");
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
	
	public void clearAll(){
	    fullName.clear();
	    address.clear();
		phoneNumber.clear();
		passportNumber.clear();
	    creditCardNum.clear();
	    favRoom.getSelectionModel().clearSelection();
		smoker.setSelected(false);
	}
}
