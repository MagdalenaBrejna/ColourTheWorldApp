package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class ApplicationException extends ExecutionException {

    public ApplicationException(String s){
        super(s);
    }

    public void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "applicationError.text")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }
}
