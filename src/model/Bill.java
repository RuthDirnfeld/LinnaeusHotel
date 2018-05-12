package model;

import java.time.LocalDate;

public class Bill {
	
	private String guestName = "";
	private int roomPrice = 0;
	private LocalDate arrival;
	private LocalDate departure;
	private boolean cancellation;
	
	public Bill(String guestName, int price, LocalDate arrival, LocalDate departure, boolean cancellation) {
		this.guestName = guestName;
		this.roomPrice = price;
		this.arrival = arrival;
		this.departure = departure;
		this.cancellation = cancellation; 
	}
	
	/**
	 * Calculates bill taking into consideration how much time
	 * the guest has spent in the room (the day of check out)
	 * @return
	 */
	public int calculateBill() {
		long days = java.time.temporal.ChronoUnit.DAYS.between(arrival, departure);
		if(!cancellation){
			return (int)days * roomPrice;
		}else{
			return (int) ((int)(days * roomPrice) * 0.15);	
		}
	}
	
	/**
	 * Calculates bill with cancellation fee
	 * @param cancellationFee
	 * @return
	 */
	public double calculateBill(double cancellationFee) {
		long days = java.time.temporal.ChronoUnit.DAYS.between(arrival, departure);
		if(!cancellation){
			return (int)days * roomPrice;
		}else{
			return ((days * roomPrice) * cancellationFee);	
		}
	}
	
	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}

	public LocalDate getArrival() {
		return arrival;
	}

	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}

	public LocalDate getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDate departure) {
		this.departure = departure;
	}

	public boolean isCancellation() {
		return cancellation;
	}

	public void setCancellation(boolean cancellation) {
		this.cancellation = cancellation;
	}
}

