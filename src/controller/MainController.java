package controller;

import javafx.stage.Stage;
import model.Options;
import utils.Config;
import utils.Database;
import view.DatabaseLoginView;

public class MainController {
	
	// Main class of everything
	
	//Controllers
	private static NetworkController netController = new NetworkController();
	
	private Database database;
	private Config config;
	private Options options = new Options("", null);
	private DatabaseLoginView dbLogin = new DatabaseLoginView();
	
	// First method ran
	public void initialize() {
		config = new Config("", options);
		dbLogin.setNetController(netController);
	}
	// False, init databaseview, true, init main view
	public boolean isDatabaseInit() {
		if (options.getDbAddress().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public Stage getDatabaseViewStage() throws Exception {
		return dbLogin.displayDbLogin();
	}
	public static NetworkController getNetController() {
		return netController;
	}
	public static void setNetController(NetworkController netController) {
		MainController.netController = netController;
	}
	public Database getDatabase() {
		return database;
	}
	public void setDatabase(Database database) {
		this.database = database;
	}
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}

}
