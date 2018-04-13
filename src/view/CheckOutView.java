package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class CheckOutView extends View {
	 
	
	ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 

	public Stage display() throws Exception {
		return stage;
	}
	
	
}
