package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.controllers.ZoomController;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectModel {

    private static final String ZOOM_FXML = "/fxml.files/ZoomWindow.fxml";

    //ProjectModel contains an active project
    UserProject activeProject;

    //elemnts necessary to serve zoomWindow
    FXMLLoader loader;
    ZoomController zoomController;
    Stage newWindow;

    //return active project
    public UserProject getActiveProject(){
        return activeProject;
    }

    //class initialization
    public void init(){
        activeProject = new UserProject();
        setZoom();
    }

    //set name of the activeProject with a text stored in textField
    public void save(StringProperty textProperty){
        activeProject.setImageProjectName(textProperty.getValue());
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
        if(activeProject.getImageProjectName() != null)
            fileChooser.setInitialFileName(activeProject.getImageProjectName());
        fileChooser.setTitle("Save project...");

        File directory = new File(System.getProperty("user.home"), "Pictures/ColourTheWorld");
        if(!directory.exists())
            directory.mkdirs();
        fileChooser.setInitialDirectory(directory);

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

            zoomController = loader.getController();
            zoomController.initData(updatedImage);
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

            zoomController = loader.getController();
            zoomController.initData(updatedImage);
        }
        return updatedImage;
    }

    //open file chooser to let find photo (jpg or png), add it to the active project
    public Image loadImage () throws ImageException {

        try {
            FileChooser fileChooser = new FileChooser();

            File directory = new File(System.getProperty("user.home"), "Pictures");
            fileChooser.setInitialDirectory(directory);

            fileChooser.setTitle("Open picture...");

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

    //Create initial stage for the zoomWindow
    public void setZoom(){

        loader = new FXMLLoader(getClass().getResource(ZOOM_FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        zoomController = loader.getController();
        zoomController.initData(null);

        newWindow = new Stage();
        newWindow.setTitle("Zoom");
        newWindow.setScene(new Scene(root));
    }

    //Show adjusted zoomWindow with readyImage
    public void showZoom(){
        zoomController = loader.getController();
        zoomController.initData(activeProject.getReadyImage());

        newWindow.setWidth(activeProject.getReadyImage().getWidth());
        newWindow.setHeight(activeProject.getReadyImage().getHeight());
        newWindow.show();
    }
}
