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
	
}