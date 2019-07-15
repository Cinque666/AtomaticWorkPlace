package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.bean.User;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class WorkWindowController {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(WorkWindowController.class);
    private TimerCounter timerCounter;

    @FXML
    private Button adminMenuButton;

    int totalTime;

    @FXML
    private Text username = new Text();

    private static String usernameHelper;

    @FXML
    private ResourceBundle resources;

    @FXML
    private static Stage stage;

    @FXML
    private Text timer;

    @FXML
    private URL location;

    @FXML
    private Hyperlink exitHyperlink;

    @FXML
    private Button eventList;

    @FXML
    private Button customers;

    @FXML
    private Text adminLvlMessage;

    @FXML
    private Button exit;


    @FXML
    void initialize() {

        FutureTask<Integer> futureTask = new FutureTask<>(new TimerCounter());
        new Thread(futureTask).start();
        exit.setOnAction(event -> {
            try {
                updateUserTime(username.getText(), futureTask.get());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            stage.close();

        });
//        eventsPDF.setOnAction(event -> );
        eventList.setOnAction(event -> {
            try {
                EventListController.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitHyperlink.setOnAction(event -> {
            try {
                LoginWindowController.start(new Stage());
                stage.close();
            } catch (IOException e) {
                LOGGER.error("Hyperlink initialize error");
            }
        });
        username.setText(usernameHelper);
        adminMenuButton.setOnAction(event -> {

            try {
                if(DBHandler.INSTANCE.checkAdminRights(username.getText())){
                    adminMenuButton.getScene().getWindow().hide();
                    openAdminMenu();
                } else{
                    adminLvlMessage.setText("Недостаточно прав.");
//                    RightsErrorController.start(new Stage());
                }
            } catch (IOException e) {
                LOGGER.error("adminMenuButton initialize Error");
            }
        });

        customers.setOnAction(event -> {
            try {
                CustomersController.start(new Stage());
            } catch (IOException e) {
                LOGGER.error("Customer controller start error");
            }
        });
        //Timer TimerCounter = new Timer();
    }

    @SuppressWarnings("Duplicates")
    static void start(Stage prStage, String login) throws IOException {
        LOGGER.info("WorkWindowController start");
        usernameHelper = login;
        Parent root = FXMLLoader.load(WorkWindowController.class.getResource(ControllerConstants.WORK_WINDOW));
        Scene scene = new Scene(root);
        prStage.setTitle(ControllerConstants.ARM);
        prStage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        prStage.setScene(scene);
        prStage.show();
        stage = prStage;
    }

    private void openAdminMenu() throws IOException {
        AdminMenuController.start(new Stage());
    }

    public static Stage getStage(){
        return stage;
    }

    class TimerCounter implements Callable<Integer> {
        @Override
        public Integer call() throws InterruptedException {
            int hoursCounter = 0;
            int minutesCounter = 0;
            int secondsCounter = 0;
            int totalTime = 0;
            try{
                while (!exit.isFocused()) {
                    secondsCounter++;
                    totalTime++;
                    if(secondsCounter == 60){
                        secondsCounter = 0;
                        minutesCounter++;
                        if(secondsCounter == 59 && minutesCounter == 59){
                            hoursCounter++;
                            minutesCounter = 0;
                            secondsCounter = 0;
                        }
                    }
                    timer.setText(hoursCounter + ":" + minutesCounter + ":" + secondsCounter);
                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException e){
                LOGGER.error("Thread exception");
            }
            return totalTime;
        }
    }

    private void updateUserTime(String login, int time) throws SQLException, ClassNotFoundException {
        User user = new User(login);
        DBHandler.INSTANCE.updateUserTime(user, time);
    }
}


