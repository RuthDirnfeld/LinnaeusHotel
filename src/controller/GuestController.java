package controller;


import java.util.ArrayList;
import java.util.List;

import model.Guest;

public class GuestController {
List<Guest> guestList = new ArrayList<Guest>();
	
public void createGuest(String name, String birthDate, String company, String citizenship, String address, boolean smoker, String email, String phoneNum, String favRoom, String creditNumber) {
Guest g = new Guest(name,birthDate,company,citizenship,address,smoker,email, phoneNum,favRoom,creditNumber);
System.out.println(g.toString());
guestList.add(g);

}
}
