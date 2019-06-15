package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;

public class RightsErrorController {

    private static Stage stage;
    private static final Logger LOGGER = LogManager.getLogger(RightsErrorController.class);

    @FXML
    private Text errorText;

    @FXML
    private Button okButton;

    public void initialize(){
        okButton.setOnAction(event -> stage.close());
    }

    public static void start(Stage primaryStage) throws IOException {
        LOGGER.info("RightsError start");
        stage = primaryStage;
        Parent root = FXMLLoader.load(RightsErrorController.class.getResource(ControllerConstants.RIGHTS_ERROR_WINDOW));
        Scene scene = new Scene(root);
        primaryStage.setTitle(ControllerConstants.RIGHTS_ERROR);
        primaryStage.getIcons().add(new Image(ControllerConstants.RIGHTS_ERROR_ICON));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
