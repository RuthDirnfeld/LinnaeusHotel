package model;

import java.time.LocalDate;

public class Reservation {
	private int id;
	private String guestName;
	private String room;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Reservation(int id, String guestName, String room, LocalDate startDate, LocalDate endDate) {
	/*	this.setId(++id);
		this.guest = guest;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;*/
		this.setId(++id);
		this.setGuest(guestName);
		this.setRoom(room);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuest() {
		return guestName;
	}

	public void setGuest(String guestName) {
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

}
