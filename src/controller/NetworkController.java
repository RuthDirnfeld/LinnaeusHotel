package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utils.Database;
import view.DatabaseLoginView;

public class NetworkController extends Controller {
	//FXML controllers
	private FXMLLoader fxmlDb = new FXMLLoader((getClass().getResource("../view/DatabaseLoginView.fxml")));
	
	// Views NetworkController is responsible for
	private DatabaseLoginView dbLogin;
	
	// Parent objects to use when initializing each view's scenes
	private Parent dbParent;
	
	// Variables for this class only
	private Database db;
	
	public NetworkController() {
		// Setting parents as fxml files
		try {
			dbParent = fxmlDb.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Connect fxml files with view classes
		dbLogin = fxmlDb.getController();
		
		// Setting parent/root objects for each view
		dbLogin.setParent(dbParent);
		
		// Setting this class to be a controller of each view this
		// controller is responsible for
		dbLogin.setController(this);
		
		// Setting names of stages for each view class
		dbLogin.setStage("Login to Database");
	}
	
	public Stage getDbView() throws Exception {
		return dbLogin.display();
	}
	
	public void setDatabase(Database db) {
		this.db = db;
	}
	
	public boolean setDatabaseAddress(String ip, String port) {
		if (isCorrectPort(port) && isCorrectIp(ip)) {
			db.setIp(ip);
			db.setPort(Integer.parseInt(port));
			if (!db.setUpDatabase()) {
				String[] address = app.getOptions().getDbAddress().split(":");
				db.setIp(address[0]);
				db.setPort(Integer.parseInt(address[1]));
				return false;
			}
			app.setDatabaseAddress(ip, port);
			return true;
		}
		return false;
	}
	
	private boolean isCorrectPort(String port) {
		try {
			Integer.parseInt(port);
		}catch(NumberFormatException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	private boolean isCorrectIp (String ip) {
		String[] parts = ip.split("[^A-Za-z0-9]");
		if (parts.length != 4) {
			return false;
		}
		for (int i = 0; i < parts.length; i++) {
			try {
				int part = Integer.parseInt(parts[i]);
				if (part > 255 || part < 0) {
					return false;
				}
			}catch(NumberFormatException e) {
				System.err.println(e);
				return false;
			}
		}
		return true;
	}
	
	public Database getDatabase(){
		return db; 
	}
}
