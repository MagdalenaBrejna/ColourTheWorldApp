package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageSettingException;

import java.io.File;

public class ImageFxModel {

    private ObjectProperty<ImageFX> imageFxObjectProperty = new SimpleObjectProperty<>(new ImageFX());

    public void init(){ }

    public ImageFX getImageFxObjectProperty() {
        return imageFxObjectProperty.get();
    }

    public ObjectProperty<ImageFX> imageFxObjectPropertyProperty() {
        return imageFxObjectProperty;
    }

    public void setImageFxObjectProperty(ImageFX imageFxObjectProperty) {
        this.imageFxObjectProperty.set(imageFxObjectProperty);
    }

    public Image loadImage () throws ImageSettingException {

        ImageFX imageBefore = new ImageFX();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

            Image image = new Image(file.toURI().toString());
            if(!image.isError()) {
                imageBefore.setImage(image);
                return imageBefore.getImage();
            }else
                throw new ImageSettingException("file exception");

        } catch (NullPointerException e) {
            throw new ImageSettingException("file exception");
        }
    }

}
