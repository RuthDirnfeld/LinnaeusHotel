package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	
}
