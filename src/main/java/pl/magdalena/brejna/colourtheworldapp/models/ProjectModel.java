package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectModel {

    //ImageFxProjectModel contains an active project and a list of created project
    ArrayList<UserProject> projectObservableList = new ArrayList<>();
    UserProject activeProject;

    //class initialization
    public void init(){
        activeProject = new UserProject();
    }

    //create current active project, add it to the list, set its name with text stored in textField
    public void save(StringProperty textProperty){
        projectObservableList.add(activeProject);
        projectObservableList.get(projectObservableList.size() - 1).setImageProjectName(textProperty.getValue());
    }

    //check if the active project is saved
    public boolean isSaved(){
        if(activeProject.getImageProjectName() == null)
            return false;
        return true;
    }


    //delete photos from active project
    public void delete(){
        activeProject.setProjectImage(null);
        activeProject.setReadyImage(null);
    }

    //transform the project photo to colouring book
    public Image createPhoto(){
        Image newImage = EdgeDetection.detectEdges(activeProject.getSourceFile(), 0.0, 150.0);
        activeProject.setReadyImage(newImage);
        return activeProject.getReadyImage();
    }

    //open file chooser to let choose location and name of saving file, save photo as a png
    public void saveImage() throws ImageException, IOException{

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png"));

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(activeProject.getReadyImage(), null), "png", file);
        }else
            throw new ImageException("Save file exception.");
    }

    //update readyImage with parameter set using slider
    public Image dilate(Double sliderValue){
        Image updatedImage =  null;
        if(activeProject.getReadyImage() != null) {
            activeProject.setDilationValue(sliderValue);
            updatedImage = EdgeDetection.detectEdges(activeProject.getSourceFile(), activeProject.getDilationValue(), activeProject.getContrastValue());
            activeProject.setReadyImage(updatedImage);
        }
        return updatedImage;
    }

    //update readyImage with contrast parameter set using slider
    public Image makeContrast(Double sliderValue){
        Image updatedImage =  null;
        if(activeProject.getReadyImage() != null) {
            activeProject.setContrastValue(sliderValue);
            updatedImage = EdgeDetection.detectEdges(activeProject.getSourceFile(), activeProject.getDilationValue(), activeProject.getContrastValue());
            activeProject.setReadyImage(updatedImage);
        }
        return updatedImage;
    }

    //open file chooser to let find photo (jpg or png), add it to the active project
    public Image loadImage () throws ImageException {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

            Image image = new Image(file.toURI().toString());
            if (!image.isError()) {
                activeProject.setSourceFile(file);
                activeProject.setProjectImage(image);
                return activeProject.getProjectImage();
            } else
                throw new ImageException("open file exception");

        } catch (NullPointerException e) {
            throw new ImageException("open file exception");
        }
    }
}
