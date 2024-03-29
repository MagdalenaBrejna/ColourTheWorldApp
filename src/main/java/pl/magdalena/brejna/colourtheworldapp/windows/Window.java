package pl.magdalena.brejna.colourtheworldapp.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

import java.io.IOException;

public abstract class Window {

    private String FXML = "";
    private final String APPLICATION_EXCEPTION_MESSAGE = "root setting exception";

    //elements necessary to serve instructionLayout
    private static FXMLLoader loader;
    private static Stage newWindow;
    private double xOffset;
    private double yOffset;

    public Window(String FXML){
        this.FXML = FXML;
        setInstructionSettings();
    }

    //enable stage moving
    protected final void setStageMoving(final Scene scene){
        scene.setOnMousePressed(event -> {
            xOffset = newWindow.getX() - event.getScreenX();
            yOffset = newWindow.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            newWindow.setX(event.getScreenX() + xOffset);
            newWindow.setY(event.getScreenY() + yOffset);
        });
    }

    public final static Stage getNewWindow(){
        return newWindow;
    }

    public final static FXMLLoader getLoader(){
        return loader;
    }

    //set necessary entry settings
    private final void setInstructionSettings(){
        loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = null;
        try {
            root = setRoot();
        } catch (ApplicationException appException) {
            appException.callErrorMessage();
        }

        newWindow = new Stage();
        newWindow.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        setStageMoving(scene);
        newWindow.setScene(scene);
    }

    //set root (only once)
    private final Parent setRoot() throws ApplicationException {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException exception) {
            throw new ApplicationException(APPLICATION_EXCEPTION_MESSAGE);
        }
        return root;
    }

    //show window
    public final void showWindow(){
        newWindow.show();
    }
}