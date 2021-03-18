package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class Main extends Application {

    private static final String MainFXML = "/fxml.files/MainLayout.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)  {
        Parent root = FxmlUtils.fxmlLoader(MainFXML);
        Scene scene = new Scene(root);
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(750);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.show();
    }
}
