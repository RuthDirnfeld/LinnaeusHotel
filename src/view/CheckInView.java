package view;

import java.util.ArrayList;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reservation;
import model.Options.City;


public class CheckInView extends View{
	@FXML private Button checkInButton;
	@FXML private TextField searchField;
    @FXML private TableView<Reservation> resTable;
	@FXML private TableColumn<Reservation, String> nameColumn;
	@FXML private TableColumn<Reservation, String> roomColumn;
	@FXML private TableColumn<Reservation, String> checkInColumn;
	@FXML private TableColumn<Reservation, String> checkOutColumn;
	
	public ArrayList<Reservation> resv;
	public ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		setTable();	
	}
	
	
	/**
	 * Updates list of guests that can check in today
	 */
	private void setTable() {
		//Clears all items in the table
		resTable.getItems().clear();
		// If resv array has ben initiated before (for updating list)
		if (resv != null) {
			ObservableList<Reservation> resvList = FXCollections.observableList(resv);
			for (Reservation r : resvList) {
				// The loop looks for reservations only in the city that the client is in as 
				// the client shouldn't be able to check in from another city
				if (((MainController)controller).getApp().getOptions().getCurrentCity() == City.VAXJO) {
					if (r.getRoom().contains("V")) {
						reservationList.add(r);
					}
				}
				else{
					if (r.getRoom().contains("K")) {
					reservationList.add(r);
					}
				}
			}
		
			resTable.setItems(reservationList);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("guestName"));
			roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
			checkInColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
			checkOutColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("endDate"));
		}}
	
	
	
	@FXML
	public void searchBox(){
		// If the user is attempting to search a name, refresh view with 
		// names that fit the search
		if(!searchField.getText().isEmpty()) {
			((MainController) controller).refreshCheckInView(searchField.getText());	
		}else {
			((MainController) controller).refreshCheckInView();
		}
	}
	
	@FXML
	public void buttonClick() {
		// If the user has selected any guests to be checked in
		// and clicks the button, the guest gets checked in.
		if (resTable.getSelectionModel().getSelectedItem() != null) {
			Reservation res = resTable.getSelectionModel().getSelectedItem();
			((MainController) controller).checkIn(res); 
			((MainController) controller).refreshCheckInView();
			searchField.clear();
		}
	}
	
	
	/**
	 * Updates a list of reservations displayed in check in list
	 * @param list
	 */
	public void setTable(ArrayList<Reservation> list) {
		this.resv = list;
	}
    
	@Override
	public Stage display() throws Exception {
		return stage;
	}
}