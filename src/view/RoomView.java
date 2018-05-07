package view;

import java.util.ArrayList;

import controller.RoomController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Guest;
import model.Reservation;
import model.Room;
import model.RoomState;

public class RoomView extends View {
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> idCol;
	@FXML
	private TableColumn<Room, Boolean> singleBedCol;
	@FXML
	private TableColumn<Room, Boolean> doubleCol;
	@FXML
	private TableColumn<Room, Boolean> doubleKingCol;
	@FXML
	private TableColumn<Room, Boolean> apartmentCol;
	@FXML
	private TableColumn<Room, Boolean> largeRoomCol;
	@FXML
	private TableColumn<Room, Boolean> viewCol;
	@FXML
	private TableColumn<Room, Boolean> smokerCol;
	@FXML
	private TableColumn<Room, Integer> priceCol;
	@FXML
	private TableColumn<Room, RoomState> roomStateCol;
	@FXML
	private Button loadBtn;

	// Imported from database
	private ArrayList<Room> roomArray;

	@Override
	public Stage display() throws Exception {
		return stage;
	}

	public void initialize() {
		setTable();
	}

	public void setTable() {
		if (roomArray != null) {
			ObservableList<Room> roomList = FXCollections.observableList(roomArray);
			roomTable.setItems(roomList);
			idCol.setCellValueFactory(new PropertyValueFactory<Room, String>("roomNum"));
			singleBedCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("singleRoom"));
			singleBedCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			roomStateCol.setCellValueFactory(new PropertyValueFactory<Room, RoomState>("roomState"));
			roomStateCol.setCellFactory(tc -> new TableCell<Room, RoomState>() {
			    @Override
			    protected void updateItem(RoomState item, boolean empty) {
			        super.updateItem(item, empty);
			        TableRow<Room> currentRow = getTableRow();
			        if(item == item.allocated){
			        	currentRow.setStyle("-fx-background-color:lightcoral");	
			        }
			        if(item == item.reserved) {
			        	currentRow.setStyle("-fx-background-color:#f7b100");
			        }
			        if(item == item.free) {
			        	currentRow.setStyle("-fx-background-color:lightgreen");
			        }
			    }
			});
			
			doubleCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("doubleRoom"));
			doubleCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			doubleKingCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("doubleKingRoom"));
			doubleKingCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			apartmentCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("apartment"));
			apartmentCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			largeRoomCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("largeRooms"));
			largeRoomCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			viewCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("view"));
			viewCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			smokerCol.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("smokerRoom"));
			smokerCol.setCellFactory(tc -> new TableCell<Room, Boolean>() {
			    @Override
			    protected void updateItem(Boolean item, boolean empty) {
			        super.updateItem(item, empty);
			        setText(empty ? null :
			            item.booleanValue() ? "XXX" : " ");
			    }
			});
			priceCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("price"));
			
		}
	}

	public void preferencesClick() {
		try {
			((RoomController) controller).getRoomPrefView().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onLoadBtnClick() {
		if(roomTable.getSelectionModel().getSelectedItem() != null) {
			Room room = roomTable.getSelectionModel().getSelectedItem();
			((RoomController) controller).getApp().getResController().setSelectedRoom(room.getRoomNum());
			((RoomController) controller).getApp().getResController().setSelectedPrice(room.getPrice());
			Stage stage = (Stage) loadBtn.getScene().getWindow();
			stage.close();
			
			}
	}

	public void setTable(ArrayList<Room> list) {
		this.roomArray = list;
	}
	
	public static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public void getRoomList(boolean b) {
		((RoomController) controller).updateRoomList(b);
	}
}
