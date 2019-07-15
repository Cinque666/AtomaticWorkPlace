package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.bean.Customer;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;
import sample.validator.NameSurnameValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController {

    private static Stage primaryStage;
    private static final Logger LOGGER = LogManager.getLogger(AddCustomerController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane addUserAnchor;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField address;

    @FXML
    private TextField passport;

    @FXML
    private Text errorMessage;

    @FXML
    private Button back;

    @SuppressWarnings("Duplicates")
    @FXML
    void initialize() {
        back.setOnAction(event -> primaryStage.close());
        signUpButton.setOnAction(event -> {
            String name = this.name.getText().trim();
            String surname = this.surname.getText().trim();
            String address = this.address.getText().trim();
            String passport = this.passport.getText().trim();
            if(NameSurnameValidator.INSTANCE.isValid(name) && NameSurnameValidator.INSTANCE.isValid(surname)) {
                try {
                    signUpCustomer(name, surname, address, passport);
                } catch (SQLException | ClassNotFoundException e) {
                    LOGGER.error("signUpCustomer exception");
                }
            }
        });
    }


    @SuppressWarnings("Duplicates")
    public static void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(AddCustomerController.class.getResource(ControllerConstants.ADD_CUSTOMER_FXML));
        Scene scene = new Scene(root);
        stage.setTitle(ControllerConstants.ARM);
        stage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        stage.setScene(scene);
        stage.show();
        AddCustomerController.primaryStage = stage;
    }

    public static Stage getStage(){
        return primaryStage;
    }

    private void signUpCustomer(String name, String surname, String address, String passport)
            throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(name, surname, address, passport);
        DBHandler.INSTANCE.signUpCustomer(customer);
    }
}
