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
	
	public void createGuest(String name, String birthDate, String company, String citizenship, String address, boolean smoker, String email, String phoneNum, String favRoom, String creditNumber) {
		Guest g = new Guest(name,birthDate,company,citizenship,address,smoker,email, phoneNum,favRoom,creditNumber);
		System.out.println(g.toString());
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
