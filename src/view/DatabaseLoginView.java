package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DatabaseLoginView extends Application {

    @FXML private TextField ipText;
    @FXML private TextField portText;
    @FXML private Button login;
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseLoginView.fxml"));

    @FXML
    public void buttonClick() {
    	//TODO get info from textfields on button click
    	//TODO error checking for input
    }
    
    private String getAddress() {
    	return ipText.getText();
    }
    
    private int getPort() {
    	return Integer.parseInt(portText.getText());
    }

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = null;
		try {
			scene = new Scene (loader.load());
	        stage.setTitle("Database login");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}
