package controller;

import javafx.stage.Stage;
import view.View;

public abstract class Controller {
	
	protected View view;
	
	public void setView(View view) {
		this.view = view;
	}
	
	public Stage getDisplay() throws Exception {
		return view.display();
	}
}
