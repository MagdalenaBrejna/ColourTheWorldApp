package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ProjectSaveException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ProjectModel {

    private final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtonsLayout.fxml";

    //ProjectModel contains an active project and zoom object
    private Project activeProject;
    private ProjectDao activeProjectDao;
    private Zoom zoom;

    //class initialization
    public void init(){
        activeProject = new Project();
        activeProjectDao = new ProjectDao();
        updateProjectList();
        zoom = new Zoom();
    }

    public void updateProjectList(){
        try {
            ProjectListModel.setProjectList(activeProjectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    //return active project
    public Project getActiveProject(){
        return activeProject;
    }

    //return loaded photo
    public Image getPhotoImage(){
        if(isPhotoSelected())
            return tryOpenFile();
        return null;
    }

    //get loaded photo
    private Image tryOpenFile(){
        try {
            return openFile(activeProject.getSourceFile());
        } catch (ImageLoadingException imageException) {
            imageException.callErrorMessage();
            return null;
        }
    }

    //return colouring book
    public Image getProjectImage(){
        if(isPhotoSelected())
            return createColouringBook();
        return null;
    }

    //check if active project contains photo
    private boolean isPhotoSelected(){
        if(!activeProject.getSourceFile().equals(""))
            return true;
        return false;
    }

    //open file chooser to let find photo (jpg or png), add it to the active project
    public Image loadImage () throws ImageLoadingException {
        try {
            FileChooser fileChooser = new FileChooser();
            setOpenFileChooserSettings(fileChooser);

            File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
            activeProject.setSourceFile(file.toURI().toString());
            Image image = openFile(file.toURI().toString());

            if(!activeProject.getSourceFile().equals(""))
                activeProjectDao.updateProject(activeProject);
            return image;

        } catch (NullPointerException exception) {
            throw new ImageLoadingException("loading exception");
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
    public Image openFile(String file) throws ImageLoadingException{
        Image image = new Image(file);
        if (!image.isError()) {
            activeProject.setSourceFile(file);
            return image;
        } else
            throw new ImageLoadingException("open file exception");
    }

    //check if current project is save. If yes update project, if not ask for a confirmation
    public void loadProject(Project project){
        if(!isSaved())
            showReplaceConfirmationDialog(project);
        else
            updateProject(project);
    }

    //ask for confirmation
    private void showReplaceConfirmationDialog(Project project){
        DialogsUtils.showConfirmationDialog("close.title", "close.text")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> updateProject(project));
    }

    //set project selected in ComboBox as activeProject
    private void updateProject(Project project){
        activeProject = project;
    }

    //transform the project photo to colouring book
    public Image createColouringBook(){
        return EdgeDetection.detectEdges(activeProject);
    }

    //save activeProject with a text stored in textField
    public void saveActiveProject(StringProperty nameTextProperty) throws ProjectSaveException{
        if(!activeProjectDao.isProject(nameTextProperty.getValue())) {
            activeProject.setProjectName(nameTextProperty.getValue());
            activeProjectDao.insertProject(activeProject);
            updateProjectList();
        }else
            throw new ProjectSaveException("project name already exists");
    }

    //open file chooser to let choose location and name of saving file, save photo as a png
    public void saveColouringBook() throws ImageProcessingException {
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
    private void saveImage(FileChooser fileChooser) throws ImageProcessingException{
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(getProjectImage(), null), "png", file);
        } catch (IOException exception) {
            throw new ImageProcessingException("Saving file exception");
        }
    }

    //close active project
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
    private boolean isSaved(){
        if(!ProjectListModel.containsProject(activeProject))
            return false;
        return true;
    }

    //restart active project
    public void deleteImage(){
        activeProject.setSourceFile("");
        setInitialProjectValues();
        activeProjectDao.updateProject(activeProject);
    }

    //set project parameters initial values
    private void setInitialProjectValues(){
        activeProject.setDilationValue(0.0);
        activeProject.setContrastValue(150.0);
    }

    //update readyImage with parameter set using slider
    public Image dilate(Double sliderValue){
        Image updatedImage = null;
        if(createColouringBook() != null) {
            activeProject.setDilationValue(sliderValue);
            updatedImage = updateImage();
            activeProjectDao.updateProject(activeProject);
        }
        return updatedImage;
    }

    //update readyImage with contrast parameter set using slider
    public Image makeContrast(Double sliderValue){
        Image updatedImage =  null;
        if(createColouringBook() != null) {
            activeProject.setContrastValue(sliderValue);
            updatedImage = updateImage();
            activeProjectDao.updateProject(activeProject);
        }
        return updatedImage;
    }

    //update Image
    private Image updateImage(){
        Image updatedImage = EdgeDetection.detectEdges(activeProject);
        zoom.updateZoomImage(updatedImage);
        return updatedImage;
    }

    //show zoom window
    public void showZoom(){
        zoom.showZoom(getProjectImage());
    }
}
