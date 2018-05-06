package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Options.City;
import view.OptionsView;

public class OptionsController extends Controller {
	
	private FXMLLoader fxmlOptions = new FXMLLoader((getClass().getResource("../view/OptionsView.fxml")));
	private OptionsView optionsView;
	private Parent optionsParent;
	
	public OptionsController() {
		try {
			optionsParent = fxmlOptions.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		optionsView = fxmlOptions.getController();
		optionsView.setParent(optionsParent);
		optionsView.setController(this);
		optionsView.setStage("Options");
	}
	
	public Stage getOptionsView() throws Exception {
		return optionsView.display();
	}
	
	public boolean createConfigFile(String city,String ip) {
		String[] fullIp = ip.split(":");
		if (fullIp.length == 2) {
			if (isIpValid(fullIp[0]) && isPortValid(fullIp[1])) {
				app.getOptions().setDbAddress(ip);
				app.getDatabase().setIp(fullIp[0]);
				app.getDatabase().setPort(Integer.parseInt(fullIp[1]));
				app.getOptions().setCurrentCity(City.valueOf(city.toUpperCase()));
				app.getConfig().writeToFile();
				return true;
			}
			else {
				return false;
			}
		}
		else if (fullIp.length == 1){
			if (isIpValid(fullIp[0])){
				app.getOptions().setDbAddress(ip+":27017");
				app.getDatabase().setIp(fullIp[0]);
				app.getDatabase().setPort(27017);
				app.getOptions().setCurrentCity(City.valueOf(city.toUpperCase()));
				app.getConfig().writeToFile();
				return true;
			}
			return false;
		}
		
		else {
			return false;
		}
		
	}
	
	public boolean connectToDatabase() {
		return app.getDatabase().updateConnection();
	}
	
	// Check IP validity 
    private boolean isIpValid(String ip) {
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
    
    // Check port validity
    private boolean isPortValid(String port) {
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
}
