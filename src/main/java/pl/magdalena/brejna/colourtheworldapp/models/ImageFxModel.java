package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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

    public void saveImage(Image imageAfter) throws ImageException, IOException{

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png"));

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null)
            ImageIO.write(SwingFXUtils.fromFXImage(imageAfter, null), "png", file);
        else
            throw new ImageException("Save file exception.");
    }

    public Image loadImage () throws ImageException {

        ImageFX imageBefore = new ImageFX();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

            Image image = new Image(file.toURI().toString());
            if(!image.isError()) {
                imageBefore.setProjectImage(image);
                setImageFxObjectProperty(imageBefore);
                return imageBefore.getProjectImage();
            }else
                throw new ImageException("open file exception");

        } catch (NullPointerException e) {
            throw new ImageException("open file exception");
        }
    }
}
