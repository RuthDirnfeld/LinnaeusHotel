package view;

import controller.MainController;
import javafx.stage.Stage;

public class MainView extends View{

	

	public Stage display() throws Exception {
		return stage;
	}

	public void onGuestManagementClick() {
		try {
			((MainController) controller).getApp().getGuestController().getGuestListView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onReservationManagementClick() {
		try {
			((MainController) controller).getApp().getResController().getResView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckInClick() {
		try {
			((MainController) controller).getCheckInView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onCheckOutClick() {
		try {
			((MainController) controller).getCheckOutView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPreferencesClick() {
		try {
			((MainController) controller).getApp().getOptionsController().getOptionsView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}