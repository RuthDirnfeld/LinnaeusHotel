package view;

import controller.MainController;
import controller.ReservationController;
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
	
	public void onRoomsClick() {
		try {
			((MainController) controller).getApp().getRoomController().getRoomView().show();
			((MainController) controller).getApp().getRoomController().updateRoomList(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPreferencesClick() {
		try {
			((MainController) controller).getApp().getOptionsController().setCity(((MainController) controller).getApp().getOptions().getCurrentCity().name());
			((MainController) controller).getApp().getOptionsController().setIp(((MainController) controller).getApp().getOptions().getDbAddress());
			((MainController) controller).getApp().getOptionsController().getOptionsView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}