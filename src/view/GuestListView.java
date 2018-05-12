package view;

import java.util.ArrayList;

import controller.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Guest;
import model.Room;

public class GuestListView extends View {
	@FXML private Button searchBtn;
	@FXML private Button addGuestBtn;
	@FXML private Button loadBtn;
	@FXML private Button cancelBtn;
	@FXML private Button modify;
	@FXML private TableView<Guest> guestTable;
	@FXML private TableColumn<Guest, String> name;
	@FXML private TableColumn<Guest, String> address;
	@FXML private TableColumn<Guest, String> telephone;
	@FXML private TableColumn<Guest, String> creditCard;
	@FXML private TableColumn<Guest, String> passport;
	@FXML private TableColumn<Guest, Boolean> smoker;
	@FXML private TableColumn<Guest, String> favRoom;
	@FXML private TextField searchBox;

	private ArrayList<Guest> guests;
	
	public Stage display() throws Exception {
		return stage;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	public void initialize() {
		setTable();
		//Enable modify button when something is selected in the table
		guestTable.setOnMouseClicked(new EventHandler() {
			@Override
			public void handle(Event event) {
				modify.setDisable(false);
			}
		});
	}

	public void setTable() {
		//If guest list has been initialized before
		if (guests != null) {
			// Set the table with the guests and add each of guest's information
			// in table columns
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

	@FXML
	public void searchClick() {
		// If user is attempting to search a guest, try to retrieve a list
		// with guests with that name
		if (searchBox.getText().isEmpty()) {
			((GuestController) controller).refreshGuestList();
		} else {
			((GuestController) controller).updateGuestList(searchBox.getText());
		}
	}
	

	@FXML
	/**
	 * If user clicks "New" button, opens new window to allow user to 
	 * create a new guest
	 */
	public void newClick() {
		try {
			((GuestController) controller).getGuestView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * This method is used to load information about the guest 
	 * and their favorite room (and its price) into the reservation
	 * window for the reservation.
	 */
	public void loadClick() {
		if(guestTable.getSelectionModel().getSelectedItem() != null) {
			Guest guest = guestTable.getSelectionModel().getSelectedItem();
			((GuestController) controller).getApp().getResController().setSelectedGuest(guest.getName());
			// If guest has a favorite room chosen
			if (guest.getFavRoom() != null || !guest.getFavRoom().isEmpty()) {
				ArrayList<Room> rooms = ((GuestController) controller).getAllRooms();
				String price = "";
				for (Room r : rooms) {
					if (r.getRoomNum().equals(guest.getFavRoom())) {
						price = r.getPrice();
						((GuestController) controller).getApp().getResController().suggest(r.getRoomNum(),price);
					}
				}
			}
			stage.close();
		}
	}

	public void setTable(ArrayList<Guest> list) {
		this.guests = list;
	}

	@FXML
	public void cancelClick() {
		stage.close();
	}
	
	@FXML
	/**
	 * Modify allows user to modify guest's favorite room, but
	 * can be extended to modify entire information about guest
	 * @throws Exception
	 */
	public void onModifyClick() throws Exception {
		Guest guest = guestTable.getSelectionModel().getSelectedItem();
		// Gets guest's info before opening window and loads it into text fields
		((GuestController) controller).getModifyGuestController().setUpGuest(guest);
		((GuestController) controller).getModifyGuestView().show();
		modify.setDisable(true);
	}

}