package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class ImageFX {

    private SimpleStringProperty  imageProjectName = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public String getImageProjectName() {
        return imageProjectName.get();
    }

    public SimpleStringProperty imageProjectNameProperty() {
        return imageProjectName;
    }

    public void setImageProjectName(String imageProjectName) {
        this.imageProjectName.set(imageProjectName);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }
}
