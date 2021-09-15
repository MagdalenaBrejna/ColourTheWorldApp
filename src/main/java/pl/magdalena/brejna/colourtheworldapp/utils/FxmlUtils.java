package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlUtils {

    public static BorderPane fxmlLoader(String fxmlPath) throws ApplicationException {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (IOException exception) {
            throw new ApplicationException("FXMLLoader exception");
        }
    }

    public static FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        return loader;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.messages");
    }
}