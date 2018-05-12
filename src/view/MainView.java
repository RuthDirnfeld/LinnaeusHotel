package view;

import controller.MainController;
import javafx.stage.Stage;

public class MainView extends View{

	

	public Stage display() throws Exception {
		return stage;
	}

	/**
	 * Opens guest list window
	 */
	public void onGuestManagementClick() {
		try {
			((MainController) controller).getApp().getGuestController().getGuestListView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens reservation window
	 */
	public void onReservationManagementClick() {
		try {
			((MainController) controller).getApp().getResController().getResView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens check in window
	 */
	public void onCheckInClick() {
		try {
			((MainController) controller).getCheckInView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens check out window
	 */
	public void onCheckOutClick() {
		try {
			((MainController) controller).getCheckOutView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens room list window
	 */
	public void onRoomsClick() {
		try {
			((MainController) controller).getApp().getRoomController().getRoomView().show();
			((MainController) controller).getApp().getRoomController().updateRoomList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens preferences
	 */
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