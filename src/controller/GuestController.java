package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Guest;
import model.Room;
import view.GuestListView;
import view.GuestView;
import view.ModifyGuestView;

public class GuestController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlGuest = new FXMLLoader((getClass().getResource("../view/GuestView.fxml")));
	private FXMLLoader fxmlGuestList = new FXMLLoader((getClass().getResource("../view/GuestListView.fxml")));
	private FXMLLoader fxmlModify = new FXMLLoader((getClass().getResource("../view/ModifyGuestView.fxml")));
	
	// Views GuestController is responsible for
	private GuestView guestView;
	private GuestListView guestListView;
	private ModifyGuestView modifyGuest;
	
	// Parent objects to use when initializing each view's scenes
	private Parent guestParent;
	private Parent guestListParent;
	private Parent modifyParent;
	
	// Variables for this class only
	private ArrayList<Guest> guests;
	
	public GuestController() {
		// Setting parents as fxml files
		try {
			guestParent = fxmlGuest.load();
			guestListParent = fxmlGuestList.load();
			modifyParent = fxmlModify.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Connect fxml files with view classes
		guestView = fxmlGuest.getController();
		guestListView = fxmlGuestList.getController();
		modifyGuest = fxmlModify.getController();
		
		// Setting parent/root objects for each view
		guestView.setParent(guestParent);
		guestListView.setParent(guestListParent);
		modifyGuest.setParent(modifyParent);
		
		// Setting this class to be a controller of each view this
		// controller is responsible for
		guestView.setController(this);
		guestListView.setController(this);
		modifyGuest.setController(this);
		
		// Setting names of stages for each view class
		guestView.setStage("Guest View");
		guestListView.setStage("Guest List View");
		modifyGuest.setStage("Modify Guest");
	}
	
	/**
	 * Creates new guest and saves it in the database.
	 * @param name
	 * @param address
	 * @param phoneNum
	 * @param creditNum
	 * @param passportNum
	 * @param smoker
	 * @param favRoom
	 */
	public void createGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
	   database.writeGuest(g);
	}
	
	/**
	 * Updates guests' favorite room. Can be updated to updated more than a room.
	 * @param name
	 * @param address
	 * @param phoneNum
	 * @param creditNum
	 * @param passportNum
	 * @param smoker
	 * @param favRoom
	 */
	public void modifyGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
		database.updateGuestFavRoom(g.getFavRoom(), g);
	}
	
	/**
	 * Updates guest list displayed for the user in case there has 
	 * been a change in the list.
	 */
	public void refreshGuestList() {
		guests = database.findGuests();
		guestListView.setTable(guests);
		guestListView.initialize();
	}
	
	/**
	 * Returns all rooms from the database
	 * @return array of Room objects
	 */
	public ArrayList<Room> getAllRooms() {
		return database.findRooms();
	}
	
	/**
	 * Returns a stage of Guest View UI
	 * @return
	 * @throws Exception
	 */
	public Stage getGuestView() throws Exception {
		return guestView.display();
	}
	
	/**
	 * Returns a stage of Modify Guest UI
	 * @return
	 * @throws Exception
	 */
	public Stage getModifyGuestView() throws Exception {
		return modifyGuest.display();
	}
	
	/**
	 * Returns ModifyGuestView class
	 * @return
	 */
	public ModifyGuestView getModifyGuestController() {
		return modifyGuest;
	}
	
	
	/**
	 * Refreshes guest view in case of an update and returns 
	 * and entire stage of Guest List
	 * @return
	 * @throws Exception
	 */
	public Stage getGuestListView() throws Exception {
		refreshGuestList();
		return guestListView.display();
	}
	
	//TODO possibly broken
	/**
	 * In case there is a search by name, guest list is
	 * updated with guests containing specified name
	 * @param s
	 */
	public void updateGuestList(String s){
		guests = database.findGuestByName(s);
		guestListView.setTable(guests);
		guestListView.initialize();
	}
	
}
