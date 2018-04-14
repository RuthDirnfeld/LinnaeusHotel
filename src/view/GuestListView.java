package view;

import java.util.ArrayList;
import java.util.List;

import controller.GuestController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Guest;

public class GuestListView extends View {
	@FXML
	Button searchBtn;
	@FXML
	Button addGuestBtn;
	@FXML
	Button loadBtn;
	@FXML
	Button cancelBtn;
	
	List<Guest> guestList = new ArrayList<Guest>();
	
	public Stage display() throws Exception {
		return stage;
	}
	
	public void searchClick() {
		System.out.println("Search");
	}

	public void newClick() {
		try {
			((GuestController) controller).getGuestView().show();
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
