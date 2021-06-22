package pl.magdalena.brejna.colourtheworldapp;

import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    private static BorderPane pane;

    public static void setCenterLayout(String fxml){
        pane = Main.getMainPane();
        pane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }
}
