package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class Main extends Application {

    private static final String MAIN_LAYOUT_FXML = "/fxml.files/MainLayout.fxml";

    private static Stage primaryStage;
    private static BorderPane mainPane;

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static BorderPane getMainPane(){
        return mainPane;
    }

    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage)  {
        setPrimaryStage(stage);
        mainPane = FxmlUtils.fxmlLoader(MAIN_LAYOUT_FXML);
        Scene scene = new Scene(mainPane);

        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(850);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.show();
    }
}
