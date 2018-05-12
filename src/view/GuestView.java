package view;

import controller.GuestController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GuestView extends View {
	@FXML private TextField fullName;
	@FXML private TextField address;
	@FXML private TextField phoneNumber;
	@FXML private TextField passportNumber;
	@FXML private TextField creditCardNum;
	@FXML private CheckBox smoker;
	@FXML private TextField favRoom;
	@FXML private Button cancelButton;
	@FXML private Button submitButton;


	@Override
	public Stage display()throws Exception {
		return stage;
	}

	@FXML
	public void initialize() {
		// Allows only digits to be input
		phoneNumber.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (!"0123456789".contains(keyEvent.getCharacter())) {
					keyEvent.consume();
				}
			}
		});
		// Allows only digits to be input
		creditCardNum.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (!"0123456789".contains(keyEvent.getCharacter())) {
					keyEvent.consume();
				}
			}
		});

	}

	@FXML
	/**
	 * When "confirm" button is clicked, passes information 
	 * to GuestController and new guest is created.
	 */
	public void onClickConfirm(){
		if(inputCheck()) {
			((GuestController) controller).createGuest(fullName.getText(), address.getText(), phoneNumber.getText(), creditCardNum.getText(),
					passportNumber.getText(), smoker.isSelected(), favRoom.getText());
			((GuestController) controller).refreshGuestList();
			clearAll();
			stage.close();
		}
	}

	@FXML
	public void onClickCancel() {
		clearAll();
		stage.close();
	}

	/**
	 * Helper method to check whether input information is correct
	 * @return
	 */
	private boolean inputCheck() {
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

	/**
	 * Helped method to display an error
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
	
	/**
	 * Clears all the text fields
	 */
	private void clearAll(){
	    fullName.clear();
	    address.clear();
		phoneNumber.clear();
		passportNumber.clear();
	    creditCardNum.clear();
	    favRoom.clear();
		smoker.setSelected(false);
	}

}