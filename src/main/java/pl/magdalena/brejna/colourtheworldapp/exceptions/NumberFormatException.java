package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class NumberFormatException extends ExecutionException{

    public NumberFormatException(String s){
        super(s);
    }

    public void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
