package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkWindowController {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(WorkWindowController.class);

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
    void initialize() {
        eventList.setOnAction(event -> {
            try {
                EventListController.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        stage.setOnCloseRequest(event -> System.out.println());
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
            adminMenuButton.getScene().getWindow().hide();
            try {
                if(DBHandler.INSTANCE.checkAdminRights(username.getText())){
                    openAdminMenu();
                } else{
                    RightsErrorController.start(new Stage());
                }
            } catch (IOException e) {
                LOGGER.error("adminMenuButton initialize Error");
            }
        });
        new TimerCounter().start();
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

    class TimerCounter extends Thread{
        public void run(){
            int hoursCounter = 0;
            int minutesCounter = 0;
            int secondsCounter = 0;
            int totalTime = 0;
            try{
                while (true) {
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
            //System.out.printf("%s fiished... \n", Thread.currentThread().getName());
        }
        }
}


