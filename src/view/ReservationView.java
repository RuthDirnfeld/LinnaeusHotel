package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Reservation;

public class ReservationView extends Application {

	@FXML
	private DatePicker arrivalDate;
	@FXML
	private DatePicker departureDate;
	@FXML
	private Label calcNights;
	@FXML
	private Label calcRoom;
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
	private TableColumn <Reservation, String> id;
	@FXML
	private TableColumn <Reservation, String> guestName;
	@FXML
	private TableColumn<Reservation, String>  room;
	@FXML
	private TableColumn<Reservation, String>  startDate;
	@FXML
	private TableColumn<Reservation, String> endDate;
	
	public ArrayList <Reservation> resv;
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ReservationView.fxml"));
		stage.setTitle("Reservation Management");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	public void initialize() {
		onArrivalClick();
		onDepartureClick();
		if (resv != null) {
			ObservableList<Reservation> resvList = FXCollections.observableList(resv);
			resTable.setItems(resvList);
			
			id.setCellValueFactory(new PropertyValueFactory<Reservation, String> ("id"));
			guestName.setCellValueFactory(new PropertyValueFactory<Reservation, String> ("guestName"));
			room.setCellValueFactory(new PropertyValueFactory<Reservation, String> ("room"));
			startDate.setCellValueFactory(new PropertyValueFactory<Reservation, String> ("startDate"));
			endDate.setCellValueFactory(new PropertyValueFactory<Reservation, String> ("endDate"));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	// DatePicker choice validation
	public void onArrivalClick() {
		System.out.println("ArrivalClick");
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
		System.out.println("DepartureClick");
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

	public void chooseGuestClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GuestListView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearFieldsClick() {
		System.out.println("Clear Fields");
	}

	public void OkBtnClick() {
		System.out.println("OK");
	}

	public void showBtnClick() {
		System.out.println("Show");
	}

	public void onMonthBoxClick() {
		System.out.println("Months");
	}

	public void onYearBoxClick() {
		System.out.println("Years");
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