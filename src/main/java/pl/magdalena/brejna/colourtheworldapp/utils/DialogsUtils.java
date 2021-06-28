package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtils {

    private static final ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static Optional<ButtonType> showConfirmationDialog(String title, String message) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString(title));
        confirmationDialog.setHeaderText(bundle.getString(message));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }
}
