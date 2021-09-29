package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.algorithms.ImageSettings;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ProjectSaveException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectModel;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class MainProjectController {

    private ProjectModel projectModel;
    private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";

    //controls connected with saving new project
    @FXML
    private Button saveProjectButton;
    @FXML
    private TextField projectNameTextField;
    @FXML
    private TextField dilationTextField;
    @FXML
    private TextField contrastTextField;
    @FXML
    private ComboBox<Project> projectChoiceComboBox;

    //controls containing photos
    @FXML
    private ImageView photoImageView;
    @FXML
    private ImageView projectImageView;
    @FXML
    private StackPane photoBasePane;
    @FXML
    private StackPane projectBasePane;
    @FXML
    private ScrollPane photoScrollPane;
    @FXML
    private ScrollPane projectScrollPane;
    @FXML
    private Group photoGroup;
    @FXML
    private Group projectGroup;

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
        setBindings();
        initializeProjects();
        setActionListeners();
    }

    private void initializeProjects(){
        initNewProject();
        loadOverviewProject();
        loadProjectList();
    }

    //init new project
    private void initNewProject(){
        this.projectModel = new ProjectModel();
        this.projectModel.init();
        this.projectNameTextField.setText("newProject");
        ImageSettings.setScrollInitialValues(photoScrollPane);
        ImageSettings.setScrollInitialValues(projectScrollPane);
    }

    //load project if selected in the overview
    private void loadOverviewProject(){
        if(App.getStoredProject() != null) {
            loadSelectedProject(App.getStoredProject());
            App.setStoredProject(null);
        }
    }

    //set projects to the list
    private void loadProjectList(){
        projectChoiceComboBox.setItems(ProjectListModel.getProjectList());
    }

    //set bindings - make some controls disabled in special conditions
    private void setBindings(){
        createColouringBookButton.setDisable(true);
        this.openZoomButton.disableProperty().bind(this.projectImageView.imageProperty().isNull());
        this.saveColouringBookButton.disableProperty().bind(this.projectImageView.imageProperty().isNull());
        this.deleteImageButton.disableProperty().bind(this.photoImageView.imageProperty().isNull());
        setSlidersDisabledStatus(true);
    }

    private void setSlidersDisabledStatus(boolean value){
        setDilationSliderDisabledStatus(value);
        setContrastSliderDisabledStatus(value);
    }

    private void setDilationSliderDisabledStatus(boolean value){
        dilationSlider.setDisable(value);
        dilationTextField.setDisable(value);
    }

    private void setContrastSliderDisabledStatus(boolean value){
        contrastSlider.setDisable(value);
        contrastTextField.setDisable(value);
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
            projectImageView.setImage(projectModel.dilate((Double) newValue));
            checkLoadingCorrectness(projectImageView);
        });
    }

    //set contrastSlider listener
    private void setContrastSliderListener(){
        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectImageView.setImage(projectModel.makeContrast((Double) newValue));
            checkLoadingCorrectness(projectImageView);
        });
    }

    //set projectChoiceComboBox listener
    private void setProjectChoiceComboBoxListener(){
        projectChoiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadProjectList();
            if(oldValue == null || ProjectListModel.containsProject(oldValue))
                loadSelectedProject(newValue);
            else
                loadBlankProject();
        });
    }

    //open project selected in ComboBox
    private void loadSelectedProject(Project newProject){
        projectModel.loadProject(newProject);
        openImageButton.setDisable(true);
        setSlidersDisabledStatus(false);
        setProjectComponents();
    }

    //set selected project values to the project layout
    private void setProjectComponents(){
        try {
            projectNameTextField.setText(projectModel.getActiveProject().getProjectName());
            photoImageView.setImage(projectModel.getPhotoImage());
            projectImageView.setImage(projectModel.getProjectImage());
            blockNextSave();
        }catch(ImageLoadingException imageLoadingException) {
            imageLoadingException.callErrorMessage();
            restartImage();
        }
    }

    //load empty project layout
    private void loadBlankProject(){
        App.setCenterLayout(MAIN_PROJECT_FXML);
    }

    //create colouring book based on uploaded photo
    @FXML
    private void createColouringBook(){
        projectImageView.setImage(projectModel.createColouringBook());
        checkLoadingCorrectness(projectImageView);
        createColouringBookButton.setDisable(true);
        setSlidersDisabledStatus(false);
    }

    private void checkLoadingCorrectness(ImageView imageView){
        if(imageView.getImage() == null)
            restartImage();
    }

    //set uploaded photo to the photoImageView
    @FXML
    private void selectImage(){
        try {
            photoImageView.setImage(projectModel.loadImage());
            openImageButton.setDisable(true);
            createColouringBookButton.setDisable(false);
        }catch(ImageLoadingException exception){
            restartImage();
            exception.callErrorMessage();
        }
    }

    //save photo stored in colouringBookImageView in computer files
    @FXML
    private void saveColouringBook(){
       try {
            projectModel.saveColouringBook();
       } catch (ImageProcessingException exception) {
           restartImage();
           exception.callErrorMessage();
       }
    }

    //delete photo stored in photoImageView
    @FXML
    private void restartImage(){
        projectModel.deleteImage();
        photoImageView.setImage(null);
        projectImageView.setImage(null);
        openImageButton.setDisable(false);
        createColouringBookButton.setDisable(true);
        setSlidersDisabledStatus(true);
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

    //set zooming an image in the photoScrollPane
    @FXML
    private void scrollPhoto(ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, photoImageView);
    }

    //set zooming an image in the projectScrollPane
    @FXML
    private void scrollProject(ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, projectImageView);
    }

    //set pressing a project image in the photoScrollPane
    @FXML
    private void pressPhoto(MouseEvent mousePressEvent){
        ImageSettings.pressImage(mousePressEvent);
    }

    //set pressing a project image in the projectScrollPane
    @FXML
    private void pressProject(MouseEvent mousePressEvent){
        ImageSettings.pressImage(mousePressEvent);
    }

    //set dragging an image in the photoScrollPane
    @FXML
    private void dragPhoto(MouseEvent mousePressEvent){
        ImageSettings.dragImage(mousePressEvent, photoScrollPane, photoGroup);
    }

    //set dragging a project image in the projectScrollPane
    @FXML
    private void dragProject(MouseEvent mousePressEvent){
        ImageSettings.dragImage(mousePressEvent, projectScrollPane, projectGroup);
    }

    //open new project on a button click
    @FXML
    private void openNew(){
        projectModel.openNewProject();
    }

    @FXML
    private void setDilationFromField(){
        try {
            double dilationValue = Double.valueOf(dilationTextField.getText());
            if (dilationValue >= 0 && dilationValue <= 3) {
                projectImageView.setImage(projectModel.dilate(dilationValue));
                checkLoadingCorrectness(projectImageView);
            }
        }catch(NumberFormatException doubleException){
            DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text");
        }
    }

    @FXML
    private void setContrastFromField(){
        try {
            double contrastValue = Double.valueOf(contrastTextField.getText());
            if (contrastValue >= 0 && contrastValue <= 255) {
                projectImageView.setImage(projectModel.makeContrast(contrastValue));
                checkLoadingCorrectness(projectImageView);
            }
        }catch(NumberFormatException doubleException){
            DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text");
        }
    }

    @FXML
    private void switchDilationImpact(){
        Double sliderValue = projectModel.getActiveProject().getDilationValue();
        if(!dilationSlider.isDisable()) {
            projectImageView.setImage(projectModel.dilate(0.0));
            projectModel.setProjectDilationValue(sliderValue);
            setDilationSliderDisabledStatus(true);
        }else if(projectImageView.getImage() != null){
            projectImageView.setImage(projectModel.dilate(projectModel.getActiveProject().getDilationValue()));
            setDilationSliderDisabledStatus(false);
        }
    }

    @FXML
    private void switchContrastImpact(){
        Double sliderValue = projectModel.getActiveProject().getContrastValue();
        if(!contrastSlider.isDisable()) {
            projectImageView.setImage(projectModel.makeContrast(150.0));
            projectModel.setProjectContrastValue(sliderValue);
            setContrastSliderDisabledStatus(true);
        }else if(projectImageView.getImage() != null){
            projectImageView.setImage(projectModel.makeContrast(projectModel.getActiveProject().getContrastValue()));
            setContrastSliderDisabledStatus(false);
        }
    }
}