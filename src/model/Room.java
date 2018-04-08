package model;

public class Room {

	private String numBeds;
	private String roomNum;
	private boolean smoking = false;
	private String arrival = "";
	private String departure = "";
	private boolean single = false;
	private String location = "";
	private String price;
	private RoomState state;
	private RoomQuality quality;
	private String floor;
	
	
	public Room(String numBeds, boolean smoking, String arrival, String departure, boolean single, String location,
			String price, RoomState state, RoomQuality quality, String floor, String number) {
		this.numBeds = numBeds;
		this.roomNum = number;
		this.smoking = smoking;
		this.arrival = arrival;
		this.departure = departure;
		this.single = single;
		this.location = location;
		this.price = price;
		this.state = state;
		this.quality = quality;
		this.floor = floor;
	}
	
	public Room() {
		
	}


	public String getNumBeds() {
		return numBeds;
	}


	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	
	public String getRoomNum() {
		return roomNum;
	}


	public void setNumBeds(String numBeds) {
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


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
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
	
	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}

}
