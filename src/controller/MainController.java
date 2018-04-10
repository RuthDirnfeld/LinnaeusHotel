package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Options;
import utils.Config;
import utils.Database;
import view.CheckInView;
import view.CheckOutView;
import view.DatabaseLoginView;
import view.GuestListView;
import view.GuestView;
import view.MainView;
import view.OptionsView;
import view.ReservationView;
import view.RoomView;

public class MainController extends Controller {
	
	// Main class of everything
	
	//Controllers
	private NetworkController netController = new NetworkController();
	private GuestController guestController = new GuestController();
	private OptionsController optionsController = new OptionsController();
	private ReservationController resController = new ReservationController();
	private RoomController roomController = new RoomController();
	
	private Database database;
	private Config config;
	private Options options = new Options("", null);
	
	//FXML loaders
	private FXMLLoader fxmlDb = new FXMLLoader((getClass().getResource("../view/DatabaseLoginView.fxml")));
	private FXMLLoader fxmlcheckIn = new FXMLLoader((getClass().getResource("../view/CheckInView.fxml")));
	private FXMLLoader fxmlcheckOut = new FXMLLoader((getClass().getResource("../view/CheckOutView.fxml")));
	private FXMLLoader fxmlMain = new FXMLLoader((getClass().getResource("../view/MainView.fxml")));
	private FXMLLoader fxmlGuest = new FXMLLoader((getClass().getResource("../view/GuestView.fxml")));
	private FXMLLoader fxmlGuestList = new FXMLLoader((getClass().getResource("../view/GuestListView.fxml")));
	private FXMLLoader fxmlOptions = new FXMLLoader((getClass().getResource("../view/OptionsView.fxml")));
	private FXMLLoader fxmlRes = new FXMLLoader((getClass().getResource("../view/ReservationView.fxml")));
	private FXMLLoader fxmlRoom = new FXMLLoader((getClass().getResource("../view/RoomView.fxml")));
	
	// Views
	private DatabaseLoginView dbLogin;
	private CheckInView checkIn;
	private MainView mainView;
	private CheckOutView checkOut;
	private GuestView guestView;
	private GuestListView guestList;
	private OptionsView optionsView;
	private ReservationView resView;
	private RoomView roomView;
	
	//Parents
	private Parent dbParent;
	private Parent mainParent;
	private Parent checkInParent;
	private Parent checkOutParent;
	private Parent guestParent;
	private Parent guestListParent;
	private Parent optionsParent;
	private Parent resParent;
	private Parent roomParent;

	
	// First method ran
	public void initialize() {
		config = new Config("", options);
		
		//Load loaders
		try {
			dbParent = fxmlDb.load();
			checkInParent = fxmlcheckIn.load();
			checkOutParent = fxmlcheckOut.load();
			mainParent = fxmlMain.load();
			optionsParent = fxmlOptions.load();
			guestParent = fxmlGuest.load();
			guestListParent = fxmlGuestList.load();
			resParent = fxmlRes.load();
			roomParent = fxmlRoom.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Init views
		dbLogin = fxmlDb.getController();
		checkIn = fxmlcheckIn.getController();
		mainView = fxmlMain.getController();
		checkOut = fxmlcheckOut.getController();
		guestView = fxmlGuest.getController();
		guestList = fxmlGuestList.getController();
		optionsView = fxmlOptions.getController();
		resView = fxmlRes.getController();
		roomView = fxmlRoom.getController(); 
		
		//Set views
		netController.setView(dbLogin);
		this.setView(mainView);
		guestController.setView(guestView);
		guestController.setCheckInView(checkIn);
		guestController.setCheckOutView(checkOut);
		resController.setListView(guestList);
		optionsController.setView(optionsView);
		resController.setView(resView);
		roomController.setView(roomView);
		
		
		// Set controllers
		dbLogin.setController(netController);
		mainView.setController(this);
		checkIn.setController(guestController);
		checkOut.setController(guestController);
		guestView.setController(guestController);
		guestList.setController(resController);
		optionsView.setController(optionsController);
		resView.setController(resController);
		roomView.setController(roomController);

		// Set parents
		dbLogin.setParent(dbParent);
		mainView.setParent(mainParent);
		checkIn.setParent(checkInParent);
		checkOut.setParent(checkOutParent);
		guestView.setParent(guestParent);
		guestList.setParent(guestListParent);
		optionsView.setParent(optionsParent);
		resView.setParent(resParent);
		roomView.setParent(roomParent);
		
	}
	// False, init databaseview, true, init main view
	public boolean isDatabaseInit() {
		if (options.getDbAddress().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public Stage getDatabaseViewStage() throws Exception {
		return netController.getDisplay();
	}
	
	public NetworkController getNetController() {
		return netController;
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
