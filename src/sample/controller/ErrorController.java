package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.controller.constants.ControllerConstants;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ErrorController {

    private static Stage stage;
    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button okButton;

    @FXML
    private URL location;

    @FXML
    private Text errorText;

    @FXML
    void initialize() {

        okButton.setOnAction(event -> stage.close());
    }

    static void start(Stage primaryStage) throws IOException {
        LOGGER.info("ErrorWindow start");
        stage = primaryStage;
        Parent root = FXMLLoader.load(ErrorController.class.getResource(ControllerConstants.ERROR_WINDOW));
        Scene scene = new Scene(root);
        primaryStage.setTitle(ControllerConstants.ERROR_TITLE);

        String file = ControllerConstants.ERROR_SOUND;
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        primaryStage.getIcons().add(new Image(ControllerConstants.ERROR_ICON));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void close(){
        stage.close();
    }

    static Stage getStage(){
        return stage;
    }
}
