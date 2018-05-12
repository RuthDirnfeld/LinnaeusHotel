package controller;

import javafx.stage.Stage;
import model.Options;
import utils.Config;
import utils.Database;

public class AppController {
	//Controllers
	private NetworkController netController = new NetworkController();
	private MainController mainController = new MainController();
	private GuestController guestController = new GuestController();
	private OptionsController optionsController = new OptionsController();
	private ReservationController resController = new ReservationController();
	private RoomController roomController = new RoomController();
	
	private Database database = new Database("", 0);
	private Config config;
	private Options options = new Options("", null);
	
	public Stage initialize() throws Exception {
		config = new Config("", options);
		
		netController.setApp(this);
		mainController.setApp(this);
		guestController.setApp(this);
		optionsController.setApp(this);
		resController.setApp(this);
		roomController.setApp(this);
		
		netController.setDatabase(database);
		guestController.setDatabase(database);
		mainController.setDatabase(database);
		optionsController.setDatabase(database);
		resController.setDatabase(database);
		roomController.setDatabase(database);
		
		
		if (!isDatabaseInit()) {
			return netController.getDbView();
		}
		else {
			String[] address = options.getDbAddress().split(":");
			database = new Database(address[0], Integer.parseInt(address[1]));
			netController.setDatabaseAddress(address[0], address[1]);

			if(!database.setUpDatabase()) {
				database = new Database("", 0);
				return netController.getDbView();
			}
		}
		return mainController.getMainView();
	}
	
	public void setDatabaseAddress(String ip, String port) {
		options.setDbAddress(ip+":"+port);
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public void setDatabase(Database database) {
		this.database = database;
	}

	public NetworkController getNetController() {
		return netController;
	}

	public MainController getMainController() {
		return mainController;
	}

	public GuestController getGuestController() {
		return guestController;
	}

	public OptionsController getOptionsController() {
		return optionsController;
	}

	public ReservationController getResController() {
		return resController;
	}

	public RoomController getRoomController() {
		return roomController;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public Options getOptions() {
		return options;
	}
	
	
	// False, init databaseview, true, init main view
	private boolean isDatabaseInit() {
		if (options.getDbAddress().isEmpty()) {
			return false;
		}
		return true;
	}
}
