package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController {

    private static Stage stage;
    private static final Logger LOGGER = LogManager.getLogger(AdminMenuController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button DeleteUserButton;

    public void initialize(){
        closeButton.setOnAction(event -> stage.close());
        addUserButton.setOnAction(event -> {
            try {
                AddUserController.start(new Stage());
            } catch (IOException e) {
                LOGGER.error("addUserButton IOException");
            }
        });
    }

    static void start(Stage primaryStage) throws IOException {
        LOGGER.info("AdminMenuWindow start");
        stage = primaryStage;
        Parent root = FXMLLoader.load(AdminMenuController.class.getResource(ControllerConstants.ADMIN_MENU_WINDOW));
        Scene scene = new Scene(root);
        primaryStage.setTitle(ControllerConstants.ADMIN_MENU_TITLE);
        primaryStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
