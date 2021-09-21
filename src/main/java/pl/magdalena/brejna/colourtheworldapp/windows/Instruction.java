package pl.magdalena.brejna.colourtheworldapp.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

import java.io.IOException;

public class Instruction {

    private final String INSTRUCTION_FXML = "/fxml.files/InstructionLayout.fxml";

    //elements necessary to serve instructionLayout
    private FXMLLoader loader;
    private static Stage newWindow;

    private double xOffset;
    private double yOffset;

    public Instruction(){
        setInstructionSettings();
    }

    private void setStageMoving(Scene scene){
        scene.setOnMousePressed(event -> {
            xOffset = newWindow.getX() - event.getScreenX();
            yOffset = newWindow.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            newWindow.setX(event.getScreenX() + xOffset);
            newWindow.setY(event.getScreenY() + yOffset);
        });
    }

    public static Stage getNewWindow(){
        return newWindow;
    }

    //set necessary entry settings
    private void setInstructionSettings(){
        loader = new FXMLLoader(getClass().getResource(INSTRUCTION_FXML));
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
    private Parent setRoot() throws ApplicationException {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException exception) {
            throw new ApplicationException("root setting exception");
        }
        return root;
    }

    //show window
    public void showWindow(){
        newWindow.show();
    }
}
