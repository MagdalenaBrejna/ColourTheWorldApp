package pl.magdalena.brejna.colourtheworldapp;

import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    static BorderPane pane = Main.mainPane;

    public static void setCenterLayout(String fxml){
        pane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }
}
