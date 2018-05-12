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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Options.City;
import model.Reservation;

public class CheckOutView extends View {
	
	@FXML private Button checkOut;
	@FXML private Button cancel;
	@FXML private TableView<Reservation> table;
	@FXML private TableColumn<Reservation, String> nameColumn;
	@FXML private TableColumn<Reservation, String> roomColumn;
	
	private ObservableList<Reservation> reservationList = FXCollections.observableArrayList(); 
	
	/**
	 * Sets up a list of checked in users to be checked out.
	 */
	private void setUpList() {
		// Clears table before setting up new list
    	table.getItems().clear();
		ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
		if (!checkedIns.isEmpty()) {
			for (Reservation r : checkedIns) {
				// Loop makes sure that only guests in client's city is shown
				if (((MainController)controller).getApp().getOptions().getCurrentCity() == City.VAXJO) {
					if (r.getRoom().contains("V")) {
						reservationList.add(r);
					}
				}
				else {
					if (r.getRoom().contains("K")) {
						reservationList.add(r);
						}
				}
			}
		}
		table.setItems(reservationList);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("guestName"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
    } 
	
	@FXML
	public void onCheckoutClick () throws FileNotFoundException, UnsupportedEncodingException {
		Reservation reservation = table.getSelectionModel().getSelectedItem();
		if (reservation != null) {
			String name = reservation.getGuestName();
			ArrayList<Reservation> checkedIns = ((MainController) controller).getCheckedInReservations();
			Reservation res = null;
			for (Reservation r : checkedIns) {
				// Finds a reservation in the database to check out
				if (r.getGuestName().equals(name)) {
					res = r;
				}
			}
			// A guest with reservation was found in the database
			if (res != null) {
				// Check out method returns true, then all worked well.
				// If false, possible issues while connecting to database
				if (((MainController) controller).checkOut(res)) {
					alertBox("Guest successfully checked out!");
					stage.close();
				}
				else {
					alertBox("Something went wrong!");
				}
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
	
	/**
	 * Method to show an information box
	 * @param message
	 */
	private void alertBox(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
	}
	
	
}
