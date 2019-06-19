package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.bean.Event;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventListController {

    private static Stage primaryStage;
    private static final Logger LOGGER = LogManager.getLogger(EventListController.class);
    private ObservableList<Event> eventsData = FXCollections.observableArrayList();

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private Button addTableDataButton;

    public void initialize() throws SQLException {
        initData();
        closeButton.setOnAction(event -> primaryStage.close());
        TableColumn<Event, String> idEvent
                = new TableColumn<>("Номер");
        TableColumn<Event, String> idWorker
                = new TableColumn<>("Номер рабочего");
        TableColumn<Event, String> idCustomer
                = new TableColumn<>("Номер клиента");
        TableColumn<Event, String> cost
                = new TableColumn<>("Стоимость");
        eventTable.getColumns().addAll(idEvent, idWorker, idCustomer, cost);
        idEvent.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        idWorker.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idCustomer.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        eventTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    System.out.println("Double clicked");
                }
            }
        });
        eventTable.setTooltip(new Tooltip("Нажмите 2 раза для редактирования строки"));
//        addTableDataButton.setOnAction(event -> );
    }

    @SuppressWarnings("Duplicates")
    static void start(Stage stage) throws IOException {
        LOGGER.info("EventList start");
        Parent root = FXMLLoader.load(EventListController.class.getResource(ControllerConstants.EVENT_LIST));
        stage.setTitle(ControllerConstants.EVENT_LIST_TITLE);
        Scene scene = new Scene(root,796, 500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        stage.show();
        primaryStage = stage;
    }

    private void initData() throws SQLException {
        ResultSet resultSet = DBHandler.INSTANCE.getEventData();
        int idEvent, idWorker, idCustomer, cost;
        while(resultSet.next()){
            idEvent = resultSet.getInt("idevent");
            idWorker = resultSet.getInt("idusers");
            idCustomer = resultSet.getInt("idcustomer");
            cost = resultSet.getInt("cost");
            eventsData.add(new Event(idEvent, idCustomer, idWorker, cost));
        }
        eventTable.setItems(eventsData);
    }
}
