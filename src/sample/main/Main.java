package sample.main;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.LoginWindowController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginWindowController.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
