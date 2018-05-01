package view;

import java.util.ArrayList;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Reservation;

public class CheckOutView extends View {
	
	@FXML private Button checkOut;
	@FXML private Button cancel;
	@FXML private ListView checkInRes;
	 
	
	ObservableList<String> reservationList = FXCollections.observableArrayList(); 
	
    private void setUpList() {
    	checkInRes.getItems().clear();
		ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
		if (!checkedIns.isEmpty()) {
			for (Reservation r : checkedIns) {
				reservationList.add(r.getName());
			}
		}
		checkInRes.setItems(reservationList);
    } 
	
	@FXML
	public void onCheckoutClick () {
		String name = checkInRes.getSelectionModel().getSelectedItem().toString();
		ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
		Reservation res = null;
		for (Reservation r : checkedIns) {
			if (r.getName().equals(name)) {
				res = r;
			}
		}
		
		if (res != null) {
			((MainController) controller).checkOut(res);
			stage.close();
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
	
	
}
