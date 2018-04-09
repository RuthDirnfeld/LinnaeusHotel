package view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Guest;

public class GuestListView extends Application{
	@FXML
	Button searchBtn;
	@FXML
	Button addGuestBtn;
	@FXML
	Button loadBtn;
	@FXML
	Button cancelBtn;
	
	List<Guest> guestList = new ArrayList<Guest>();
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GuestListView.fxml"));
		stage.setTitle("Guest List");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void searchClick() {
		System.out.println("Search");
	}

	public void newClick() {
		System.out.println("New");
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

	public void loadClick() {
		System.out.println("Load");
	}

	public void cancelClick() {
		System.out.println("Cancel");
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

}
