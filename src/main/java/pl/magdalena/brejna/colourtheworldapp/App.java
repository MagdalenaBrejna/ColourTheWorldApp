package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    private static BorderPane appPane;

    public static void setCenterLayout(String fxml){
        appPane = Main.getMainPane();
        appPane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }

    public static void refresh(){
        Main.getPrimaryStage().close();
        Platform.runLater( () -> new Main().start(new Stage()));
    }

    public static void setAppOnTop(){
        Main.getPrimaryStage().setAlwaysOnTop(true);
    }
}
