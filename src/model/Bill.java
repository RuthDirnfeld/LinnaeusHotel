package model;

import java.time.LocalDate;

public class Bill {
	
	private String guestName = "";
	private int roomPrice = 0;
	private LocalDate arrival;
	private LocalDate departure;
	
	public Bill(String guestName, int price, LocalDate arrival, LocalDate departure) {
		this.guestName = guestName;
		this.roomPrice = price;
		this.arrival = arrival;
		this.departure = departure;
	}
	
	public String getBill() {
		String bill = guestName + "\n"
				+ "Arrival: " + arrival.toString()
				+ "Departure: " + departure.toString()
				+ "Price: " + calculateBill();
		return bill;
	}
	
	private int calculateBill() {
		long days = java.time.temporal.ChronoUnit.DAYS.between(arrival, departure);
		return (int)days * roomPrice;
	}

}
