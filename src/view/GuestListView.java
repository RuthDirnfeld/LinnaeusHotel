package view;

import java.util.ArrayList;
import java.util.List;

import controller.GuestController;
import controller.ReservationController;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Guest;

public class GuestListView extends View {
	@FXML
	private Button searchBtn;
	@FXML
	private Button addGuestBtn;
	@FXML
	private Button loadBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private TableView<Guest> guestTable;
	@FXML
	private TableColumn<Guest, String> name;
	@FXML
	private TableColumn<Guest, String> address;
	@FXML
	private TableColumn<Guest, String> telephone;
	@FXML
	private TableColumn<Guest, String> creditCard;
	@FXML
	private TableColumn<Guest, String> passport;
	@FXML
	private TableColumn<Guest, Boolean> smoker;
	@FXML
	private TableColumn<Guest, String> favRoom;
	@FXML
	private TextField searchBox;

	public ArrayList<Guest> guests;
	ReservationView resView = new ReservationView();
	
	public Stage display() throws Exception {
		return stage;
	}

	@FXML
	public void initialize() {
		setTable();
	}

	public void setTable() {
		if (guests != null) {
			ObservableList<Guest> guestList = FXCollections.observableList(guests);
			guestTable.setItems(guestList);
			name.setCellValueFactory(new PropertyValueFactory<Guest, String>("name"));
			address.setCellValueFactory(new PropertyValueFactory<Guest, String>("address"));
			telephone.setCellValueFactory(new PropertyValueFactory<Guest, String>("phoneNum"));
			creditCard.setCellValueFactory(new PropertyValueFactory<Guest, String>("creditNumber"));
			passport.setCellValueFactory(new PropertyValueFactory<Guest, String>("passportNumber"));
			favRoom.setCellValueFactory(new PropertyValueFactory<Guest, String>("favRoom"));
			smoker.setCellValueFactory(new PropertyValueFactory<Guest, Boolean>("smoker"));
		}
	}

	public void searchClick() {
		if (searchBox.getText().isEmpty()) {
			System.out.println("test");
			((GuestController) controller).updateGuestList();
		} else {
			((GuestController) controller).updateGuestList(searchBox.getText());
		}
	}

	public void newClick() {
		try {
			((GuestController) controller).getGuestView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadClick() {
		Guest guest = guestTable.getSelectionModel().getSelectedItem();
		System.out.println(guest.getName());
		((GuestController) controller).getApp().getResController().setSelectedGuest(guest.getName());
		Stage stage = (Stage) loadBtn.getScene().getWindow();
		stage.close();
	}

	/* Cant fetch the arraylist for some reason */
	public void setTable(ArrayList<Guest> list) {
		this.guests = list;
	}

	public void fetchGuestList() {
		((GuestController) controller).updateGuestList();
	}

	public void cancelClick() {
		System.out.println("Cancel");
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

}