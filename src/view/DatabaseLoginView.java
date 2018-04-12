package view;


import controller.NetworkController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class DatabaseLoginView extends View {
	
  private Stage stage;

  @FXML private TextField ipText;
  @FXML private TextField portText;
  @FXML private Button login;

  public DatabaseLoginView () {
  }
    
  @FXML
	public void initialize() throws InstantiationException, IllegalAccessException {
		portText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (!"0123456789".contains(keyEvent.getCharacter())) {
					keyEvent.consume();
				}
			}
		});
		ipText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (!"0123456789.".contains(keyEvent.getCharacter())) {
					keyEvent.consume();
				}
			}
		});

	}


  @FXML
  public void buttonClick() {
    if (getAddress().isEmpty() || getPort().isEmpty()) {
      showAlert("Address or port fields are empty!");
    }
    else {
      ((NetworkController) controller).setDatabase(getAddress(), getPort());
        stage.close();
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

	public Stage displayDbLogin() throws Exception {
		stage = new Stage();
		Scene scene = null;
		scene = new Scene (parent);
		stage.setTitle("Database login");
		stage.setScene(scene);
		return stage;
	}

}
