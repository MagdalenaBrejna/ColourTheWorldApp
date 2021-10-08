package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ProjectSaveException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import pl.magdalena.brejna.colourtheworldapp.windows.FullView;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public final class ProjectModel {

    private final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtonsLayout.fxml";
    private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";

    private final String LOADING_EXCEPTION_MESSAGE = "loading exception";
    private final String SAVE_EXCEPTION_MESSAGE = "loading exception";
    private final String CLOSE_TITLE = "close.title";
    private final String CLOSE_TEXT = "close.text";
    private final String SYSTEM_PROPERTY = "user.home";
    private final String CHILD_DIRECTORY_FILE = "Pictures/ColourTheWorld";
    private final String SAVE_FILE_CHOOSER_TITLE = "Save project...";
    private final String OPEN_FILE_CHOOSER_TITLE = "Open picture...";
    private final String FILE_CHOOSER_FILTER_DESCRIPTION = "Image Files";
    private final String FILE_CHOOSER_CHILD = "Pictures";

    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

    //ProjectModel contains an active project and zoom object
    private Project activeProject;
    private ProjectDao activeProjectDao;
    private FullView fullView;

    //class initialization
    public final void init(){
        activeProject = new Project();
        activeProjectDao = new ProjectDao();
        fullView = new FullView();
        updateProjectList();
    }

    //update the list of projects stored in the application with the list stored in the database
    public final void updateProjectList(){
        try {
            ProjectListModel.setProjectList(activeProjectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    //return active project
    public final Project getActiveProject(){
        return activeProject;
    }

    //return loaded photo
    public final Image getPhotoImage() throws ImageLoadingException{
        if(isPhotoSelected())
            return tryOpenFile();
        return null;
    }

    //return colouring book
    public final Image getProjectImage(){
        if(isPhotoSelected())
            return createColouringBook();
        return null;
    }

    //check if active project contains photo
    private final boolean isPhotoSelected(){
        if(!activeProject.getSourceFile().equals(""))
            return true;
        return false;
    }

    //open file chooser to let find photo (jpg or png), add it to the active project
    public final Image loadImage () throws ImageLoadingException {
        try {
            final FileChooser fileChooser = new FileChooser();
            setOpenFileChooserSettings(fileChooser);

            final File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
            activeProject.setSourceFile(file.toURI().toString());
            final Image image = openFile(activeProject.getSourceFile());

            if(!activeProject.getSourceFile().equals("")) {
                activeProjectDao.updateProject(activeProject);
                updateProjectList();
            }
            return image;

        } catch (NullPointerException exception) {
            throw new ImageLoadingException(LOADING_EXCEPTION_MESSAGE);
        }
    }

    //set settings of FileChooser
    private final void setOpenFileChooserSettings(final FileChooser fileChooser){
        final File directory = new File(System.getProperty(SYSTEM_PROPERTY), FILE_CHOOSER_CHILD);
        fileChooser.setInitialDirectory(directory);
        fileChooser.setTitle(OPEN_FILE_CHOOSER_TITLE);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(FILE_CHOOSER_FILTER_DESCRIPTION, "*.png", "*.jpg"));
    }

    //get loaded photo
    private final Image tryOpenFile(){
        return openFile(activeProject.getSourceFile());
    }

    //open file given as string
    public final Image openFile(final String file) throws ImageLoadingException{
        Image image = new Image(file);
        if (!image.isError())
            return image;
        else
            throw new ImageLoadingException(LOADING_EXCEPTION_MESSAGE);
    }

    //check if current project is save. If yes update project, else ask for a confirmation
    public final void loadProject(final Project project){
        if(App.getStoredProject() != null)
            updateProject(project);
        else if(!isSaved())
            showReplaceConfirmationDialog(project);
        else
            updateProject(project);
    }

    //ask for close project confirmation
    private final void showReplaceConfirmationDialog(final Project project){
        DialogsUtils.showConfirmationDialog(CLOSE_TITLE, CLOSE_TEXT)
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> updateProject(project));
    }

    //set project selected in ComboBox as activeProject
    private final void updateProject(final Project project){
        activeProject = project;
    }

    //transform the project photo to colouring book
    public final Image createColouringBook(){
        try {
            return EdgeDetection.detectEdges(activeProject);
        } catch (ImageProcessingException exception) {
            exception.callErrorMessage();
            return null;
        }
    }

    //save activeProject with a text stored in textField
    public final void saveActiveProject(final StringProperty nameTextProperty) throws ProjectSaveException{
        if(!activeProjectDao.isProject(nameTextProperty.getValue())) {
            activeProject.setProjectName(nameTextProperty.getValue());
            activeProjectDao.insertProject(activeProject);
            updateProjectList();
        }else
            throw new ProjectSaveException(SAVE_EXCEPTION_MESSAGE);
    }

    //open file chooser to let choose location and name of saving file, save photo as a png
    public final void saveColouringBook() throws ImageProcessingException {
        final FileChooser fileChooser = new FileChooser();
        setSaveFileChooserSettings(fileChooser);

        if(activeProject.getProjectName() != null)
            fileChooser.setInitialFileName(activeProject.getProjectName());

        setDirectorySettings(fileChooser);
        saveImage(fileChooser);
    }

    //set settings of the FileChooser
    private final void setSaveFileChooserSettings(final FileChooser fileChooser){
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(FILE_CHOOSER_FILTER_DESCRIPTION, "*.png"));
        fileChooser.setTitle(SAVE_FILE_CHOOSER_TITLE);
    }

    //set settings of a Directory
    private final void setDirectorySettings(final FileChooser fileChooser){
        final File directory = new File(System.getProperty(SYSTEM_PROPERTY), CHILD_DIRECTORY_FILE);
        if(!directory.exists())
            directory.mkdirs();
        fileChooser.setInitialDirectory(directory);
    }

    //save an image in the computer files
    private final void saveImage(final FileChooser fileChooser) throws ImageProcessingException{
        final File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(getProjectImage(), null), "png", file);
        } catch (IOException exception) {
            throw new ImageProcessingException(SAVE_EXCEPTION_MESSAGE);
        }
    }

    //close the active project
    public final void closeProject(){
        if(!isSaved())
            showCloseConfirmationDialog();
        else
            App.setCenterLayout(MAIN_MENU_BUTTONS_FXML);
    }

    //ask for close confirmation
    private final void showCloseConfirmationDialog(){
        DialogsUtils.showConfirmationDialog(CLOSE_TITLE, CLOSE_TEXT)
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> App.setCenterLayout(MAIN_MENU_BUTTONS_FXML));
    }

    //check if the active project is saved
    public final boolean isSaved(){
        if(!ProjectListModel.containsProject(activeProject))
            return false;
        return true;
    }

    //restart active project
    public final void deleteImage(){
        activeProject.setSourceFile("");
        setInitialProjectValues();
        activeProjectDao.updateProject(activeProject);
    }

    //set project parameters initial values
    private final void setInitialProjectValues(){
        activeProject.setDilationValue(DEFAULT_DILATION_VALUE);
        activeProject.setContrastValue(DEFAULT_CONTRAST_VALUE);
    }

    public final void setProjectDilationValue(final double dilationValue){
        activeProject.setDilationValue(dilationValue);
    }

    //update readyImage with parameter set using slider
    public final Image dilate(final Double sliderValue){
        Image updatedImage = null;
        if(createColouringBook() != null) {
            activeProject.setDilationValue(sliderValue);
            updatedImage = updateImage();
            if(isSaved())
                activeProjectDao.updateProject(activeProject);
        }
        return updatedImage;
    }

    public final void setProjectContrastValue(final double contrastValue){
        activeProject.setContrastValue(contrastValue);
    }

    //update readyImage with contrast parameter set using slider
    public final Image makeContrast(final Double sliderValue){
        Image updatedImage =  null;
        if(createColouringBook() != null) {
            activeProject.setContrastValue(sliderValue);
            updatedImage = updateImage();
            if(isSaved())
                activeProjectDao.updateProject(activeProject);
        }
        return updatedImage;
    }

    //update project Image with new dilation and contrast values
    private final Image updateImage(){
        Image updatedImage = null;
        try {
            updatedImage = EdgeDetection.detectEdges(activeProject);
        } catch (ImageProcessingException exception) {}
        fullView.updateFullViewImage(updatedImage);
        return updatedImage;
    }

    //show full view window
    public final void showFullView(){
        fullView.showFullView(getProjectImage());
    }

    //open empty project
    public final void openNewProject(){
        if(!isSaved())
            showOpenNewProjectConfirmationDialog();
        else
            App.setCenterLayout(MAIN_PROJECT_FXML);
    }

    //show confirmation dialog and open a blank project if the answer is ok
    private final void showOpenNewProjectConfirmationDialog(){
        DialogsUtils.showConfirmationDialog(CLOSE_TITLE, CLOSE_TEXT)
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> App.setCenterLayout(MAIN_PROJECT_FXML));
    }
}
