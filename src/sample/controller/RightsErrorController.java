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

public class RightsErrorController {

    private static Stage stage;

    @FXML
    private Text errorText;

    @FXML
    private Button okButton;

    public void initialize(){
        okButton.setOnAction(event -> stage.close());
    }

    public static void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(RightsErrorController.class.getResource("/rightsError.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Rights error");
        primaryStage.getIcons().add(new Image("/images/erroricon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
