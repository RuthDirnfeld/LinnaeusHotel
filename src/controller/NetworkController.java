package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utils.Database;
import view.DatabaseLoginView;

public class NetworkController extends Controller {
	
	private FXMLLoader fxmlDb = new FXMLLoader((getClass().getResource("../view/DatabaseLoginView.fxml")));
	private Database db;
	private DatabaseLoginView dbLogin;
	private Parent dbParent;
	
	public NetworkController() {
		try {
			dbParent = fxmlDb.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbLogin = fxmlDb.getController();;
		dbLogin.setParent(dbParent);
		dbLogin.setController(this);
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
			db.connect();
			if (!db.isConnected()) {
				return false;
			}
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
		String[] parts = ip.split(".");
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
