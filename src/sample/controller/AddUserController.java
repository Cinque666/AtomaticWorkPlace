package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.controller.constants.ControllerConstants;

public class AddUserController {

    private static Stage stage;
    private static final Logger LOGGER = LogManager.getLogger(AddUserController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboxRole;

    @FXML
    private Button authorizationButton;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    void initialize() {
        comboxRole.getItems().addAll(ControllerConstants.ADMINISTRATOR, ControllerConstants.WORKER);

    }

    public static void start(Stage primaryStage) throws IOException {
        LOGGER.info("addUserWindow start");
        Parent root = FXMLLoader.load(AddUserController.class.getResource(ControllerConstants.ADD_USER_WINDOW));
        Scene scene = new Scene(root);
        primaryStage.setTitle(ControllerConstants.ARM);
        primaryStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        primaryStage.setScene(scene);
        primaryStage.show();
        AddUserController.stage = primaryStage;
    }
}
