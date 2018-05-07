package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Reservation;
import utils.Database;
import view.ReservationView;

public class ReservationController extends Controller {

	private FXMLLoader fxmlRes = new FXMLLoader((getClass().getResource("../view/ReservationView.fxml")));

	private ReservationView resView;
	private Parent resParent;
	private Database database;
	Controller controller = null;
	private ArrayList<Reservation> resvList;

	public ReservationController() {
		try {
			resParent = fxmlRes.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		resView = fxmlRes.getController();
		resView.setParent(resParent);
		resView.setController(this);
		resView.setStage("Reservation");
	}

	public Stage getResView() throws Exception {
		this.database = app.getDatabase();
		resvList = database.findReservations();
		resView.setTable(resvList);
		resView.initialize();
		return resView.display();
	}

	public void createReservation(String guestName, String room, LocalDate startDate, LocalDate endDate, String price) {
		Reservation res = new Reservation(guestName, room, startDate, endDate, price);
		this.database = app.getDatabase();
		database.writeReservation(res);
	}
	public void updateReservationList(){
		this.database = app.getDatabase();
		resvList = database.findReservations();
		resView.setTable(resvList);
		resView.initialize();
	}
	
	public void deleteReservation(Reservation res) {
		app.getDatabase().deleteReservation(res);
	}

	public void setSelectedGuest(String name) {
		resView.setSelectedGuest(name);
		resView.initialize();
		
	}
	public void setSelectedRoom(String roomNum) {
		resView.setSelectedRoom(roomNum);
		resView.initialize();		
	}
	public void setSelectedPrice(String price) {
		resView.setSelectedPrice(price);
		resView.initialize();		
	}
	


}
