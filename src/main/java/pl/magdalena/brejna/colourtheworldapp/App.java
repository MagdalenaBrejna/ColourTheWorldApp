package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.database.dao.StageDao;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public final class App {

    private static BorderPane appPane;
    private static Project storedProject;

    //set the given fxml at the center of the main border pane
    public final static void setCenterLayout(final String fxml){
        appPane = Main.getMainPane();
        appPane.setCenter(FxmlUtils.fxmlLoader(fxml));
    }

    //minimize the given stage
    public final static void minimize(final Stage stage){
        stage.setIconified(true);
    }

    //close the given stage
    public final static void closeWindow(final Stage stage){
        stage.close();
    }

    //switch the main stage size to the opposite
    public final static void switchSize(){
        if(Main.getPrimaryStage().isMaximized()) {
            Main.getPrimaryStage().setMaximized(false);
            setMainStageSize(0);
        }else {
            Main.getPrimaryStage().setMaximized(true);
            setMainStageSize(1);
        }
    }

    //close the application
    public final static void closeApplication(){
        setMainStageSize(1);
        System.exit(0);
    }

    //reload the application
    public final static void refresh(){
        Main.getPrimaryStage().close();
        Platform.runLater(() -> new Main().start(new Stage()));
    }

    //set the application on top
    public final static void setAppOnTop(){
        Main.getPrimaryStage().setAlwaysOnTop(true);
    }

    //get project stored in the application
    public final static Project getStoredProject(){
        return storedProject;
    }

    //set project in the application
    public final static void setStoredProject(final Project project){
        storedProject = project;
    }

    //update the size of the main stage stored in the database
    public final static void setMainStageSize(final int value){
        StageDao dao = new StageDao();
        dao.updateMainStageSize(value);
    }
}
