package utils;

import java.util.ArrayList;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Database {
	
	private String dbAddress = "";
	private int dbPort = 0;
	private MongoClient client;
	private DB database;
	
	public Database(String address, int port) {
		this.dbAddress = address;
		this.dbPort = port;
	}
	
	public void connect() {
		//Change ip and port later
		client = new MongoClient("178.62.236.241", 27017);
		database = client.getDB("hotel");
		// If database doesn't contain any collections,
		// initialize them
		if (isDbEmpty()) {
			// What else to add?
			database.getCollection("guests");
			database.getCollection("rooms");
		}
	}
	
	public boolean isConnected() {
		try {
			client.getAddress();
		} catch (Exception e) {
			System.out.println("Mongo is down");
			client.close();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Check if database has not been initialized
	 * @return true if db is empty
	 */
	private boolean isDbEmpty() {
		return !(client.listDatabaseNames().iterator().hasNext());
	}
	
	// For whatever reason
	public void closeConnection() {
		client.close();
	}

}
