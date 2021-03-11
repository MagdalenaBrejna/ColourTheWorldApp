package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class Main extends Application {

    private static final String MAIN_FXML = "/fxml.files/MainLayout.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)  {
        Parent root = FxmlUtils.fxmlLoader(MAIN_FXML);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.show();
    }
}
