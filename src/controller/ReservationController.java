package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Reservation;
import view.ReservationView;

public class ReservationController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlRes = new FXMLLoader((getClass().getResource("../view/ReservationView.fxml")));

	// Views ReservationController is responsible for
	private ReservationView resView;
	
	// Parent objects to use when initializing each view's scenes
	private Parent resParent;
	
	// Variables for this class only
	private ArrayList<Reservation> resvList;

	public ReservationController() {
		// Setting parents as fxml files
		try {
			resParent = fxmlRes.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Connect fxml files with view classes
		resView = fxmlRes.getController();
		
		// Setting parent/root objects for each view
		resView.setParent(resParent);
		
		// Setting this class to be a controller of each view this
		// controller is responsible for
		resView.setController(this);
		
		// Setting names of stages for each view class
		resView.setStage("Reservation");
	}

	/**
	 * Returns the reservation UI and updates list of reservations
	 * in case there has been changes made
	 * @return
	 * @throws Exception
	 */
	public Stage getResView() throws Exception {
		updateReservationList();
		return resView.display();
	}

	/**
	 * Creates new reservation object and writes it to database
	 * @param guestName
	 * @param room
	 * @param startDate
	 * @param endDate
	 * @param price
	 */
	public void createReservation(String guestName, String room, LocalDate startDate, LocalDate endDate, String price) {
		Reservation res = new Reservation(guestName, room, startDate, endDate, price);
		database.writeReservation(res);
	}
	
	/**
	 * Updates reservation list by retrieving reservations
	 * from the database
	 */
	public void updateReservationList(){
		resvList = database.findReservations();
		resView.setTable(resvList);
		resView.initialize();
	}
	
	/**
	 * Deletes reservation from the database
	 * @param res
	 */
	public void deleteReservation(Reservation res) {
		database.deleteReservation(res);
	}

	/**
	 * Set guest selected from guest list as
	 * guest to start reservation with
	 * @param name
	 */
	public void setSelectedGuest(String name) {
		resView.setSelectedGuest(name);
		resView.initialize();
		
	}
	
	/**
	 * 
	 * @param room
	 * @param price
	 */
	public void suggest(String room, String price) {
		resView.suggestion(room, price);
		resView.initialize();	
	}
	/**
	 * Set room in the reservation if any room was selected from
	 * the room list
	 * @param roomNum
	 */
	public void setSelectedRoom(String roomNum) {
		resView.setSelectedRoom(roomNum);
		resView.initialize();		
	}
	
	/**
	 * Sets predefined price for a selected room
	 * @param price
	 */
	public void setSelectedPrice(String price) {
		resView.setSelectedPrice(price);
		resView.initialize();		
	}
	


}
