package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import java.io.IOException;
import java.util.ResourceBundle;

public final class FxmlUtils {

    private final static String APPLICATION_EXCEPTION_MESSAGE = "FXMLLoader exception";
    private final static String BUNDLE = "bundles.messages";

    //load fxml and set bundles resources
    public final static BorderPane fxmlLoader(final String fxmlPath) throws ApplicationException {
        final FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (IOException exception) {
            throw new ApplicationException(APPLICATION_EXCEPTION_MESSAGE);
        }
    }

    //get FXML loader
    public final static FXMLLoader getLoader(final String fxmlPath) {
        final FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        return loader;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BUNDLE);
    }
}