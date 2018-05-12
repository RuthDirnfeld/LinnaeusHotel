package model;

public class Options {
	public enum City {
		VAXJO, KALMAR
	}

	private String dbAddress;
	private City currentCity;

	public Options(String dbAddress, City currentCity) {
		this.dbAddress = dbAddress;
		this.currentCity = currentCity; 
	}

	public String getDbAddress() {
		return dbAddress;
	}

	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}

	public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City currentCity) {
		this.currentCity = currentCity;
	} 
}
