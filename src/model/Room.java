package model;

public class Room {

	public String roomNum;
	public boolean singleRoom;
	public boolean doubleRoom;
	public boolean doubleKingRoom;
	public boolean apartment;
	public boolean largeRooms;
	public boolean view;
	public boolean smokerRoom;
	public String price;

	public Room(String roomNum, boolean singleRoom, boolean doubleRoom, boolean doubleKingRoom, boolean apartment,
			boolean largeRooms, boolean view, boolean smokerRoom, String price) {
		this.roomNum = roomNum;
		this.singleRoom = singleRoom;
		this.doubleRoom = doubleRoom;
		this.doubleKingRoom = doubleKingRoom;
		this.apartment = apartment;
		this.largeRooms = largeRooms;
		this.view = view;
		this.smokerRoom = smokerRoom;
		this.price = price;
	}

	public Room() {

	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public boolean isSingleRoom() {
		return singleRoom;
	}

	public void setSingleRoom(boolean singleRoom) {
		this.singleRoom = singleRoom;
	}

	public boolean isDoubleRoom() {
		return doubleRoom;
	}

	public void setDoubleRoom(boolean doubleRoom) {
		this.doubleRoom = doubleRoom;
	}

	public boolean isDoubleKingRoom() {
		return doubleKingRoom;
	}

	public void setDoubleKingRoom(boolean doubleKingRoom) {
		this.doubleKingRoom = doubleKingRoom;
	}

	public boolean isApartment() {
		return apartment;
	}

	public void setApartment(boolean apartment) {
		this.apartment = apartment;
	}

	public boolean isLargeRooms() {
		return largeRooms;
	}

	public void setLargeRooms(boolean largeRooms) {
		this.largeRooms = largeRooms;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isSmokerRoom() {
		return smokerRoom;
	}

	public void setSmokerRoom(boolean smokerRoom) {
		this.smokerRoom = smokerRoom;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
