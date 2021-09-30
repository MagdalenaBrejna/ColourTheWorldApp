package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public final class NumberFormatException extends ExecutionException{

    public NumberFormatException(String s){
        super(s);
    }

    public final void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
