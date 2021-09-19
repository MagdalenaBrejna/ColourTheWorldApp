package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    private static BorderPane appPane;
    private static Project storedProject;

    public static void setCenterLayout(String fxml){
        appPane = Main.getMainPane();
        appPane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }

    public static void minimize(){
        Main.getPrimaryStage().setIconified(true);
    }

    public static void switchSize(){
        if(Main.getPrimaryStage().isMaximized())
            Main.getPrimaryStage().setMaximized(false);
        else
            Main.getPrimaryStage().setMaximized(true);
    }

    public static void refresh(){
        Main.getPrimaryStage().close();
        Platform.runLater( () -> new Main().start(new Stage()));
    }

    public static void setAppOnTop(){
        Main.getPrimaryStage().setAlwaysOnTop(true);
    }

    public static void resize(){
        Main.getPrimaryStage().setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        Main.getPrimaryStage().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
    }

    public static Project getStoredProject(){
        return storedProject;
    }

    public static void setStoredProject(Project project){
        storedProject = project;
    }
}
