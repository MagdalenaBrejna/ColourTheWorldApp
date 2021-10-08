package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.FullView;

public final class FullViewController {

    @FXML
    private ImageView fullImageView;

    //set full view's image content
    public final void setFullViewImage(final Image image){
        fullImageView.setImage(image);
    }

    //get full view's image
    public final Image getFullViewImage(){
        return fullImageView.getImage();
    }

    //close full view's stage
    @FXML
    private final void closeFullView(){
        App.closeWindow(FullView.getNewWindow());
    }

    //minimize full view's stage
    @FXML
    private final void minimizeFullView(){
        App.minimize(FullView.getNewWindow());
    }
}
