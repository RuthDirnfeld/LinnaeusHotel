package model;

public class Guest {
	public String name;
	public String address;
	public String phoneNum;
	public String creditNumber;
	public String passportNumber;
	public boolean smoker;
	public String favRoom;

	public Guest(String name, String address, String phoneNum, String creditNumber, String passportNumber,
			boolean smoker, String favRoom) {
		this.name = name;
		this.address = address;
		this.phoneNum = phoneNum;
		this.creditNumber = creditNumber;
		this.passportNumber = passportNumber;
		this.smoker = smoker;
		this.favRoom = favRoom;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public boolean isSmoker() {
		return smoker;
	}

	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}

	public String getFavRoom() {
		return favRoom;
	}

	public void setFavRoom(String favRoom) {
		this.favRoom = favRoom;
	}

	@Override
	public String toString() {
		return "Guest [name=" + name + ", address=" + address + ", phoneNum=" + phoneNum + ", creditNumber="
				+ creditNumber + ", passportNumber=" + passportNumber + ", smoker=" + smoker + ", favRoom=" + favRoom
				+ "]";
	}
}