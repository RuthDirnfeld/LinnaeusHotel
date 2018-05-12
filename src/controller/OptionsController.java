package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Options.City;
import view.OptionsView;

public class OptionsController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlOptions = new FXMLLoader((getClass().getResource("../view/OptionsView.fxml")));
	
	// Views OptionsController is responsible for
	private OptionsView optionsView;
	
	// Parent objects to use when initializing each view's scenes
	private Parent optionsParent;
	
	public OptionsController() {
		// Setting parents as fxml files
		try {
			optionsParent = fxmlOptions.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Connect fxml files with view classes
		optionsView = fxmlOptions.getController();
		
		// Setting parent/root objects for each view
		optionsView.setParent(optionsParent);
		
		// Setting this class to be a controller of each view this
		// controller is responsible for
		optionsView.setController(this);
		
		// Setting names of stages for each view class
		optionsView.setStage("Options");
	}
	
	public Stage getOptionsView() throws Exception {
		return optionsView.display();
	}
	
	/**
	 * Updates config file with new IP and city; and the database object
	 * with new IP, if IP is correct.
	 * @param city
	 * @param ip
	 */
	public void updateConfigFile(String city,String ip) {
		String[] fullIp = ip.split(":");
		// If port was provided
		if (fullIp.length == 2) {
			app.getOptions().setDbAddress(ip);
			app.getDatabase().setIp(fullIp[0]);
			app.getDatabase().setPort(Integer.parseInt(fullIp[1]));
			app.getOptions().setCurrentCity(City.valueOf(city.toUpperCase()));
			// Write changes to file
			app.getConfig().writeToFile();
				
		}
		// If not port provided
		else if (fullIp.length == 1){
			// Add port, since user didn't add it and assume
			// they are using default mongodb port
			app.getOptions().setDbAddress(ip+":27017");
			app.getDatabase().setIp(fullIp[0]);
			app.getDatabase().setPort(27017);
			app.getOptions().setCurrentCity(City.valueOf(city.toUpperCase()));
			// Write changes to file
			app.getConfig().writeToFile();
		}
	}
	
	/**
	 * Checks IP validity
	 * @param ip
	 * @return
	 */
    public boolean isIpValid(String ip) {
    	String[] splitIp = ip.split( "\\.");
    	// IP consists of 4 decimals, separated by dots
    	if (splitIp.length != 4) {
    		return false;
    	}
    	// Input is integers, not string or any other characters
    	// Also if integers are 0 - 255
    	for (String s : splitIp) {
    		try {
    			int integer = Integer.parseInt(s);
    			if (integer < 0 || integer > 255) {
    				return false;
    			}
    		} catch (NumberFormatException e) {
        		return false;
    		}
    	}
    	if (ip.endsWith(".") || ip.startsWith(".")) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * Checks port validity
     * @param port
     * @return
     */
    public boolean isPortValid(String port) {
    	try {
    	//Check if integer and if valid port
    		int integer = Integer.parseInt(port);
    		if (integer < 1 || integer > 65535) {
    			return false;
    		}
		} catch (NumberFormatException e) {
    		return false;
		}
    	return true;
    }
    
    public void setIp(String ip) {
    	optionsView.setIp(ip);
    }
    
    public void setCity(String city) {
    	optionsView.setCity(city);
    }
}
