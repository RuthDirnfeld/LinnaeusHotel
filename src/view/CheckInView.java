package view;

import java.util.ArrayList;

import controller.GuestController;
import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reservation;


public class CheckInView extends View{
	@FXML 
	private Button checkInButton;
	@FXML 
	private TextField searchField;
    @FXML
	private TableView<Reservation> resTable;
	@FXML
	private TableColumn<Reservation, String> nameColumn;
	@FXML
	private TableColumn<Reservation, String> roomColumn;
	@FXML
	private TableColumn<Reservation, String> checkInColumn;
	@FXML
	private TableColumn<Reservation, String> checkOutColumn;
	
	public ArrayList<Reservation> resv;
	
	@FXML
	public void initialize() {
	setTable();	
	}
	
	
	public void setTable() {
		if (resv != null) {
			ObservableList<Reservation> resvList = FXCollections.observableList(resv);
			resTable.setItems(resvList);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("guestName"));
			roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
			checkInColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
			checkOutColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("endDate"));
		}
	}
	
	
	@FXML
	public void searchBox(){
		if(!searchField.getText().isEmpty()) {
		((MainController) controller).refreshCheckInView(searchField.getText());	
		}else {
		((MainController) controller).refreshCheckInView();
		}
	}
	
	@FXML
	public void buttonClick() {
		Reservation res = resTable.getSelectionModel().getSelectedItem();
		((MainController) controller).checkIn(res); 
		((MainController) controller).refreshCheckInView();
		
		//Remove checked in guest from the list
		((MainController) controller).refreshCheckInView();
		searchField.clear();
	    }
	
	
	public void setTable(ArrayList<Reservation> list) {
		this.resv = list;
	}
    
	@Override
	public Stage display() throws Exception {
		return stage;
	}
}
