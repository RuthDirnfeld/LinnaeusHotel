package controller;

import utils.Database;

public class NetworkController extends Controller {
	
	private Database db;
	
	public NetworkController() {
		
	}
	
	public void setDatabase(Database db) {
		this.db = db;
	}
	
	public boolean setDatabase(String ip, String port) {
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

}
