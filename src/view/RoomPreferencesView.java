package view;

import controller.RoomController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.RoomState;

public class RoomPreferencesView extends View {
	@FXML private TextField roomNum;
	@FXML private CheckBox singleRoom;
	@FXML private CheckBox doubleRoom;
	@FXML private CheckBox doubleKingRoom;
	@FXML private CheckBox apartment;
	@FXML private CheckBox largeRooms;
	@FXML private CheckBox view;
	@FXML private CheckBox smokerRoom;
	@FXML private TextField price;
	@FXML private Button saveBtn;
	@FXML private Button cancelBtn;

	@Override
	public Stage display() throws Exception {

		return stage;
	}

	/**
	 * When user is done creating new room, save button click
	 * makes sure that room is saved to the database
	 */
	public void onSaveBtnClick() {
		RoomState r = null; 
		// Saves new room in database
		((RoomController) controller).createRoom(roomNum.getText(), singleRoom.isSelected(), doubleRoom.isSelected(),
				doubleKingRoom.isSelected(), apartment.isSelected(), largeRooms.isSelected(), view.isSelected(),
				smokerRoom.isSelected(), price.getText(),r.free);
		// Updates the list displayed in RoomView window
		((RoomController) controller).updateRoomList();
		clearAll();
		stage.close();
	}

	public void onCancelBtnClick() {
		clearAll();
		stage.close();
	}

	private void clearAll() {
		roomNum.clear();
		singleRoom.setSelected(false);
		doubleRoom.setSelected(false);
		doubleKingRoom.setSelected(false);
		apartment.setSelected(false);
		largeRooms.setSelected(false);
		view.setSelected(false);
		smokerRoom.setSelected(false);
		price.clear();
	}
}
