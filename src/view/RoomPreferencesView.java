package view;

import controller.RoomController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomPreferencesView extends View {
	@FXML
	private TextField roomNum;
	@FXML
	private CheckBox singleRoom;
	@FXML
	private CheckBox doubleRoom;
	@FXML
	private CheckBox doubleKingRoom;
	@FXML
	private CheckBox apartment;
	@FXML
	private CheckBox largeRooms;
	@FXML
	private CheckBox view;
	@FXML
	private CheckBox smokerRoom;
	@FXML
	private TextField price;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	@Override
	public Stage display() throws Exception {

		return stage;
	}

	public void onSaveBtnClick() {
		((RoomController) controller).createRoom(roomNum.getText(), singleRoom.isSelected(), doubleRoom.isSelected(),
				doubleKingRoom.isSelected(), apartment.isSelected(), largeRooms.isSelected(), view.isSelected(),
				smokerRoom.isSelected(), price.getText());
		((RoomController) controller).updateRoomList();
		clearAll();
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

	public void onCancelBtnClick() {
		clearAll();
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

	public void clearAll() {
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
