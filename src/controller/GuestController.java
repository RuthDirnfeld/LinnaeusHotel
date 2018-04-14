package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Guest;
import view.GuestListView;
import view.GuestView;

public class GuestController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlGuest = new FXMLLoader((getClass().getResource("../view/GuestView.fxml")));
	private FXMLLoader fxmlGuestList = new FXMLLoader((getClass().getResource("../view/GuestListView.fxml")));
	
	
	private List<Guest> guestList = new ArrayList<Guest>();
	private GuestView guestView;
	private GuestListView guestListView;
	private Parent guestParent;
	private Parent guestListParent;
	
	public GuestController() {
		try {
			guestParent = fxmlGuest.load();
			guestListParent = fxmlGuestList.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		guestView = fxmlGuest.getController();
		guestListView = fxmlGuestList.getController();
		
		guestView.setParent(guestParent);
		guestListView.setParent(guestListParent);
		guestView.setController(this);
		guestListView.setController(this);
		
		guestView.setStage("Guest View");
		guestListView.setStage("Guest List View");
	}
	
	public void createGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
		guestList.add(g);	
  }
	
	public Stage getGuestView() throws Exception {
		return guestView.display();
	}
	
	public Stage getGuestListView() throws Exception {
		return guestListView.display();
	}
}
