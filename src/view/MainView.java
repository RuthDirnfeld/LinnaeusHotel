package view;

import controller.MainController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends View{

	

	public Stage display() throws Exception {
		Stage stage = new Stage();
		stage.setTitle("Main View");
		stage.setScene(new Scene(parent));
		stage.setResizable(false);
		
		return stage;
	}

	public void onGuestManagementClick() {
		try {
			((MainController) controller).getGuestController().getDisplay().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onReservationManagementClick() {
		try {
			((MainController) controller).getResController().getDisplay().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckInClick() {
		try {
			((MainController) controller).getGuestController().getCheckInView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckOutClick() {
		try {
			((MainController) controller).getGuestController().getCheckOutView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPreferencesClick() {
		try {
			((MainController) controller).getOptionsController().getDisplay().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}