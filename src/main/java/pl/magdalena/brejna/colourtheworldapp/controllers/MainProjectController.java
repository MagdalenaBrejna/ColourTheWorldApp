package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.algorithms.ImageSettings;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ProjectSaveException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectModel;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public final class MainProjectController {

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
    private Button deleteImageButton;

    //controls connected with slider
    @FXML
    private SplitPane splitPane;
    @FXML
    private Slider dilationSlider;
    @FXML
    private Slider contrastSlider;

    //class initialization - init project, set bindings, set actionListeners
    public final void initialize(){
        setBindings();
        initializeProjects();
        setActionListeners();
    }

    //initialize project
    private final void initializeProjects(){
        initNewProject();
        loadOverviewProject();
        loadProjectList();
    }

    //initialize the new project
    private final void initNewProject(){
        this.projectModel = new ProjectModel();
        this.projectModel.init();
        this.projectNameTextField.setText("newProject");
    }

    //load project if selected in the project overview
    private final void loadOverviewProject(){
        if(App.getStoredProject() != null) {
            loadSelectedProject(App.getStoredProject());
            App.setStoredProject(null);
        }
    }

    //set projects to the combo box
    private final void loadProjectList(){
        projectChoiceComboBox.setItems(ProjectListModel.getProjectList());
    }

    //set bindings - make some controls disabled in special conditions
    private final void setBindings(){
        this.openZoomButton.disableProperty().bind(this.projectImageView.imageProperty().isNull());
        this.saveColouringBookButton.disableProperty().bind(this.projectImageView.imageProperty().isNull());
        this.deleteImageButton.disableProperty().bind(this.photoImageView.imageProperty().isNull());
        createColouringBookButton.setDisable(true);
        setSlidersDisabledStatus(true);
    }

    //set parameters' use
    private final void setSlidersDisabledStatus(final boolean value){
        setDilationSliderDisabledStatus(value);
        setContrastSliderDisabledStatus(value);
    }

    //set dilation use
    private final void setDilationSliderDisabledStatus(final boolean value){
        dilationSlider.setDisable(value);
        dilationTextField.setDisable(value);
    }

    //set contrast use
    private final void setContrastSliderDisabledStatus(final boolean value){
        contrastSlider.setDisable(value);
        contrastTextField.setDisable(value);
    }

    //set action listeners
    private final void setActionListeners(){
        setSplitPaneListener();
        setDilationSliderListener();
        setContrastSliderListener();
        setProjectChoiceComboBoxListener();
    }

    //set splitPane listener
    private final void setSplitPaneListener(){
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});
    }

    //set dilationSlider listener
    private final void setDilationSliderListener(){
        dilationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectImageView.setImage(projectModel.dilate((Double) newValue));
            checkLoadingCorrectness(projectImageView);
        });
    }

    //set contrastSlider listener
    private final void setContrastSliderListener(){
        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectImageView.setImage(projectModel.makeContrast((Double) newValue));
            checkLoadingCorrectness(projectImageView);
        });
    }

    //set projectChoiceComboBox listener
    private final void setProjectChoiceComboBoxListener(){
        projectChoiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadProjectList();
            if(oldValue == null || ProjectListModel.containsProject(oldValue))
                loadSelectedProject(newValue);
            else
                loadBlankProject();
        });
    }

    //open selected project
    private final void loadSelectedProject(final Project newProject){
        projectModel.loadProject(newProject);
        openImageButton.setDisable(true);
        setSlidersDisabledStatus(false);
        setProjectComponents();
    }

    //set selected project values to the project layout
    private final void setProjectComponents(){
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
    private final void loadBlankProject(){
        App.setCenterLayout(MAIN_PROJECT_FXML);
    }

    //create colouring book based on uploaded photo
    @FXML
    private final void createColouringBook(){
        projectImageView.setImage(projectModel.createColouringBook());
        checkLoadingCorrectness(projectImageView);
        createColouringBookButton.setDisable(true);
        setSlidersDisabledStatus(false);
    }

    //check if a project contains a valid source file
    private final void checkLoadingCorrectness(final ImageView imageView){
        if(imageView.getImage() == null) {
            final ImageLoadingException imageLoadingException = new ImageLoadingException("wrong source file");
            imageLoadingException.callErrorMessage();
            restartImage();
        }
    }

    //set uploaded photo to the photoImageView
    @FXML
    private final void selectImage(){
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
    private final void saveColouringBook(){
       try {
            projectModel.saveColouringBook();
       } catch (ImageProcessingException exception) {
           restartImage();
           exception.callErrorMessage();
       }
    }

    //delete photo stored in photoImageView
    @FXML
    private final void restartImage(){
        projectModel.deleteImage();
        photoImageView.setImage(null);
        projectImageView.setImage(null);
        openImageButton.setDisable(false);
        createColouringBookButton.setDisable(true);
        setSlidersDisabledStatus(true);
    }

    //if the project is unsaved ask for confirmation, else close project
    @FXML
    private final void closeProject(){
        projectModel.closeProject();
    }

    //save current project
    @FXML
    private final void saveProject(){
        try {
            projectModel.saveActiveProject(projectNameTextField.textProperty());
            loadProjectList();
            blockNextSave();
        }catch(ProjectSaveException exception){
            exception.callErrorMessage();
        }
    }

    //prevent saving for the second time
    private final void blockNextSave(){
        projectNameTextField.setDisable(true);
        saveProjectButton.disableProperty().unbind();
        saveProjectButton.setDisable(true);
    }

    //open a new Window with the colouring book
    @FXML
    private final void openFullViewWindow(){
        projectModel.showFullView();
    }

    //delete project selected in the ComboBox
    @FXML
    private final void clickProjectChoiceComboBox(final MouseEvent mouseClickEvent){
        if(mouseClickEvent.getButton().equals(MouseButton.SECONDARY)) {
            ProjectListModel.deleteProjectOnMouseClick(projectChoiceComboBox.getValue());
            loadProjectList();
        }
    }

    //set zooming an image in the photoScrollPane
    @FXML
    private final void scrollPhoto(final ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, photoImageView, projectImageView);
    }

    //set zooming an image in the projectScrollPane
    @FXML
    private final void scrollProject(final ScrollEvent mouseScrollEvent){
        ImageSettings.scrollImage(mouseScrollEvent, projectImageView, photoImageView);
    }

    //set pressing a project image in the photoScrollPane
    @FXML
    private final void pressPhoto(final MouseEvent mousePressEvent){
        ImageSettings.pressImage(mousePressEvent);
    }

    //set pressing a project image in the projectScrollPane
    @FXML
    private final void pressProject(final MouseEvent mousePressEvent){
        ImageSettings.pressImage(mousePressEvent);
    }

    //set dragging an image in the photoScrollPane
    @FXML
    private final void dragPhoto(final MouseEvent mousePressEvent){
        ImageSettings.dragImage(mousePressEvent, photoScrollPane, photoGroup);
    }

    //set dragging a project image in the projectScrollPane
    @FXML
    private final void dragProject(final MouseEvent mousePressEvent){
        ImageSettings.dragImage(mousePressEvent, projectScrollPane, projectGroup);
    }

    //open new project on a button click
    @FXML
    private final void openNew(){
        projectModel.openNewProject();
    }

    //get project dilation value from the text field and update image
    @FXML
    private final void setDilationFromField(){
        try {
            final double dilationValue = Double.valueOf(dilationTextField.getText());
            if (dilationValue >= 0 && dilationValue <= 3) {
                projectImageView.setImage(projectModel.dilate(dilationValue));
                checkLoadingCorrectness(projectImageView);
            }
        }catch(NumberFormatException doubleException){
            DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text");
        }
    }

    @FXML
    private final void setContrastFromField(){
        try {
            final double contrastValue = Double.valueOf(contrastTextField.getText());
            if (contrastValue >= 0 && contrastValue <= 255) {
                projectImageView.setImage(projectModel.makeContrast(contrastValue));
                checkLoadingCorrectness(projectImageView);
            }
        }catch(NumberFormatException doubleException){
            DialogsUtils.showConfirmationDialog("error.title", "numberFormatError.text");
        }
    }

    //make dilation value disabled if it is active or restore dilation value if it is not active
    @FXML
    private final void switchDilationImpact(){
        final Double sliderValue = projectModel.getActiveProject().getDilationValue();
        if(!dilationSlider.isDisable()) {
            projectImageView.setImage(projectModel.dilate(0.0));
            projectModel.setProjectDilationValue(sliderValue);
            setDilationSliderDisabledStatus(true);
        }else if(projectImageView.getImage() != null){
            projectImageView.setImage(projectModel.dilate(projectModel.getActiveProject().getDilationValue()));
            setDilationSliderDisabledStatus(false);
        }
    }

    //make contrast value disabled if it is active or restore contrast value if it is not active
    @FXML
    private final void switchContrastImpact(){
        final Double sliderValue = projectModel.getActiveProject().getContrastValue();
        if(!contrastSlider.isDisable()) {
            projectImageView.setImage(projectModel.makeContrast(150.0));
            projectModel.setProjectContrastValue(sliderValue);
            setContrastSliderDisabledStatus(true);
        }else if(projectImageView.getImage() != null){
            projectImageView.setImage(projectModel.makeContrast(projectModel.getActiveProject().getContrastValue()));
            setContrastSliderDisabledStatus(false);
        }
    }

    //delete active project on a button click
    @FXML
    private final void deleteActiveProject(){
        ProjectListModel.deleteProjectOnMouseClick(projectModel.getActiveProject());
        loadProjectList();
        loadBlankProject();
    }
}