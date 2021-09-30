package pl.magdalena.brejna.colourtheworldapp.objects;

import javafx.beans.property.SimpleStringProperty;
import java.util.Objects;

public final class Project {

    private SimpleStringProperty projectName = new SimpleStringProperty();
    private String sourceFile = "";
    private Double dilationValue = 0.0;
    private Double contrastValue = 150.0;

    public String getProjectName() {
        return projectName.get();
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setDilationValue(Double newValue){
        dilationValue = newValue;
    }

    public Double getDilationValue(){
        return dilationValue;
    }

    public Double getContrastValue() {
        return contrastValue;
    }

    public void setContrastValue(Double contrastValue) {
        this.contrastValue = contrastValue;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Project))
            return false;
        Project project = (Project) object;
        return Objects.equals(getProjectName(), project.getProjectName()) &&
                Objects.equals(getSourceFile(), project.getSourceFile()) &&
                Objects.equals(getDilationValue(), project.getDilationValue()) &&
                Objects.equals(getContrastValue(), project.getContrastValue());
    }

    public int hashCode(){
        return Objects.hash(getProjectName(), getSourceFile(), getDilationValue(), getContrastValue());
    }

    public final String toString() {
        return projectName.getValue();
    }
}