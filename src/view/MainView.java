package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView extends Application {

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
		stage.setTitle("Main View");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void onGuestManagementClick() {
		System.out.println("Guest Management");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GuestView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onReservationManagementClick() {
		System.out.println("Reservation Management");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckInClick() {
		System.out.println("Check In");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckInView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckOutClick() {
		System.out.println("Check Out");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOutView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}