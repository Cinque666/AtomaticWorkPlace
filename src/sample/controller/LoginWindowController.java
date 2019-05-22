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
import sample.bean.User;
import sample.connection.DBHandler;

public class LoginWindowController {

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
                loginUser(loginText, loginPassword);
            } else {
                System.out.println("Invalid login or password.");
            }
        } );
    }

    private void loginUser(String loginText, String loginPassword) {
        DBHandler dbHandler = new DBHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = dbHandler.signInUser(user);

        int counter = 0;

        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        if(counter >= 1){
            WorkWindowController workWindowController = new WorkWindowController();
            try{
                workWindowController.start(new Stage(), user.getLogin());
                primaryStage.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(LoginWindowController.class.getResource("/loginWindow.fxml"));
        LoginWindowController controller;
        primaryStage.setTitle("АРМ");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.show();
        LoginWindowController.primaryStage = primaryStage;
    }
}
