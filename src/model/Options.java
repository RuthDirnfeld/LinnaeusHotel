package model;

public class Options {
	public enum City {
	   VAXJO, KALMAR
	}
	
	public String dbAddress;
	public City currentCity; 

	public Options(String dbAddress, City currentCity) {
		this.dbAddress = dbAddress;
		this.currentCity = currentCity; 
	} 
	
}
