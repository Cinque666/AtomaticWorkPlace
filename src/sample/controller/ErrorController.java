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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ErrorController {

    private static Stage stage;

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
        stage = primaryStage;
        Parent root = FXMLLoader.load(ErrorController.class.getResource("/errorWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("ERROR");

        String file = "resources/sounds/Error.mp3";
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        primaryStage.getIcons().add(new Image("/images/erroricon.png"));
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
