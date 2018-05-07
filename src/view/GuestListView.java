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
	@FXML
	private Button searchBtn;
	@FXML
	private Button addGuestBtn;
	@FXML
	private Button loadBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button modify;
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
		if(guestTable.getSelectionModel().getSelectedItem() != null) {
			Guest guest = guestTable.getSelectionModel().getSelectedItem();
			((GuestController) controller).getApp().getResController().setSelectedGuest(guest.getName());
			if (guest.getFavRoom() != null || !guest.getFavRoom().isEmpty()) {
				ArrayList<Room> rooms = ((GuestController) controller).getAllRooms();
				String price = "";
				((GuestController) controller).getApp().getResController().setSelectedRoom(guest.getFavRoom());
				for (Room r : rooms) {
					if (r.getRoomNum().equals(guest.getFavRoom())) {
						price = r.getPrice();
					}
				}
				((GuestController) controller).getApp().getResController().setSelectedPrice(price);
			}
			Stage stage = (Stage) loadBtn.getScene().getWindow();
			stage.close();
		}
	}

	/* Cant fetch the arraylist for some reason */
	public void setTable(ArrayList<Guest> list) {
		this.guests = list;
	}

	public void fetchGuestList() {
		((GuestController) controller).updateGuestList();
	}

	public void cancelClick() {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}
	
	public void onModifyClick() throws Exception {
		Guest guest = guestTable.getSelectionModel().getSelectedItem();
		((GuestController) controller).getModifyGuestController().setUpGuest(guest);
		((GuestController) controller).getModifyGuestView().show();
		modify.setDisable(true);
	}

}