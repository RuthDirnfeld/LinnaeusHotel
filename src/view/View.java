package view;

import controller.Controller;
import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class View {
	protected Controller controller;
	protected Parent parent;
	
	public void setController (Controller c) {
		this.controller = c;
	}
	
	public void setParent(Parent p) {
		this.parent = p;
	}
	
	public Stage display() throws Exception {
		return null;
	}
}
