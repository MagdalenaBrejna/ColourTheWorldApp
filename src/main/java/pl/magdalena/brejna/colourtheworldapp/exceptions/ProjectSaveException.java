package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public final class ProjectSaveException extends ExecutionException {

    public ProjectSaveException(String s){
        super(s);
    }

    public final void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "projectSaveError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
