package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class DatabaseException extends ExecutionException {

    public DatabaseException(String s){
        super(s);
    }

    public void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "databaseError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
