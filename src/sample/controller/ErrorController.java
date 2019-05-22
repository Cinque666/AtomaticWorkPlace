package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        primaryStage.getIcons().add(new Image("/images/erroricon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
