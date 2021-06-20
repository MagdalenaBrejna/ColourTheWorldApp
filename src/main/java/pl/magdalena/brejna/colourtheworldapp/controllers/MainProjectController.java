package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import pl.magdalena.brejna.colourtheworldapp.models.UserProject;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectModel;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import java.io.IOException;

public class MainProjectController {

    private ProjectModel projectModel;
    private static final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtons.fxml";

    //controls connected with saving new project
    @FXML
    private Button saveNameButton;
    @FXML
    private TextField projectNameField;
    @FXML
    private ComboBox<UserProject> projectChoiceComboBox;
    @FXML
    private Button editButton;

    //controls containing photos
    @FXML
    private ImageView imageViewBefore;
    @FXML
    private ImageView imageViewAfter;

    //controls connected with processing photo
    @FXML
    private Button openImageButton;
    @FXML
    private Button createProjectButton;
    @FXML
    private Button zoomButton;
    @FXML
    private Button saveAsButton;
    @FXML
    private Button closeProjectButton;
    @FXML
    private Button deleteButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Slider slider;
    @FXML
    private Slider contrastSlider;

    //class initialization - set bindings, set movement of splitPane
    public void initialize(){
        this.projectModel = new ProjectModel();
        this.projectModel.init();
        this.projectNameField.setText("newProject");

        bindings();

        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
           imageViewAfter.setImage(projectModel.dilate((Double) newValue));
        });

        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageViewAfter.setImage(projectModel.makeContrast((Double) newValue));
        });
    }

    //set bindings - make some controls disabled in special conditions
    public void bindings(){
        this.createProjectButton.disableProperty().bind(this.imageViewBefore.imageProperty().isNull());
        this.zoomButton.disableProperty().bind(this.imageViewAfter.imageProperty().isNull());
        this.saveAsButton.disableProperty().bind(this.imageViewAfter.imageProperty().isNull());
        this.deleteButton.disableProperty().bind(this.imageViewBefore.imageProperty().isNull());
    }

    //create colouring based on uploaded photo
    @FXML
    private void createNewPhoto(){
        imageViewAfter.setImage(projectModel.createPhoto());
    }

    //set uploadd photo to the imageViewBefore
    @FXML
    private void choosePhoto(){
        try {
            imageViewBefore.setImage(projectModel.loadImage());
            openImageButton.setDisable(true);
        }catch(ImageException e){
            e.printStackTrace();
        }
    }

    //save photo stored in imageViewAfter in computer files
    @FXML
    private void savePhoto(){
       try {
            projectModel.saveImage();
        } catch (ImageException | IOException e) {
            e.printStackTrace();
        }
    }

    //delete photo stored in imageViewBefore
    @FXML
    private void deletePhoto(){
        projectModel.delete();
        imageViewBefore.setImage(null);
        imageViewAfter.setImage(null);
        openImageButton.setDisable(false);
    }

    //ask for confirmation if the project is unsaved and if the answer is OK close an active project
    @FXML
    private void closeProject(){
        if(!projectModel.isSaved()) {
            DialogsUtils.confirmationDialog()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> App.setCenterLayout(MAIN_MENU_BUTTONS_FXML));
        }else
            App.setCenterLayout(MAIN_MENU_BUTTONS_FXML);
    }

    //create current project and save its name
    @FXML
    private void saveProject(){
        projectModel.save(projectNameField.textProperty());
        projectNameField.setDisable(true);
        this.saveNameButton.disableProperty().unbind();
        saveNameButton.setDisable(true);
    }

    //open new Window with readyImage
    @FXML
    private void openZoom(){
        projectModel.showZoom();
    }
}
