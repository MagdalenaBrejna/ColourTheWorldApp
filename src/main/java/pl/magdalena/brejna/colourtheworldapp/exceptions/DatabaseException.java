package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public final class DatabaseException extends ExecutionException {

    public DatabaseException(String s){
        super(s);
    }

    public final void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "databaseError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
