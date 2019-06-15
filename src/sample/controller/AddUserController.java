package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.bean.User;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;
import sample.validator.LoginValidator;
import sample.validator.NameValidator;
import sample.validator.PasswordValidator;
import sample.validator.SurnameValidator;

@SuppressWarnings("ALL")
public class AddUserController {

    private static Stage stage;
    private static final Logger LOGGER = LogManager.getLogger(AddUserController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboxRole;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    void initialize() {
        comboxRole.getItems().addAll(ControllerConstants.ADMINISTRATOR, ControllerConstants.WORKER);
        signUpButton.setOnAction(event -> {
            String login = this.login.getText().trim();
            String password = this.password.getText().trim();
            String name = this.name.getText().trim();
            String surname = this.surname.getText().trim();
            if(LoginValidator.INSTANCE.isValid(login) || PasswordValidator.INSTANCE.isValid(password)){
                if(NameValidator.INSTANCE.isValid(name) || SurnameValidator.INSTANCE.isValid(surname)){
                    signUpUser(login, password, name, surname, checkRole());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Неверное имя или фамилия");
                    alert.showAndWait();
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Неверный логин или пароль");
                alert.showAndWait();
            }
//            System.out.println(LoginValidator.INSTANCE.isValid(login.getText().trim()));
        });

    }

    public static void start(Stage primaryStage) throws IOException {
        LOGGER.info("addUserWindow start");
        Parent root = FXMLLoader.load(AddUserController.class.getResource(ControllerConstants.ADD_USER_WINDOW));
        Scene scene = new Scene(root);
        primaryStage.setTitle(ControllerConstants.ARM);
        primaryStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        primaryStage.setScene(scene);
        primaryStage.show();
        AddUserController.stage = primaryStage;
    }

    public static Stage getStage(){
        return AddUserController.stage;
    }

//    public static void close(){
//        AddUserController.stage.close();
//    }

    private void signUpUser(String login, String password, String name, String surname, int role){
//        System.out.println("signup");
        if(!DBHandler.INSTANCE.checkLoginExisting(login)) {
            User user = new User(name, surname, login, password, role);
            System.out.println("User created");
        }
    }

    private int checkRole(){
        if(comboxRole.getValue().equals(ControllerConstants.ADMINISTRATOR)){
            return 2;
        }
        else {
            return 1;
        }
    }

    private boolean isLoginExist(String login){

        return true;
    }
}
