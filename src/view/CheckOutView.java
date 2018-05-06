package view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Reservation;

public class CheckOutView extends View {
	
	@FXML private Button checkOut;
	@FXML private Button cancel;
	@SuppressWarnings("rawtypes")
	@FXML private ListView checkInRes;
	 
	
	ObservableList<String> reservationList = FXCollections.observableArrayList(); 
	
    @SuppressWarnings("unchecked")
	private void setUpList() {
    	checkInRes.getItems().clear();
		ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
		if (!checkedIns.isEmpty()) {
			for (Reservation r : checkedIns) {
				reservationList.add(r.getGuestName());
			}
		}
		checkInRes.setItems(reservationList);
    } 
	
	@FXML
	public void onCheckoutClick () throws FileNotFoundException, UnsupportedEncodingException {
		String name = checkInRes.getSelectionModel().getSelectedItem().toString();
		ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
		Reservation res = null;
		for (Reservation r : checkedIns) {
			if (r.getGuestName().equals(name)) {
				res = r;
			}
		}
		
		if (res != null) {
			if (((MainController) controller).checkOut(res)) {
				alertBox("Guest successfully checked out!");
				stage.close();
			}
			else {
				alertBox("Something went wrong!");
			}
		}
	}
	
	@FXML
	public void onCancelClick() {
		stage.close();
	}

	public Stage display() throws Exception {
		setUpList();
		return stage;
	}
	
	private void alertBox(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
	}
	
	
}
