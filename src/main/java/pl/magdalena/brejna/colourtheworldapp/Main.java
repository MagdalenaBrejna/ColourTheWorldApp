package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    //private static final String MAIN_FXML = "/fxml.files/MainLayout.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        ResourceBundle resources = ResourceBundle.getBundle("bundles.messages");
        BorderPane load = FXMLLoader.load(getClass().getResource("/fxml.files/MainLayout.fxml"), resources);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.show();

        //FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource("/fxml.files/MainLayout.fxml"));
        //Parent root = sampleLoader.load();

        //Parent root = FxmlUtils.fxmlLoader(MAIN_FXML);
        //Parent root =
        //Scene scene = new Scene(root);
        //primaryStage.setScene(scene);
        //primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("tittle.application"));
        //primaryStage.show();
    }
}
