package controller;


import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Guest;
import model.Room;
import utils.Database;
import view.GuestListView;
import view.GuestView;
import view.ModifyGuestView;

public class GuestController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlGuest = new FXMLLoader((getClass().getResource("../view/GuestView.fxml")));
	private FXMLLoader fxmlGuestList = new FXMLLoader((getClass().getResource("../view/GuestListView.fxml")));
	private FXMLLoader fxmlModify = new FXMLLoader((getClass().getResource("../view/ModifyGuestView.fxml")));
	
	private GuestView guestView;
	private GuestListView guestListView;
	private ModifyGuestView modifyGuest;
	private Parent guestParent;
	private Parent guestListParent;
	private Parent modifyParent;
	private Database database;
	Controller controller = null;
	private ArrayList<Guest> guests;
	
	public GuestController() {
		try {
			guestParent = fxmlGuest.load();
			guestListParent = fxmlGuestList.load();
			modifyParent = fxmlModify.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		guestView = fxmlGuest.getController();
		guestListView = fxmlGuestList.getController();
		modifyGuest = fxmlModify.getController();
		
		guestView.setParent(guestParent);
		guestListView.setParent(guestListParent);
		modifyGuest.setParent(modifyParent);
		guestView.setController(this);
		guestListView.setController(this);
		modifyGuest.setController(this);
		
		guestView.setStage("Guest View");
		guestListView.setStage("Guest List View");
		modifyGuest.setStage("Modify Guest");
	}
	
	public void createGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
	   database.writeGuest(g);
	}
	
	public void modifyGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
		database.updateGuestFavRoom(g.getFavRoom(), g);
	}
	
	public void refreshGuestList() {
		guests = database.findGuests();
		guestListView.setTable(guests);
		guestListView.initialize();
	}
	
	public ArrayList<Room> getAllRooms() {
		return database.findRooms();
	}
	
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public Stage getGuestView() throws Exception {
		return guestView.display();
	}
	
	public Stage getModifyGuestView() throws Exception {
		return modifyGuest.display();
	}
	
	public ModifyGuestView getModifyGuestController() {
		return modifyGuest;
	}
	
	public Stage getGuestListView() throws Exception {
		guests = database.findGuests();
		guestListView.setTable(guests);
		guestListView.initialize();
		return guestListView.display();
	}
	
	public void updateGuestList(){
		guests = database.findGuests();
		guestListView.setTable(guests);
		guestListView.initialize();
	}
	public void updateGuestList(String s){
		guests = database.findGuestByName(s);
		guestListView.setTable(guests);
		guestListView.initialize();
	}
	
}
