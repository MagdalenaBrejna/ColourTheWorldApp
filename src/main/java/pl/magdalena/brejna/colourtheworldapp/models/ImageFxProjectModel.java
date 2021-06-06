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

public class ImageFxProjectModel {

    //ImageFxProjectModel contains an active project and a list of created project
    ArrayList<ImageFxProject> imageFxProjectObservableList = new ArrayList<>();
    ImageFxProject activeProject;

    //class initialization
    public void init(){ }

    //create current active project, add it to the list, set its name with text stored in textField
    public void save(StringProperty textProperty){
        activeProject = new ImageFxProject();
        imageFxProjectObservableList.add(activeProject);
        imageFxProjectObservableList.get(imageFxProjectObservableList.size() - 1).setImageProjectName(textProperty.getValue());
    }

    //delete photos from active project
    public void delete(){
        activeProject.setProjectImage(null);
        activeProject.setReadyImage(null);
    }

    //transform the project photo to colouring book
    public Image createPhoto(){
        Image newImage = EdgeDetection.detectEdges(activeProject.getSourceFile());
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
                imageFxProjectObservableList.get(imageFxProjectObservableList.size() - 1).setProjectImage(image);
                return imageFxProjectObservableList.get(imageFxProjectObservableList.size() - 1).getProjectImage();
            } else
                throw new ImageException("open file exception");

        } catch (NullPointerException e) {
            throw new ImageException("open file exception");
        }
    }
}
