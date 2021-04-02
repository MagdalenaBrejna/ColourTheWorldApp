package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageException;
import pl.magdalena.brejna.colourtheworldapp.models.ImageFxModel;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import java.io.IOException;

public class ImageViewController {

    private static final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtons.fxml";

    private MainProjectController mainProjectController;

    private ImageFxModel imageFxModel;

    @FXML
    private ImageView imageViewBefore;
    @FXML
    private ImageView imageViewAfter;

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

    public void initialize(){
        this.imageFxModel = new ImageFxModel();
        this.imageFxModel.init();
        bindings();
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});
    }

    public void bindings(){
        this.createProjectButton.disableProperty().bind(this.imageViewBefore.imageProperty().isNull());
        this.zoomButton.disableProperty().bind(this.imageViewAfter.imageProperty().isNull());
        this.saveAsButton.disableProperty().bind(this.imageViewAfter.imageProperty().isNull());
        this.deleteButton.disableProperty().bind(this.imageViewBefore.imageProperty().isNull());
        this.zoomButton.disableProperty().bind(this.imageViewAfter.imageProperty().isNull());
        //this.imageViewBefore.imageProperty().bindBidirectional(imageFxModel.getImageFxObjectProperty().projectImageProperty());
    }

    public void setMainProjectController(MainProjectController mainController) {
        this.mainProjectController = mainController;
    }

    @FXML
    private void choosePhoto(){
       try {
           imageViewBefore.setImage(imageFxModel.loadImage());
       }catch(ImageException e){
           e.printStackTrace();
       }
    }

    @FXML
    private void savePhoto(){
        try {
            imageFxModel.saveImage(imageViewAfter.getImage());
        } catch (ImageException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deletePhoto(){
        imageViewBefore.setImage(null);
    }

    @FXML
    private void closeProject(){
        DialogsUtils.confirmationDialog()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> App.setCenterLayout(MAIN_MENU_BUTTONS_FXML));
    }
}
