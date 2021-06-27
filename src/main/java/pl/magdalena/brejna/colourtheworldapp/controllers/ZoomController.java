package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ZoomController {

    @FXML
    private ImageView zoomImageView;

    public void setZoomImage(Image image){
        zoomImageView.setImage(image);
    }
}
