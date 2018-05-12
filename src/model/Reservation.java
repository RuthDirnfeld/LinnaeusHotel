package model;

import java.time.LocalDate;

public class Reservation {
	
	private String guestName;
	private String room;
	private LocalDate startDate;
	private LocalDate endDate;
	private String price;
	private Boolean checkedIn; 
	
	public Reservation(String guestName, String room, LocalDate startDate, LocalDate endDate, String price) {
		this.guestName = guestName;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		checkedIn = false; 
	}
	

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setCheckedIn(Boolean bool) {
		checkedIn = bool; 
	}
	public boolean getCheckedIn() {
		return checkedIn; 
	}
}
