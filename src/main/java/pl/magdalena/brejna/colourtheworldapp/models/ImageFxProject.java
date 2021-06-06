package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;

public class ImageFxProject {

    private SimpleStringProperty imageProjectName = new SimpleStringProperty();
    private ObjectProperty<Image> projectImage = new SimpleObjectProperty<>();
    private ObjectProperty<Image> readyImage = new SimpleObjectProperty<>();
    private File sourceFile;

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public String toString() {
        return "imageProjectName = " + imageProjectName;
    }

    public Image getReadyImage() {
        return readyImage.get();
    }

    public ObjectProperty<Image> readyImageProperty() {
        return readyImage;
    }

    public void setReadyImage(Image readyImage) {
        this.readyImage.set(readyImage);
    }



    public String getImageProjectName() {
        return imageProjectName.get();
    }

    public SimpleStringProperty imageProjectNameProperty() {
        return imageProjectName;
    }

    public void setImageProjectName(String imageProjectName) {
        this.imageProjectName.set(imageProjectName);
    }


    public Image getProjectImage() {
        return projectImage.get();
    }

    public ObjectProperty<Image> projectImageProperty() {
        return projectImage;
    }

    public void setProjectImage(Image projectImage) {
        this.projectImage.set(projectImage);
    }
}
