package pl.magdalena.brejna.colourtheworldapp.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

import java.io.IOException;

public class Instruction {

    private final String INSTRUCTION_FXML = "/fxml.files/InstructionLayout.fxml";

    //elements necessary to serve instructionLayout
    private FXMLLoader loader;
    private Stage newWindow;

    public Instruction(){
        setInstructionSettings();
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
        newWindow.setTitle("Instruction");
        newWindow.setScene(new Scene(root));
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
        newWindow.setWidth(800);
        newWindow.setHeight(800);
        newWindow.show();
    }
}
