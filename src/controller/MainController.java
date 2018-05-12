package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import model.Bill;
import model.Reservation;
import model.RoomState;
import view.CheckInView;
import view.CheckOutView;
import view.MainView;

public class MainController extends Controller {
	
	//FXML controllers
	private FXMLLoader fxmlcheckIn = new FXMLLoader((getClass().getResource("../view/CheckInView.fxml")));
	private FXMLLoader fxmlcheckOut = new FXMLLoader((getClass().getResource("../view/CheckOutView.fxml")));
	private FXMLLoader fxmlMain = new FXMLLoader((getClass().getResource("../view/MainView.fxml")));
	
	// Views MainController is responsible for
	private CheckInView checkIn;
	private MainView mainView;
	private CheckOutView checkOut;
	
	// Parent objects to use when initializing each view's scenes
	private Parent mainParent;
	private Parent checkInParent;
	private Parent checkOutParent;
	
	// Variables for this class only
	public double cancellationFee = 0.15;
	private ArrayList<Reservation> resvList;

	public MainController() {
		
		// Setting parents as fxml files
		try {
			checkInParent = fxmlcheckIn.load();
			checkOutParent = fxmlcheckOut.load();
			mainParent = fxmlMain.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Connect fxml files with view classes
		checkIn = fxmlcheckIn.getController();
		mainView = fxmlMain.getController();
		checkOut = fxmlcheckOut.getController();
		
		// Setting this class to be a controller of each view this
		// controller is responsible for
		mainView.setController(this);
		checkIn.setController(this);
		checkOut.setController(this);
		
		// Setting parent/root objects for each view
		mainView.setParent(mainParent);
		checkIn.setParent(checkInParent);
		checkOut.setParent(checkOutParent);
		
		// Setting names of stages for each view class
		mainView.setStage("Linnaeus Hotel");
		checkIn.setStage("Check In");
		checkOut.setStage("Check Out");
		
	}
	
	public Stage getMainView() throws Exception {
		return mainView.display();
	}
	
	public Stage getDatabaseViewStage() throws Exception {
		return app.getNetController().getDbView();
	}
	
	public Stage getCheckInView() throws Exception {
		refreshCheckInView();
		return checkIn.display();
	}
	
	public Stage getCheckOutView() throws Exception {
		return checkOut.display();
	}
	
	/**
	 * Updates reservation list in case a guest has checked in
	 */
	public void refreshCheckInView(){
		resvList = app.getDatabase().findReservations();
		resvList = checkedOutRes(resvList);
		checkIn.setTable(resvList);
		checkIn.initialize();
	}
	
	/**
	 * Updates reservation list in case a gust has checked in 
	 * searching by guests' name
	 * @param s
	 */
	public void refreshCheckInView(String s){
		resvList = app.getDatabase().findReservationByName(s);
		resvList = checkedOutRes(resvList);
		checkIn.setTable(resvList);
		checkIn.initialize();
	}
	
	/**
	 * Checks out a guest and removes reservation from the database
	 * @param res
	 * @return
	 */
	public boolean checkOut(Reservation res) {
		try {
			// Prints bill first, then deletes reservation
			printBill(res,false);
			database.deleteReservation(res);
			// Updates room state. Room can be reserved by another person
			// for another date.
			if(database.findReservationByRoom(res.getRoom()).isEmpty()) {
				database.updateRoomState(res.getRoom(), RoomState.free);
			}else{
				database.updateRoomState(res.getRoom(), RoomState.reserved);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Returns reservations that are currently checked in
	 * @return
	 */
	public ArrayList<Reservation> getCheckedInReservations() {
		return database.findCheckedInReservations();
	}

	/**
	 *  Performs check in by updating room state and updating database
	 */
	public void checkIn(Reservation res) {
		res.setCheckedIn(true);
		database.updateRoomState(res.getRoom(), RoomState.allocated);
		database.updateReservationState(res);
	}
	
	
	/**
	 * Creates a bill information about guest's stay that is written into a file as a bill
	 * @param res
	 * @param cancellation if reservation is cancelled, set to true
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void printBill(Reservation res, Boolean cancellation) throws FileNotFoundException, UnsupportedEncodingException {
	    Bill bill =  new Bill(res.getGuestName(), Integer.parseInt(res.getPrice()), res.getStartDate(), res.getEndDate(),cancellation);
		String fileName = bill.getGuestName() + bill.getArrival().toString() + "-" + bill.getDeparture() + ".txt";
		
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		
		writer.println("Bill for " + bill.getGuestName());
		writer.println("-------------------------------------");
		// If bill is printed for cancelled reservation
		if(bill.isCancellation()) {
			double temp = (double) cancellationFee*100;
			writer.println("Cancelled Reservation from " + bill.getArrival().toString() + " to " + bill.getDeparture().toString()+ " in room " + res.getRoom());
			writer.println("Room Price : " + bill.getRoomPrice());
			writer.println("Cancellation fee ( "+temp+ "% of total room reservation price) : " + bill.calculateBill(cancellationFee));
			writer.println("Total : " + bill.calculateBill(cancellationFee) + "SEK");
		}
		// If bill is printed after check out
		else{
			writer.println("Stay from " + bill.getArrival().toString() + " to " + bill.getDeparture().toString() + " in room " + res.getRoom());
			writer.println("Room price : " + bill.getRoomPrice() + " SEK (per night)"); 
			writer.println("Total Price : " + bill.calculateBill() + "SEK"); 
		}
		writer.close();
	}

	/**
	 * Simply sets cancellation fee.
	 * @param i
	 */
	public void setCancellationFee(double i) {
		 cancellationFee = (double) i/100; 
	}
	
	/**
	 * Generates array of guests that are not checked in and the room
	 * they reserved is for this day
	 * @param resvList
	 * @return
	 */
	private ArrayList<Reservation> checkedOutRes(ArrayList<Reservation> resvList) {
		ArrayList<Reservation> temp = new ArrayList<Reservation>(); 
		if(!resvList.isEmpty())
			for(int i = 0; i < resvList.size(); i++){
				if(resvList.get(i).getCheckedIn() == false && resvList.get(i).getStartDate().equals(LocalDate.now())) {
					temp.add(resvList.get(i));
			}
		}
		return temp;
	}
}


