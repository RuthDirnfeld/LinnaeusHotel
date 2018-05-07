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
import model.Options.City;


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
	
	public ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
	setTable();	
	}
	
	
	public void setTable() {
		if (resv != null) {
			ObservableList<Reservation> resvList = FXCollections.observableList(resv);
			for (Reservation r : resvList) {
				if (((MainController)controller).getApp().getOptions().getCurrentCity() == City.VAXJO) {
					if (r.getRoom().contains("V")) {
						reservationList.add(r);
					}
				}
				else{
					if (r.getRoom().contains("K")) {
					reservationList.add(r);
					}
				}
			}
		
			resTable.setItems(reservationList);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("guestName"));
			roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
			checkInColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("startDate"));
			checkOutColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("endDate"));
		}}
	
	
	
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