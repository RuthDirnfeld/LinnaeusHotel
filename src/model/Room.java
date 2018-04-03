package model;

public class Room {
	
	private int numBeds = 0;
	private boolean smoking = false;
	private String arrival = "";
	private String departure = "";
	private boolean single = false;
	private String location = "";
	private int price = 0;
	private RoomState state;
	private RoomQuality quality;
	
	
	public Room() {
		
	}


	public int getNumBeds() {
		return numBeds;
	}


	public void setNumBeds(int numBeds) {
		this.numBeds = numBeds;
	}


	public boolean isSmoking() {
		return smoking;
	}


	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}


	public String getArrival() {
		return arrival;
	}


	public void setArrival(String arrival) {
		this.arrival = arrival;
	}


	public String getDeparture() {
		return departure;
	}


	public void setDeparture(String departure) {
		this.departure = departure;
	}


	public boolean isSingle() {
		return single;
	}


	public void setSingle(boolean single) {
		this.single = single;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public RoomState getState() {
		return state;
	}


	public void setState(RoomState state) {
		this.state = state;
	}


	public RoomQuality getQuality() {
		return quality;
	}


	public void setQuality(RoomQuality quality) {
		this.quality = quality;
	}
	
	

}
