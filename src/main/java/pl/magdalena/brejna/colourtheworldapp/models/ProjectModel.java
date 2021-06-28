package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.controllers.ZoomController;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class ProjectModel {

    private final String ZOOM_FXML = "/fxml.files/ZoomLayout.fxml";
    private final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtonsLayout.fxml";

    //ProjectModel contains an active project
    private Project activeProject;

    //elements necessary to serve zoomLayout
    private FXMLLoader loader;
    private ZoomController zoomController;
    private Stage newWindow;

    //class initialization
    public void init(){
        activeProject = new Project();
        setZoom();
    }

    //return active project
    public Project getActiveProject(){
        return activeProject;
    }

    //set name of the activeProject with a text stored in textField
    public void saveActiveProject(StringProperty nameTextProperty){
        activeProject.setProjectName(nameTextProperty.getValue());
        ProjectListModel.addProjectToList(activeProject);
    }

    public void closeProject(){
        if(!isSaved())
            showCloseConfirmationDialog();
        else
            App.setCenterLayout(MAIN_MENU_BUTTONS_FXML);
    }

    //ask for close confirmation
    private void showCloseConfirmationDialog(){
        DialogsUtils.showConfirmationDialog("close.title", "close.text")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> App.setCenterLayout(MAIN_MENU_BUTTONS_FXML));
    }

    //check if the active project is saved
    public boolean isSaved(){
        if(!ProjectListModel.containsProject(activeProject))
            return false;
        return true;
    }

    //delete photos from active project
    public void deleteImage(){
        activeProject.setProjectImage(null);
        activeProject.setColouringBookImage(null);
    }

    //transform the project photo to colouring book
    public Image createColouringBook(){
        Image newImage = EdgeDetection.detectEdges(activeProject.getSourceFile(), 0.0, 150.0);
        activeProject.setColouringBookImage(newImage);
        return activeProject.getColouringBookImage();
    }

    //set project selected in ComboBox as activeProject
    public void updateProject(Project project){
        activeProject = project;
    }

    //open file chooser to let choose location and name of saving file, save photo as a png
    public void saveColouringBook() throws ImageException, IOException{
        FileChooser fileChooser = new FileChooser();
        setSaveFileChooserSettings(fileChooser);

        if(activeProject.getProjectName() != null)
            fileChooser.setInitialFileName(activeProject.getProjectName());

        setDirectorySettings(fileChooser);
        saveImage(fileChooser);
    }

    //set settings of FileChooser
    private void setSaveFileChooserSettings(FileChooser fileChooser){
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png"));
        fileChooser.setTitle("Save project...");
    }

    //set settings of Directory
    private void setDirectorySettings(FileChooser fileChooser){
        File directory = new File(System.getProperty("user.home"), "Pictures/ColourTheWorld");
        if(!directory.exists())
            directory.mkdirs();
        fileChooser.setInitialDirectory(directory);
    }

    //save image in computer files
    private void saveImage(FileChooser fileChooser) throws ImageException, IOException{
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(activeProject.getColouringBookImage(), null), "png", file);
        }else
            throw new ImageException("Save file exception.");
    }

    //open file chooser to let find photo (jpg or png), add it to the active project
    public Image loadImage () throws ImageException {
        try {
            FileChooser fileChooser = new FileChooser();
            setOpenFileChooserSettings(fileChooser);

            File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
            openFile(file);
            return activeProject.getProjectImage();

        } catch (NullPointerException e) {
            throw new ImageException("open file exception");
        }
    }

    //set settings of FileChooser
    private void setOpenFileChooserSettings(FileChooser fileChooser){
        File directory = new File(System.getProperty("user.home"), "Pictures");
        fileChooser.setInitialDirectory(directory);

        fileChooser.setTitle("Open picture...");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
    }

    //open file
    private void openFile(File file) throws ImageException{
        Image image = new Image(file.toURI().toString());
        if (!image.isError()) {
            activeProject.setSourceFile(file);
            activeProject.setProjectImage(image);
        } else
            throw new ImageException("open file exception");
    }

    //update readyImage with parameter set using slider
    public Image dilate(Double sliderValue){
        Image updatedImage = null;
        if(activeProject.getColouringBookImage() != null) {
            activeProject.setDilationValue(sliderValue);
            updatedImage = updateImage();
        }
        return updatedImage;
    }

    //update readyImage with contrast parameter set using slider
    public Image makeContrast(Double sliderValue){
        Image updatedImage =  null;
        if(activeProject.getColouringBookImage() != null) {
            activeProject.setContrastValue(sliderValue);
            updatedImage = updateImage();
        }
        return updatedImage;
    }

    //update Image
    private Image updateImage(){
        Image updatedImage = EdgeDetection.detectEdges(activeProject.getSourceFile(), activeProject.getDilationValue(), activeProject.getContrastValue());
        activeProject.setColouringBookImage(updatedImage);
        updateZoomImage(updatedImage);

        return updatedImage;
    }

    //update zoomImage
    private void updateZoomImage(Image image){
        zoomController = loader.getController();
        zoomController.setZoomImage(image);
    }

    //Create initial stage for the zoomWindow
    private void setZoom(){
        loader = new FXMLLoader(getClass().getResource(ZOOM_FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = setRoot();
        updateZoomImage(null);

        newWindow = new Stage();
        newWindow.setTitle("Zoom");
        newWindow.setScene(new Scene(root));
    }

    //Load root
    private Parent setRoot(){
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    //Show adjusted zoomWindow with readyImage
    public void showZoom(){
        updateZoomImage(activeProject.getColouringBookImage());

        newWindow.setWidth(activeProject.getColouringBookImage().getWidth());
        newWindow.setHeight(activeProject.getColouringBookImage().getHeight());
        newWindow.show();
    }
}
