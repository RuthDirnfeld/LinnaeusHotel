package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.ReservationView;

public class ReservationController extends Controller {
	
	private FXMLLoader fxmlRes = new FXMLLoader((getClass().getResource("../view/ReservationView.fxml")));
	
	private ReservationView resView;
	private Parent resParent;
	
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
		return resView.display();
	}

}
