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

public class OptionsView extends Application {
	@FXML
	private TextField address; 
	@FXML 
	private ChoiceBox<String> cityChoice; 
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("OptionsView.fxml"));
		primaryStage.setTitle("Options");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML 
	private void initialize() {
		cityChoice.setItems(cityList);
	}
	
	public void onAcceptClick() {
		System.out.println("Accept");
		System.out.println(address.getText());
	}
	public void onClickCancel() {
		System.out.println("Cancel");
	}
	public void onClickManageRooms() {
		System.out.println("Manage Rooms");
	}
	public void onClickManageFloors() {
		System.out.println("Manage Floors");
	}
	
}
