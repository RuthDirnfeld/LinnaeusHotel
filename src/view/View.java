package view;

import controller.Controller;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View {
	protected Controller controller;
	protected Parent parent;
	protected Stage stage = new Stage();
	
	public void setController (Controller c) {
		this.controller = c;
	}
	
	public void setParent(Parent p) {
		this.parent = p;
	}
	
	public void setStage(String name) {
		Scene scene = new Scene (parent);
		stage.setTitle(name);
		stage.setScene(scene);
	}
	
	public Stage display() throws Exception {
		return null;
	}
}
