package view;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Guest;

public class GuestListView extends Application {
	@FXML
	private Button searchBtn;
	@FXML
	private Button addGuestBtn;
	@FXML
	private Button loadBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private TableView<Guest> guestTable;
	@FXML
	private TableColumn <Guest, String> name;
	@FXML
	private TableColumn <Guest, String> address;
	@FXML
	private TableColumn<Guest, String>  telephone;
	@FXML
	private TableColumn<Guest, String>  creditCard;
	@FXML
	private TableColumn<Guest, String> passport;
	@FXML
	private TableColumn <Guest, Boolean> smoker;
	@FXML
	private TableColumn <Guest, String> favRoom;
	
	public ArrayList<Guest> guests;
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GuestListView.fxml"));
		stage.setTitle("Guest List");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void initialize() {
		if (guests != null) {
			ObservableList<Guest> guestList = FXCollections.observableList(guests);
			guestTable.setItems(guestList);
			
			name.setCellValueFactory(new PropertyValueFactory<Guest, String> ("name"));
			address.setCellValueFactory(new PropertyValueFactory<Guest, String> ("address"));
			telephone.setCellValueFactory(new PropertyValueFactory<Guest, String> ("phoneNum"));
			creditCard.setCellValueFactory(new PropertyValueFactory<Guest, String> ("creditNumber"));
			passport.setCellValueFactory(new PropertyValueFactory<Guest, String> ("passportNumber"));
			favRoom.setCellValueFactory(new PropertyValueFactory<Guest, String> ("favRoom"));
			smoker.setCellValueFactory(new PropertyValueFactory<Guest, Boolean> ("smoker"));
	
			/*		smoker.setCellValueFactory(cellData -> cellData.getValue().isSmoker());

			smoker.setCellFactory(col -> new TableCell<Guest, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty) ;
			        setText(empty ? null : item ? "Smoker" : "Non-Smoker" );
			    }
			});*/
		}
	}
	
	public void searchClick() {
		System.out.println("Search");
	}

	public void newClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GuestView.fxml"));
			Parent parent = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadClick() {
		System.out.println("Load");
	}

	public void cancelClick() {
		System.out.println("Cancel");
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

}
