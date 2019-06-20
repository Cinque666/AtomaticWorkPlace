package sample.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkWindowController {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(WorkWindowController.class);
    private TimerCounter timerCounter;

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
    private Text adminLvlMessage;

    @FXML
    void initialize() {
        eventList.setOnAction(event -> {
            try {
                EventListController.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        stage.setOnCloseRequest(confirmCloseEventHandler);
//        stage.setOnCloseRequest(event -> System.out.println());
//        stage.setOnCloseRequest(event -> {
//
//            final Stage dialog = new Stage();
//            dialog.initModality(Modality.APPLICATION_MODAL);
//
//            // Frage - Label
//            Label label = new Label("Do you really want to quit?");
//
//            // Antwort-Button JA
//            Button okBtn = new Button("Yes");
//            okBtn.setOnAction(event12 ->{
//                Platform.exit();
//                dialog.close();
//                event.consume();
//            });
//
//
//            // Antwort-Button NEIN
//            Button cancelBtn = new Button("No");
//            cancelBtn.setOnAction(event1 -> {
////                stage.show();
//                dialog.close();
//            });
//        });
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
        timerCounter = new TimerCounter();
        timerCounter.start();
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
//    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
//        Alert closeConfirmation = new Alert(
//                Alert.AlertType.CONFIRMATION,
//                "Are you sure you want to exit?"
//        );
//        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
//                ButtonType.OK
//        );
//        exitButton.setText("Exit");
//        closeConfirmation.setHeaderText("Confirm Exit");
//        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
//        closeConfirmation.initOwner(stage);
//
//        // normally, you would just use the default alert positioning,
//        // but for this simple sample the main stage is small,
//        // so explicitly position the alert so that the main window can still be seen.
//        closeConfirmation.setX(stage.getX());
//        closeConfirmation.setY(stage.getY() + stage.getHeight());
//
//        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
//        if (!ButtonType.OK.equals(closeResponse.get())) {
//            event.consume();
//        }
//    };

}


