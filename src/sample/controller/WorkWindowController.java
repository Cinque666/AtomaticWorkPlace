package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import sample.connection.DBHandler;

public class WorkWindowController {

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

        exitHyperlink.setOnAction(event -> {
            try {
                LoginWindowController.start(new Stage());
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        username.setText(usernameHelper);
        adminMenuButton.setOnAction(event -> {
            try {
                if(DBHandler.INSTANCE.checkAdminRights(username.getText())){
                    openAdminMenu();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static void start(Stage primaryStage, String login) throws IOException {
        usernameHelper = login;
        Parent root = FXMLLoader.load(WorkWindowController.class.getResource("/workWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("APM");
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
    }

    private void openAdminMenu() throws IOException {
        AdminMenuController.start(new Stage());
    }

    private void checkAdminRights(){

    }
}

