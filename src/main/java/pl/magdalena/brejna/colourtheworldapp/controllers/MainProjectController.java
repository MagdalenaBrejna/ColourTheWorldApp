package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import pl.magdalena.brejna.colourtheworldapp.models.ImageFxProject;
import pl.magdalena.brejna.colourtheworldapp.models.ImageFxProjectModel;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import java.io.IOException;

public class MainProjectController {

    private ImageFxProjectModel imageFxProjectModel;
    private static final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtons.fxml";

    //controls connected with saving new project
    @FXML
    private Button saveNameButton;
    @FXML
    private TextField projectNameField;
    @FXML
    private ComboBox<ImageFxProject> projectChoiceComboBox;
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

    //class initialization - set bindings, set movement of splitPane
    public void initialize(){
        this.imageFxProjectModel = new ImageFxProjectModel();
        this.imageFxProjectModel.init();
        this.projectNameField.setText("newProject");
        bindings();
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});
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
        imageViewAfter.setImage(imageFxProjectModel.createPhoto());
    }

    //set uploadd photo to the imageViewBefore
    @FXML
    private void choosePhoto(){
        try {
            imageViewBefore.setImage(imageFxProjectModel.loadImage());
            openImageButton.setDisable(true);
        }catch(ImageException e){
            e.printStackTrace();
        }
    }

    //save photo stored in imageViewAfter in computer files
    @FXML
    private void savePhoto(){
       try {
            imageFxProjectModel.saveImage();
        } catch (ImageException | IOException e) {
            e.printStackTrace();
        }
    }

    //delete photo stored in imageViewBefore
    @FXML
    private void deletePhoto(){
        imageFxProjectModel.delete();
        imageViewBefore.setImage(null);
        imageViewAfter.setImage(null);
        openImageButton.setDisable(false);
    }

    //ask for confirmation and if the answer is OK close an active project
    @FXML
    private void closeProject(){
        DialogsUtils.confirmationDialog()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> App.setCenterLayout(MAIN_MENU_BUTTONS_FXML));
    }

    //create current project and save its name
    @FXML
    private void saveProject(){
        imageFxProjectModel.save(projectNameField.textProperty());
        projectNameField.setDisable(true);
        this.saveNameButton.disableProperty().unbind();
        saveNameButton.setDisable(true);
    }
}
