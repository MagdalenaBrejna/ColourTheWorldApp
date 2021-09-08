package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class ImageLoadingException extends ExecutionException {

    public ImageLoadingException(String s){
        super(s);
    }

    public void callErrorMessage(){
        DialogsUtils.showConfirmationDialog("error.title", "imageLoadingError.text")
                .filter(response -> response == ButtonType.OK);
    }
}
