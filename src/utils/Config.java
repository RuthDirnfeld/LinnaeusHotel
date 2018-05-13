package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.Options;

public class Config {
	private String filePath;
	private Options options;
	
	public Config (String filePath, Options options) {
		this.filePath = filePath;
		this.options = options;
		// If config file doesn't exist yet, create it
		if (!isCreated()) {
			initializeConfig();
		}
		// File exists, get what's written into the system
		else {
			try {
				getFromFile();
				
			} catch (JSONException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// Checks if config file empty
	private boolean isCreated() {
		return (new java.io.File(filePath + "config.json").exists()) && (new java.io.File(filePath + "config.json").length() > 0);
	}
	
	// Config file doesn't exist, create new one
	private void initializeConfig() {
		// Create json obects for city and ip
		JSONObject file = new JSONObject();
		file.put("city", "");
		file.put("ip", "");
		
		// Write to new file
		try {
			FileWriter fr = new FileWriter(filePath + "config.json");
			fr.write(file.toString());
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get information from config file and save it in options object
	 * @throws JSONException
	 * @throws FileNotFoundException
	 */
	public void getFromFile() throws JSONException, FileNotFoundException {
		options.setDbAddress(getDbIp());
		// Set city depending on what city is selected in the file
		if (getCity().toLowerCase().equals(model.Options.City.VAXJO.toString().toLowerCase())) {
			options.setCurrentCity(model.Options.City.VAXJO);
		}else if (getCity().isEmpty()){
			options.setCurrentCity(null);
		}
		else {
			options.setCurrentCity(model.Options.City.KALMAR);
		}
		
	}
	
	/**
	 * Get information from options object and write it to config file
	 */
	public void writeToFile() {
		JSONObject file = new JSONObject();
		file.put("city", options.getCurrentCity().name());
		file.put("ip", options.getDbAddress());
		try {
			FileWriter fr = new FileWriter(filePath + "config.json");
			fr.write(file.toString());
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getCity() throws JSONException, FileNotFoundException {
		String city = "";
		JSONObject json = new JSONObject(new JSONTokener(new FileReader(filePath + "config.json")));
		city = (String) json.get("city");
		
		return city;
	}
	
	private String getDbIp() throws JSONException, FileNotFoundException {
		String ip = "";
		JSONObject json = new JSONObject(new JSONTokener(new FileReader(filePath + "config.json")));
		ip = (String) json.get("ip");
		
		return ip;
	}
	
}
