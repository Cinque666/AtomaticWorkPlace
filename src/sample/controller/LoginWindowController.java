package sample.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.bean.User;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

/**
 * Entry Window
 */
public class LoginWindowController {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(LoginWindowController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private static Stage primaryStage;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authorizationButton;

    @FXML
    void initialize() {
        authorizationButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals("")){
                try {
                    loginUser(loginText, loginPassword);
                } catch (IOException e) {
                    LOGGER.error("authorizationButton initialize Error");
                }
            } else {
                try {
                    if(ErrorController.getStage() == null) {
                        ErrorController.start(new Stage());
                    } else{
                        ErrorController.close();
                        ErrorController.start(new Stage());
                    }
                } catch (IOException e) {
                    LOGGER.error("authorization initialize Error");
                }
            }
        } );
    }

    private void loginUser(String loginText, String loginPassword) throws IOException {
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = DBHandler.INSTANCE.signInUser(user);

        int counter = 0;

        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch(SQLException e){
            LOGGER.error("loginUser method Error SQL Exception");
        }
        if(counter >= 1){
            try{
                WorkWindowController.start(new Stage(), user.getLogin());
                primaryStage.close();
            } catch(IOException e){
                e.printStackTrace();
                LOGGER.error("loginUser IOException");
            }
        } else{
            if(ErrorController.getStage() == null) {
                ErrorController.start(new Stage());
            } else{
                ErrorController.close();
                ErrorController.start(new Stage());
            }
        }
    }

    public static void start(Stage primaryStage) throws IOException {
        LOGGER.info("LoginWindow start");
        Parent root = FXMLLoader.load(LoginWindowController.class.getResource(ControllerConstants.LOGIN_WINDOW));
        primaryStage.setTitle(ControllerConstants.ARM);
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        primaryStage.show();
        LoginWindowController.primaryStage = primaryStage;
    }
}
