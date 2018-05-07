package view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.ReservationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private Button chooseGuestBtn;
	@FXML
	private Button clearFieldsBtn;
	@FXML
	private Button OkBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button roomsBtn;
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

	// Opens a List of all Guests
	public void chooseGuestClick() {
		try {
			((ReservationController) controller).getApp().getGuestController().getGuestListView().showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onRoomsBtnClick() {
		try {
			((ReservationController) controller).getApp().getRoomController().getRoomView().show();
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
		if (inputCheck()) {
			((ReservationController) controller).createReservation(chosenGuest.getText(), calcRoom.getText(),
					arrivalDate.getValue(), departureDate.getValue(), calcPrice.getText());
			((ReservationController) controller).updateReservationList();
			((ReservationController) controller).getApp().getRoomController().reserveRoom(calcRoom.getText());
			clearAll();
		}
	}

	public void onCancelBtnClick() {
		if (resTable.getSelectionModel().getSelectedItem() != null) {
			Reservation res = resTable.getSelectionModel().getSelectedItem();
			try {
				((ReservationController) controller).getApp().getMainController().printBill(res, true);
				((ReservationController) controller).deleteReservation(res);
				((ReservationController) controller).updateReservationList();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
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

	public void setSelectedGuest(String name) {
		chosenGuest.setText(name);

	}
	
	public void setSelectedRoom(String roomNum) {
		calcRoom.setText(roomNum);
	}
	
	public void setSelectedPrice(String price) {
		calcPrice.setText(price);
	}

	public boolean inputCheck() {
		if (calcRoom.getText().isEmpty()) {
			showError("No room selected", "Please select a room");
			return false;
		}
		if (chosenGuest.getText().isEmpty()) {
			showError("No guest selected", "Please select a guest");
			return false;
		}
		if (arrivalDate.getValue() == null) {
			showError("No arrival date selected", "Please specify the arrival date");
			return false;
		}
		if (departureDate.getValue() == null) {
			showError("No departure date selected", "Please specify the departure date");
			return false;
		}
		if (calcPrice.getText().isEmpty()) {
			showError("No price entered", "Please specify the price per night");
			return false;
		}
		return true;
	}

	public static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

}