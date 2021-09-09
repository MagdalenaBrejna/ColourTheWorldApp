package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.algorithms.ImageSettings;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ProjectSaveException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import pl.magdalena.brejna.colourtheworldapp.models.Project;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectModel;

public class MainProjectController {

    private ProjectModel projectModel;
    private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";

    //controls connected with saving new project
    @FXML
    private Button saveProjectButton;
    @FXML
    private TextField projectNameTextField;
    @FXML
    private ComboBox<Project> projectChoiceComboBox;

    //controls containing photos
    @FXML
    private ImageView photoImageView;
    @FXML
    private ImageView colouringBookImageView;
    @FXML
    private AnchorPane photoBasePane;
    @FXML
    private AnchorPane projectBasePane;
    @FXML
    private ScrollPane photoScrollPane;
    @FXML
    private ScrollPane projectScrollPane;

    //controls connected with processing photo
    @FXML
    private Button openImageButton;
    @FXML
    private Button createColouringBookButton;
    @FXML
    private Button openZoomButton;
    @FXML
    private Button saveColouringBookButton;
    @FXML
    private Button closeProjectButton;
    @FXML
    private Button deleteImageButton;

    //controls connected with slider
    @FXML
    private SplitPane splitPane;
    @FXML
    private Slider dilationSlider;
    @FXML
    private Slider contrastSlider;

    //class initialization - init project, set bindings, set actionListeners
    public void initialize(){
        initNewProject();
        loadProjectList();
        setBindings();
        setActionListeners();
    }

    //set projects to the list
    private void loadProjectList(){
        projectChoiceComboBox.setItems(ProjectListModel.getProjectList());
    }

    //init new project
    private void initNewProject(){
        this.projectModel = new ProjectModel();
        this.projectModel.init();
        this.projectNameTextField.setText("newProject");
    }

    //set bindings - make some controls disabled in special conditions
    private void setBindings(){
        this.createColouringBookButton.disableProperty().bind(this.photoImageView.imageProperty().isNull());
        this.openZoomButton.disableProperty().bind(this.colouringBookImageView.imageProperty().isNull());
        this.saveColouringBookButton.disableProperty().bind(this.colouringBookImageView.imageProperty().isNull());
        this.deleteImageButton.disableProperty().bind(this.photoImageView.imageProperty().isNull());
        this.dilationSlider.disableProperty().bind(this.colouringBookImageView.imageProperty().isNull());
        this.contrastSlider.disableProperty().bind(this.colouringBookImageView.imageProperty().isNull());
    }

    //set action listeners
    private void setActionListeners(){
        setSplitPaneListener();
        setDilationSliderListener();
        setContrastSliderListener();
        setProjectChoiceComboBoxListener();
    }

    //set splitPane listener
    private void setSplitPaneListener(){
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});
    }

    //set dilationSlider listener
    private void setDilationSliderListener(){
        dilationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            colouringBookImageView.setImage(projectModel.dilate((Double) newValue));
        });
    }

    //set contrastSlider listener
    private void setContrastSliderListener(){
        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            colouringBookImageView.setImage(projectModel.makeContrast((Double) newValue));
        });
    }

    //set projectChoiceComboBox listener
    private void setProjectChoiceComboBoxListener(){
        projectChoiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue == null || ProjectListModel.containsProject(oldValue))
                loadSelectedProject(newValue);
            else
                loadBlankProject();
        });
    }

    //set scroll start position
    private void setScrollPaneLocation(ScrollPane scrollPane){
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
    }

    //open project selected in ComboBox
    private void loadSelectedProject(Project newProject){
        projectModel.loadProject(newProject);
        setProjectComponents();
    }

    //set selected project values to the project layout
    private void setProjectComponents(){
        photoImageView.setImage(projectModel.getPhotoImage());
        colouringBookImageView.setImage(projectModel.getProjectImage());
        projectNameTextField.setText(projectModel.getActiveProject().getProjectName());
        blockNextSave();
    }

    //load empty project layout
    private void loadBlankProject(){
        App.setCenterLayout(MAIN_PROJECT_FXML);
    }

    //create colouring book based on uploaded photo
    @FXML
    private void createColouringBook(){
        setScrollPaneLocation(projectScrollPane);
        colouringBookImageView.setImage(projectModel.createColouringBook());
    }

    //set uploaded photo to the photoImageView
    @FXML
    private void selectImage(){
        try {
            setScrollPaneLocation(photoScrollPane);
            photoImageView.setImage(projectModel.loadImage());
            openImageButton.setDisable(true);
        }catch(ImageLoadingException exception){
           exception.callErrorMessage();
        }
    }

    //save photo stored in colouringBookImageView in computer files
    @FXML
    private void saveColouringBook(){
       try {
            projectModel.saveColouringBook();
       } catch (ImageProcessingException exception) {
           exception.callErrorMessage();
       }
    }

    //delete photo stored in photoImageView
    @FXML
    private void deleteImage(){
        projectModel.deleteImage();
        photoImageView.setImage(null);
        colouringBookImageView.setImage(null);
        openImageButton.setDisable(false);
    }

    //if the project is unsaved ask for confirmation, else close project
    @FXML
    private void closeProject(){
        projectModel.closeProject();
    }

    //save current project
    @FXML
    private void saveProject(){
        try {
            projectModel.saveActiveProject(projectNameTextField.textProperty());
            loadProjectList();
            blockNextSave();
        }catch(ProjectSaveException exception){
            exception.callErrorMessage();
        }
    }

    //prevent saving for the second time
    private void blockNextSave(){
        projectNameTextField.setDisable(true);
        saveProjectButton.disableProperty().unbind();
        saveProjectButton.setDisable(true);
    }

    //open new Window with colouring book
    @FXML
    private void openZoomWindow(){
        projectModel.showZoom();
    }

    //delete project selected in ComboBox
    @FXML
    private void clickProjectChoiceComboBox(MouseEvent mouseClickEvent){
        if(mouseClickEvent.getButton().equals(MouseButton.SECONDARY)) {
            ProjectListModel.deleteProjectOnRightClick(projectChoiceComboBox.getValue());
            loadProjectList();
        }
    }

    //set zooming a photo in the photoScrollPane
    @FXML
    private void scrollPhoto(ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, photoBasePane);
    }

    //set zooming a photo in the projectScrollPane
    @FXML
    private void scrollProject(ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, projectBasePane);
    }

    @FXML
    public void openNew(){
        projectModel.openNewProject();
    }
}