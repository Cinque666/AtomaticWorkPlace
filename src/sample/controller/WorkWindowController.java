package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

public class WorkWindowController {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(WorkWindowController.class);

    @FXML
    private Button adminMenuButton;

    @FXML
    private Text username = new Text();

    private static String usernameHelper;

    @FXML
    private ResourceBundle resources;

    @FXML
    private static Stage stage;

    @FXML
    private URL location;

    @FXML
    private Hyperlink exitHyperlink;

    @FXML
    void initialize() {
//        stage.setOnCloseRequest(event -> System.out.println());
        exitHyperlink.setOnAction(event -> {
            try {
                LoginWindowController.start(new Stage());
                stage.close();
            } catch (IOException e) {
                LOGGER.error("Hyperlink initialize error");
            }
        });
        username.setText(usernameHelper);
        adminMenuButton.setOnAction(event -> {
            try {
                if(DBHandler.INSTANCE.checkAdminRights(username.getText())){
                    openAdminMenu();
                } else{
                    RightsErrorController.start(new Stage());
                }
            } catch (IOException e) {
                LOGGER.error("adminMenuButton initialize Error");
            }
        });
    }

    @SuppressWarnings("Duplicates")
    static void start(Stage prStage, String login) throws IOException {
        LOGGER.info("WorkWindowController start");
        usernameHelper = login;
        Parent root = FXMLLoader.load(WorkWindowController.class.getResource(ControllerConstants.WORK_WINDOW));
        Scene scene = new Scene(root);
        prStage.setTitle(ControllerConstants.ARM);
        prStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        prStage.setScene(scene);
        prStage.show();
        stage = prStage;
    }

    private void openAdminMenu() throws IOException {
        AdminMenuController.start(new Stage());
    }

}

