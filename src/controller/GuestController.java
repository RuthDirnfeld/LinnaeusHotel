package controller;

import model.Guest;

public class GuestController {
	
public void createGuest(String name, String birthDate, String company, String citizenship, String address, boolean smoker, String email, String phoneNum, String favRoom, String creditNumber) {
Guest g = new Guest(name,birthDate,company,citizenship,address,smoker,email, phoneNum,favRoom,creditNumber);
System.out.println(g.toString());
}
}
