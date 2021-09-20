package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;
import pl.magdalena.brejna.colourtheworldapp.windows.Zoom;

public class ZoomController {

    @FXML
    private ImageView zoomImageView;

    public void setZoomImage(Image image){
        zoomImageView.setImage(image);
    }

    @FXML
    private void closeZoom(){
        App.closeWindow(Zoom.getNewWindow());
    }

    @FXML
    private void minimizeZoom(){
        App.minimize(Zoom.getNewWindow());
    }
}
