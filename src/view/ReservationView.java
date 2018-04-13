package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import controller.ReservationController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ReservationView extends View {

	@FXML
	DatePicker arrivalDate;
	@FXML
	DatePicker departureDate;
	@FXML
	Label calcNights;
	@FXML
	Label calcRoom;
	@FXML
	RadioButton oneBed;
	@FXML
	RadioButton twoBeds;
	@FXML
	RadioButton threeBeds;
	@FXML
	RadioButton fourBeds;
	@FXML
	RadioButton apartment;
	@FXML
	Button chooseGuestBtn;
	@FXML
	Button clearFieldsBtn;
	@FXML
	Button OkBtn;
	@FXML
	Button ShowBtn;
	@FXML
	ComboBox monthBox;
	@FXML
	ComboBox yearBox;


	@Override
	public Stage display() throws Exception {
		return stage;
	}

	public void initialize() {
		onArrivalClick();
		onDepartureClick();
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
						// FIXME currently calculating weird stuff
						long totalNights = ChronoUnit.DAYS.between(arrivalDate.getValue(), item);
						String temp = Objects.toString(totalNights, null);
						calcNights.setText(temp);
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
			((ReservationController) controller).getApp().getGuestController().getGuestView().show();
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