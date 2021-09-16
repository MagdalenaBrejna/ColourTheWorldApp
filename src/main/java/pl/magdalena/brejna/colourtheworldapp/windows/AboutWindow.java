package pl.magdalena.brejna.colourtheworldapp.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import java.io.IOException;

public class AboutWindow {

    private final String ABOUT_WINDOW_FXML = "/fxml.files/AboutWindowLayout.fxml";

    //elements necessary to serve instructionLayout
    private FXMLLoader loader;
    private Stage newWindow;

    public AboutWindow(){
        setInstructionSettings();
    }

    //set necessary entry settings
    private void setInstructionSettings(){
        loader = new FXMLLoader(getClass().getResource(ABOUT_WINDOW_FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = null;
        try {
            root = setRoot();
        } catch (ApplicationException appException) {
            appException.callErrorMessage();
        }

        newWindow = new Stage();
        newWindow.setTitle("About");
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
        newWindow.setWidth(790);
        newWindow.setHeight(440);
        newWindow.show();
    }
}
