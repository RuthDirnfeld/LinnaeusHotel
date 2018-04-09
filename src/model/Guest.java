package model;

public class Guest {
	public String name;
	public String company;
	public String birthDate;
	public String citizenship;
	public String address;
	public boolean smoker;
	public String email;
	public String phoneNum;
	public String favRoom;
	public String creditNumber;

	public Guest(String name, String birthDate, String company, String citizenship, String address, boolean smoker,
			String email, String phoneNum, String favRoom, String creditNumber) {
		this.name = name;
		this.birthDate = birthDate;
		this.company = company;
		this.citizenship = citizenship;
		this.address = address;
		this.smoker = smoker;
		this.email = email;
		this.phoneNum = phoneNum;
		this.favRoom = favRoom;
		this.creditNumber = creditNumber;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isSmoker() {
		return smoker;
	}

	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getFavRoom() {
		return favRoom;
	}

	public void setFavRoom(String favRoom) {
		this.favRoom = favRoom;
	}

	@Override
	public String toString() {
		return "Guest [name=" + name + ", company=" + company + ", birthDate=" + birthDate + ", citizenship="
				+ citizenship + ", address=" + address + ", smoker=" + smoker + ", email=" + email + ", phoneNum="
				+ phoneNum + ", favRoom=" + favRoom + "]";
	}

}
