package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Reservation;
import view.CheckInView;
import view.CheckOutView;
import view.MainView;

public class MainController extends Controller {
	
	//FXML loaders
	private FXMLLoader fxmlcheckIn = new FXMLLoader((getClass().getResource("../view/CheckInView.fxml")));
	private FXMLLoader fxmlcheckOut = new FXMLLoader((getClass().getResource("../view/CheckOutView.fxml")));
	private FXMLLoader fxmlMain = new FXMLLoader((getClass().getResource("../view/MainView.fxml")));
	
	// Views
	private CheckInView checkIn;
	private MainView mainView;
	private CheckOutView checkOut;
	
	//Parents
	private Parent mainParent;
	private Parent checkInParent;
	private Parent checkOutParent;
	
	
	private ArrayList<Reservation> resvList;

	public MainController() {
		
		//Load loaders
		try {
			checkInParent = fxmlcheckIn.load();
			checkOutParent = fxmlcheckOut.load();
			mainParent = fxmlMain.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Init views
		checkIn = fxmlcheckIn.getController();
		mainView = fxmlMain.getController();
		checkOut = fxmlcheckOut.getController();
		
		// Set controllers
		mainView.setController(this);
		checkIn.setController(this);
		checkOut.setController(this);
		
		//Set databases
		

		// Set parents
		mainView.setParent(mainParent);
		checkIn.setParent(checkInParent);
		checkOut.setParent(checkOutParent);
		
		//Set stages
		mainView.setStage("Linnaeus Hotel");
		checkIn.setStage("Check In");
		checkOut.setStage("Check Out");
		
	}
	
	public Stage getMainView() throws Exception {
		return mainView.display();
	}
	
	public Stage getDatabaseViewStage() throws Exception {
		System.out.println(app.getNetController().getDbView() == null);
		return app.getNetController().getDbView();
	}
	
	public ArrayList<Reservation> checkedOutRes(ArrayList<Reservation> resvList) {
		ArrayList<Reservation> temp = new ArrayList<Reservation>(); 
		if(!resvList.isEmpty())
		for(int i = 0; i < resvList.size(); i++){
			if(resvList.get(i).getCheckedIn() == false && resvList.get(i).getStartDate().equals(LocalDate.now())) {
				temp.add(resvList.get(i));
			}
		}
		return temp;
	}
	
	public void refreshCheckInView(){
		resvList = app.getDatabase().findReservation();
		resvList = checkedOutRes(resvList);
		checkIn.setTable(resvList);
		checkIn.initialize();
	}
	
	public void refreshCheckInView(String s){
		resvList = app.getDatabase().findReservationByName(s);
		resvList = checkedOutRes(resvList);
		checkIn.setTable(resvList);
		checkIn.initialize();
	}
	
	public Stage getCheckInView() throws Exception {
		resvList = app.getDatabase().findReservation();
		resvList = checkedOutRes(resvList);
		checkIn.setTable(resvList);
		checkIn.initialize();
		return checkIn.display();
	}
	
	public Stage getCheckOutView() throws Exception {
		return checkOut.display();
	}

	public void checkIn(Reservation res) {
	app.getDatabase().deleteReservation(res);
	res.setCheckedIn(true);
	app.getDatabase().writeReservation(res);
		
	}

}
