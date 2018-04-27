package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.ReservationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Guest;
import model.Reservation;

public class ReservationView extends View {

	@FXML
	private DatePicker arrivalDate;
	@FXML
	private DatePicker departureDate;
	@FXML
	private TextField chosenGuest;
	@FXML
	private TextField calcRoom;
	@FXML
	private TextField calcPrice;
	@FXML
	private RadioButton oneBed;
	@FXML
	private RadioButton twoBeds;
	@FXML
	private RadioButton threeBeds;
	@FXML
	private RadioButton fourBeds;
	@FXML
	private RadioButton apartment;
	@FXML
	private Button chooseGuestBtn;
	@FXML
	private Button clearFieldsBtn;
	@FXML
	private Button OkBtn;
	@FXML
	private Button ShowBtn;
	@FXML
	private TableView<Reservation> resTable;
	@FXML
	private TableColumn<Reservation, String> nameColumn;
	@FXML
	private TableColumn<Reservation, String> roomColumn;
	@FXML
	private TableColumn<Reservation, String> checkInColumn;
	@FXML
	private TableColumn<Reservation, String> checkOutColumn;
	@FXML
	private TableColumn<Reservation, String> priceColumn;

	public ArrayList<Reservation> resv;
	GuestListView guestListView;

	@Override
	public Stage display() throws Exception {
		return stage;
	}

	public void initialize() {
		onArrivalClick();
		onDepartureClick();
		setTable();
	}

	// DatePicker choice validation
	public void onArrivalClick() {
		dateConverter();
		final Callback<DatePicker, DateCell> sdayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (departureDate.getValue() != null && item.isAfter(departureDate.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #a6a6a6");
						}
					}
				};
			}
		};
		arrivalDate.setDayCellFactory(sdayCellFactory);
	}

	// DatePicker choice validation
	public void onDepartureClick() {
		dateConverter();
		final Callback<DatePicker, DateCell> edayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (arrivalDate.getValue() != null && item.isBefore(arrivalDate.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #a6a6a6");
						}
					}
				};
			}
		};
		departureDate.setDayCellFactory(edayCellFactory);
	}

	// Set Cell Values of the TableView
	public void setTable() {
		if (resv != null) {
			ObservableList<Reservation> resvList = FXCollections.observableList(resv);
			resTable.setItems(resvList);

			nameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("guestName"));
			roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
			checkInColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
			checkOutColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("endDate"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("price"));
		}
	}

	public void singleRoomClick() {
		System.out.println("Single Room");
	}

	public void doubleRoomClick() {
		System.out.println("Double Room");
	}

	public void tripleRoomClick() {
		System.out.println("Triple Room");
	}

	public void fourBedClick() {
		System.out.println("Four Bed Room");
	}

	public void apartmentClick() {
		System.out.println("Apartment");
	}

	// Opens a List of all Guests
	public void chooseGuestClick() {
		try {
			((ReservationController) controller).getApp().getGuestController().getGuestListView().showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Clears all Fields
	public void clearFieldsClick() {
		clearAll();
	}

	// Adds the new Reservation to TableView
	public void OkBtnClick() {
		((ReservationController) controller).createReservation(chosenGuest.getText(), calcRoom.getText(),
				arrivalDate.getValue(), departureDate.getValue(), calcPrice.getText());

		((ReservationController) controller).updateReservationList();
		clearAll();
	}

	// TODO button will be probably moved to show rooms or smtg or removed, not sure
	// yet
	public void showBtnClick() {
		System.out.println("Show");
	}

	public void setTable(ArrayList<Reservation> list) {
		this.resv = list;
	}
	
	public void getReservationList() {
		((ReservationController) controller).updateReservationList();
	}

	public void clearAll() {
		arrivalDate.setValue(null);
		departureDate.setValue(null);
		chosenGuest.clear();
		calcRoom.clear();
		calcPrice.clear();
	}

	// DatePicker uses by default MM-dd-yyyy :(
	public void dateConverter() {
		String pattern = "dd-MM-yyyy";

		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		arrivalDate.setConverter(converter);
		arrivalDate.setPromptText(pattern.toLowerCase());
		arrivalDate.requestFocus();
		departureDate.setConverter(converter);
		departureDate.setPromptText(pattern.toLowerCase());
		departureDate.requestFocus();

	}
}