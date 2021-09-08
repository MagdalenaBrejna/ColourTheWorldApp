package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class ImageProcessingException extends ExecutionException {

    public ImageProcessingException(String s){
        super(s);
    }

    public void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "imageProcessingError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
