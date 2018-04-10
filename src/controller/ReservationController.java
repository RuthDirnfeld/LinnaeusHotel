package controller;

import javafx.stage.Stage;
import view.View;

public class ReservationController extends Controller {
	
	private View guestListView;
	
	public void setListView(View view) {
		this.guestListView = view;
	}
	
	public Stage getListView() throws Exception {
		return guestListView.display();
	}

}
