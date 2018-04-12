package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckOutView extends View {
	 
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		Stage stage = new Stage();
		stage.setTitle("Check-Out View");
		stage.setScene(new Scene(parent));
		stage.setResizable(false);
		return stage;
	}

}
