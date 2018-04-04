package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReservationView extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ReservationView.fxml"));
		stage.setTitle("Reservation Management");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void singleRoomClick() {
		System.out.println("Single Room");
	}
	
	public void doubleRoomClick() {
		System.out.println("Double Room");
	}
	
	public void tripleRoomClick() {
		System.out.println("Triple Room");
	}
	
	public void fourBedClick() {
		System.out.println("Four Bed Room");
	}
	
	public void apartmentClick() {
		System.out.println("Apartment");
	}
	
	public void chooseGuestClick() {
		System.out.println("Choose Guest");
	}
	
	public void clearFieldsClick() {
		System.out.println("Clear Fields");
	}
	
	public void OkBtnClick() {
		System.out.println("OK");
	}
	
	public void showBtnClick() {
		System.out.println("Show");
	}
}