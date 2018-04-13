package main;

import controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
	AppController app = new AppController();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		app.initialize().show();
	}

}
