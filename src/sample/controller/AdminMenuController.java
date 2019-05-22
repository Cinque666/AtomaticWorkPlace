package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController {

    private static Stage stage;

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
    }

    public static void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(AdminMenuController.class.getResource("/adminMenuWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin menu");
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
