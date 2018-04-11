package controller;

import java.util.ArrayList;
import java.util.Arrays;

import model.Guest;

public class GuestController {
	private ArrayList <Guest> guestList = new ArrayList<Guest>();
	
	public void createGuest(String name, String address, String phoneNum, String creditNum, String passportNum,
			boolean smoker, String favRoom) {
		Guest g = new Guest(name, address, phoneNum, creditNum, passportNum, smoker, favRoom);
			System.out.println(g.toString());
			guestList.add(g);		
	}
}
