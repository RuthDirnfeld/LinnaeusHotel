package controller;

import utils.Database;

public abstract class Controller {
	
	protected AppController app;
	protected Database database;
	
	public void setApp(AppController app) {
		this.app = app;
	}
	
	public AppController getApp() {
		return app;
	}
	
	public void setDatabase(Database db) {
		this.database = db;
	}
	
	public Database getDatabase() {
		return this.database;
	}
}
