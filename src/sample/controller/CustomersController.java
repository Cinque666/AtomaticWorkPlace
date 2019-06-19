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
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.bean.Customer;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersController {

    private static Stage primaryStage;
    private static final Logger LOGGER = LogManager.getLogger(CustomersController.class);
    private ObservableList<Customer> customersData = FXCollections.observableArrayList();

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Customer> customersTable;

    public void initialize() throws SQLException, ClassNotFoundException {
        closeButton.setOnAction(event -> primaryStage.close());
        initData();
        TableColumn<Customer, String> idCustomer
                = new TableColumn<>("Номер");
        TableColumn<Customer, String> name
                = new TableColumn<>("Имя");
        TableColumn<Customer, String> surname
                = new TableColumn<>("Фамилия");
        TableColumn<Customer, String> address
                = new TableColumn<>("Адресс");
        TableColumn<Customer, String> passport
                = new TableColumn<>("Пасспорт");
        customersTable.getColumns().addAll(idCustomer, name, surname, address, passport);
        idCustomer.setCellValueFactory(new PropertyValueFactory<>("idCustomers"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        customersTable.setTooltip(new Tooltip("Нажмите 2 раза для редактирования строки"));
    }

    @SuppressWarnings("Duplicates")
    static void start(Stage stage) throws IOException {
        LOGGER.info("EventList start");
        Parent root = FXMLLoader.load(CustomersController.class.getResource(ControllerConstants.CUSTOMERS));
        stage.setTitle(ControllerConstants.CUSTOMERS_TITLE);
        Scene scene = new Scene(root,796, 500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        stage.show();
        primaryStage = stage;
    }

    private void initData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBHandler.INSTANCE.getCustomerData();
        int idCustomer;
        String name, surname, address, passport;
        while(resultSet.next()){
            idCustomer = resultSet.getInt("idcustomers");
            name = resultSet.getString("name");
            surname = resultSet.getString("surname");
            address = resultSet.getString("address");
            passport = resultSet.getString("passport");
            customersData.add(new Customer(idCustomer,name,surname,address,passport));
        }
        customersTable.setItems(customersData);
    }
}
