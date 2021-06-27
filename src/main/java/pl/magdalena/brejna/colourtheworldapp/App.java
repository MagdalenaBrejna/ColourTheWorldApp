package pl.magdalena.brejna.colourtheworldapp;

import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    private static BorderPane appPane;

    public static void setCenterLayout(String fxml){
        appPane = Main.getMainPane();
        appPane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }
}
