package view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import controller.ReservationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Reservation;

public class ReservationView extends View {

	@FXML private DatePicker arrivalDate;
	@FXML private DatePicker departureDate;
	@FXML private TextField chosenGuest;
	@FXML private TextField calcRoom;
	@FXML private TextField calcPrice;
	@FXML private Button chooseGuestBtn;
	@FXML private Button clearFieldsBtn;
	@FXML private Button OkBtn;
	@FXML private Button cancelBtn;
	@FXML private Button roomsBtn;
	@FXML private TableView<Reservation> resTable;
	@FXML private TableColumn<Reservation, String> nameColumn;
	@FXML private TableColumn<Reservation, String> roomColumn;
	@FXML private TableColumn<Reservation, String> checkInColumn;
	@FXML private TableColumn<Reservation, String> checkOutColumn;
	@FXML private TableColumn<Reservation, String> priceColumn;

	private ArrayList<Reservation> resv;

	@Override
	public Stage display() throws Exception {
		departureDate.setDisable(true);
		arrivalDate.valueProperty().addListener((ov, oldValue, newValue) -> {
		    departureDate.setDisable(false);
		});
		
		return stage;
	}

	/**
	 * Initializes date pickers
	 * and reservation table
	 */
	public void initialize() {
		onArrivalClick();
		onDepartureClick();
		setTable();
	}
	
	
	/**
	 * Handles date picker and choice validation,
	 * meaning that arrival cannot be after departure.
	 */
	public void onArrivalClick() {
		// Conversts data to proper european format and to string
		dateConverter();
		
		final Callback<DatePicker, DateCell> sdayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						// If room is selected
						if(!calcRoom.getText().isEmpty()) {
							// Checks if there are any reservations in the list
							if(!resv.isEmpty()) {
								for(int i = 0; i < resv.size(); i++) {
									// Loop checks if input room is in the reservation list
									// and if it is, then it checks if arrival date is selected after
									// reservation date. If it is, it allows to select that date,
									// otherwise date is disabled and marked with color
									if(resv.get(i).getRoom().equals(calcRoom.getText())) {
										if(item.isBefore(resv.get(i).getEndDate())&&item.isAfter(resv.get(i).getStartDate())||item.equals(resv.get(i).getStartDate())){
											setDisable(true);
											setStyle("-fx-background-color:lightcoral");
										}
									}
								}
							}
						}

						// If departure value was selected before. check that arrival
						// is not after departure
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

	/**
	 * Handles date picker and choice validation,
	 * meaning that departure cannot be before arrival.
	 */
	public void onDepartureClick() {
		// Conversts data to proper european format and to string
		dateConverter();
		// If arrival date is set already, disable dates that 
		// would be before arrival date
		if(arrivalDate.getValue() != null){
			departureDate.setDisable(false);
		}
		final Callback<DatePicker, DateCell> edayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						// If room is chosen
						if(!calcRoom.getText().isEmpty()) {
							// Checks if there are any reservations in the list
							if(!resv.isEmpty()) {
								for(int i = 0; i<resv.size(); i++) {
								// Loop checks if input room is in the reservation list
								// and if it is, then it checks if departure date is selected after
								// reservation date. If it is, it allows to select that date,
								// otherwise date is disabled and marked with color
									if(resv.get(i).getRoom().equals(calcRoom.getText())) {
										if((item.isBefore(resv.get(i).getEndDate())&&item.isAfter(resv.get(i).getStartDate()))||(item.isAfter(resv.get(i).getEndDate())&& arrivalDate.getValue().isBefore(resv.get(i).getStartDate())||item.equals(resv.get(i).getEndDate()))){
											setDisable(true);
											setStyle("-fx-background-color:lightcoral");
										}
									}
								}
							}
						}

						// If arrival value is set, disables not allowed dates
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

	/**
	 * Sets cell values for tableView
	 */
	public void setTable() {
		// If resvation array was set before, continue
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

	/**
	 * Opens a list of guests
	 */
	public void chooseGuestClick() {
		try {
			((ReservationController) controller).getApp().getGuestController().getGuestListView().showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a list of rooms
	 */
	public void onRoomsBtnClick() {
		try {
			((ReservationController) controller).getApp().getRoomController().getRoomView().show();
			((ReservationController) controller).getApp().getRoomController().updateRoomList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearFieldsClick() {
		clearAll();
	}

	/**
	 * Adds new reservation to the reservation list and 
	 * adds it to the database
	 */
	public void OkBtnClick() {
		if (inputCheck()) {
			((ReservationController) controller).createReservation(chosenGuest.getText(), calcRoom.getText(),
					arrivalDate.getValue(), departureDate.getValue(), calcPrice.getText());
			((ReservationController) controller).updateReservationList();
			((ReservationController) controller).getApp().getRoomController().reserveRoom(calcRoom.getText());
			clearAll();
		}
	}

	/**
	 * Allows to cancel reservation if a reservation
	 * is selected from reservation list
	 */
	public void onCancelBtnClick() {
		if (resTable.getSelectionModel().getSelectedItem() != null) {
			Reservation res = resTable.getSelectionModel().getSelectedItem();
			
			// Prints text for user to let them know
			// that they can change cancellation fee
			TextInputDialog dialog = new TextInputDialog("15");
			dialog.setTitle("Cancellation fee");
			dialog.setHeaderText("Select a cancellation fee");
			dialog.setContentText("Please enter cancellation fee (%):");

			Optional<String> result = dialog.showAndWait();
			
			// If user has input the percentage of the fee
			if (result.isPresent()){
				int fee = Integer.parseInt(result.get());
				((ReservationController) controller).getApp().getMainController().setCancellationFee(fee);  
			}
			// Prints bill, deletes reservation that is cancelled, sets new availability for the room
			// and updates reservation list
			try {
				((ReservationController) controller).getApp().getMainController().printBill(res, true);
				((ReservationController) controller).deleteReservation(res);
				((ReservationController) controller).getApp().getRoomController().freeRoom(res.getRoom());
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

	private void clearAll() {
		arrivalDate.setValue(null);
		departureDate.setDisable(true);
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
	
	public void suggestion(String roomNum, String price){
		if(calcRoom.getText().isEmpty() && calcPrice.getText().isEmpty()) {
		   calcRoom.setText(roomNum);
		   calcPrice.setText(price);
		}
	}
	
	public void setSelectedRoom(String roomNum) {
		calcRoom.setText(roomNum);
	}
	
	public void setSelectedPrice(String price) {
		calcPrice.setText(price);
	}

	private boolean inputCheck() {
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

	private static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

}