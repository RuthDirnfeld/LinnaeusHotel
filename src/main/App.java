package main;

import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
	MainController mc = new MainController();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		mc.initialize();
		//if (mc.isDatabaseInit()) {
			mc.getDisplay().show();
		//}
		//else {
		//	mc.getDatabaseViewStage().show();
		//}
	}

}
