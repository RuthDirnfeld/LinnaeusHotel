package view;

import controller.NetworkController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Options.City;

public class DatabaseLoginView extends View {
    @FXML private TextField ipText;
    @FXML private TextField portText;
    @FXML private Button login;
	@FXML private ChoiceBox<String> cities;
	
	private ObservableList<String> cityList = FXCollections.observableArrayList("Vaxjo","Kalmar"); 
    
    public DatabaseLoginView () {
    }
    
    @FXML
	public void initialize() throws InstantiationException, IllegalAccessException {
    	// Prevents anything but numbers typed in port field of the window
		portText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			  @Override 
			  public void handle(KeyEvent keyEvent) {
				  if (!"0123456789".contains(keyEvent.getCharacter())) {
			      keyEvent.consume();
			    }
			  }
		});
		// Prevents anything but numbers and a dot typed in IP field
		ipText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			  @Override 
			  public void handle(KeyEvent keyEvent) {
				  if (!"0123456789.".contains(keyEvent.getCharacter())) {
			      keyEvent.consume();
			    }
			  }
		});
		cities.setItems(cityList);
	}

    @FXML
    public void buttonClick() throws Exception {
    	//User provided no input in IP or port fields
    	if (getAddress().isEmpty() || getPort().isEmpty()) {
    		showAlert("Address or port fields are empty!");
    	}
    	else {
    		// User provided input for IP, but haven't selected a city
    		if (((NetworkController) controller).setDatabaseAddress(getAddress(), getPort())) {
	    		if (cities.getSelectionModel().getSelectedItem() == null) {
	    			showAlert("Make sure you have selected a city!");
	    		}
	    		// User chose VAXJO as their city, set that in options
	    		else if (cities.getSelectionModel().getSelectedItem().toLowerCase().equals(City.VAXJO.name().toLowerCase())) {
	    			((NetworkController) controller).getApp().getOptions().setCurrentCity(City.VAXJO);
	        		stage.close();
	        		((NetworkController) controller).getApp().getMainController().getMainView().show();
	    		}
	    		// User chose KALMAR as their city, set that in options
	    		else {
	    			((NetworkController) controller).getApp().getOptions().setCurrentCity(City.KALMAR);
	        		stage.close();
	        		((NetworkController) controller).getApp().getMainController().getMainView().show();
	    		}
	    		// All went good, write everything to config file
	    		((NetworkController) controller).getApp().getConfig().writeToFile();
    		}
    		// An issue occured setting database address. Wrong address or connection issues
    		else {
    			showAlert("There has been an issue connecting to the database!");
    		}
    	}
    }
    
    private String getAddress() {
    	return ipText.getText();
    }
    
    private String getPort() {
    	return portText.getText();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

	public Stage display() throws Exception {
		return stage;
	}

}
