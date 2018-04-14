package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.OptionsView;

public class OptionsController extends Controller {
	
	private FXMLLoader fxmlOptions = new FXMLLoader((getClass().getResource("../view/OptionsView.fxml")));
	
	private OptionsView optionsView;
	private Parent optionsParent;
	
	public OptionsController() {
		try {
			optionsParent = fxmlOptions.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		optionsView = fxmlOptions.getController();
		optionsView.setParent(optionsParent);
		optionsView.setController(this);
		optionsView.setStage("Options");
	}
	
	public Stage getOptionsView() throws Exception {
		return optionsView.display();
	}

}
