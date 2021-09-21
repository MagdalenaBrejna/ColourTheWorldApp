package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class App {

    private static BorderPane appPane;
    private static Project storedProject;

    public static void setCenterLayout(String fxml){
        appPane = Main.getMainPane();
        appPane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }

    public static void minimize(Stage stage){
        stage.setIconified(true);
    }

    public static void closeWindow(Stage stage){
        stage.close();
    }

    public static void switchSize(){
        if(Main.getPrimaryStage().isMaximized()) {
            Main.getPrimaryStage().setMaximized(false);
            setMainStageSize(0);
        }else {
            Main.getPrimaryStage().setMaximized(true);
            setMainStageSize(1);
        }
    }

    public static void closeApplication(){
        setMainStageSize(1);
        System.exit(0);
    }

    public static void refresh(){
        Main.getPrimaryStage().close();
        Platform.runLater(() -> new Main().start(new Stage()));
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

    public static void setMainStageSize(int value){
        ProjectDao dao = new ProjectDao();
        dao.updateMainStageSize(value);
    }
}
