package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import java.io.File;
import java.util.Objects;

public class Project {

    private SimpleStringProperty projectName = new SimpleStringProperty();
    private ObjectProperty<Image> projectImage = new SimpleObjectProperty<>();
    private ObjectProperty<Image> colouringBookImage = new SimpleObjectProperty<>();
    private File sourceFile;
    private Double dilationValue = 0.0;
    private Double contrastValue = 0.0;

    public String toString() {
        return projectName.getValue();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Project))
            return false;
        Project project = (Project) o;
        return Objects.equals(getProjectName(), project.getProjectName()) &&
                Objects.equals(getSourceFile(), project.getSourceFile()) &&
                Objects.equals(getDilationValue(), project.getDilationValue()) &&
                Objects.equals(getContrastValue(), project.getContrastValue());
    }

    public int hashCode() {
        return Objects.hash(getProjectName(), getProjectImage(), getColouringBookImage(), getSourceFile(), getDilationValue(), getContrastValue());
    }


    public Double getContrastValue() {
        return contrastValue;
    }

    public void setContrastValue(Double contrastValue) {
        this.contrastValue = contrastValue;
    }


    public void setDilationValue(Double newValue){
        dilationValue = newValue;
    }

    public Double getDilationValue(){
        return dilationValue;
    }


    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }


    public Image getColouringBookImage() {
        return colouringBookImage.get();
    }

    public ObjectProperty<Image> colouringBookImageProperty() {
        return colouringBookImage;
    }

    public void setColouringBookImage(Image colouringBookImage) {
        this.colouringBookImage.set(colouringBookImage);
    }


    public String getProjectName() {
        return projectName.get();
    }

    public SimpleStringProperty projectNameProperty() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
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
