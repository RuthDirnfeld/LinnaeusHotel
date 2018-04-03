package view;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuestView extends Application {
	 
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GuestView.fxml"));
		primaryStage.setTitle("Guest View");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
