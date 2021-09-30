package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.FullView;

public final class FullViewController {

    @FXML
    private ImageView zoomImageView;

    //set full view's image content
    public final void setZoomImage(final Image image){
        zoomImageView.setImage(image);
    }

    //close full view's stage
    @FXML
    private final void closeZoom(){
        App.closeWindow(FullView.getNewWindow());
    }

    //minimize full view's stage
    @FXML
    private final void minimizeZoom(){
        App.minimize(FullView.getNewWindow());
    }
}
