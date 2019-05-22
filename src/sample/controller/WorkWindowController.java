package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WorkWindowController {

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

        exitHyperlink.setOnAction(event -> {
            try {
                LoginWindowController.start(new Stage());
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        username.setText(usernameHelper);
    }

    public static void start(Stage primaryStage, String login) throws IOException {
        usernameHelper = login;
        Parent root = FXMLLoader.load(WorkWindowController.class.getResource("/workWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("APM");
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
    }
}

