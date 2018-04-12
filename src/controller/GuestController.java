package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import model.Guest;
import view.View;

public class GuestController extends Controller {
	private List<Guest> guestList = new ArrayList<Guest>();
	private View checkInView;
	private View checkOutView;
	
  public void createGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
  Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
  guestList.add(g);	
  }
	
	public void setCheckInView(View view) {
		this.checkInView = view;
	}
	
	public void setCheckOutView(View view) {
		this.checkOutView = view;
	}
	
	public Stage getCheckInView() throws Exception {
		return checkInView.display();
	}
	
	public Stage getCheckOutView() throws Exception {
		return checkOutView.display();
	}
}
