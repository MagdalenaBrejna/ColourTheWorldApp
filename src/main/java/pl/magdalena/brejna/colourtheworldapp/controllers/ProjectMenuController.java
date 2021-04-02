package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.magdalena.brejna.colourtheworldapp.models.ImageFxModel;

public class ProjectMenuController {

    private MainProjectController mainProjectController;

    private ImageFxModel imageFxModel;

    @FXML
    private Button saveNameButton;
    @FXML
    private TextField projectNameField;

    @FXML
    private Button editButton;

    public void initialize() {
        this.imageFxModel = new ImageFxModel();
        bindings();
    }

    public void bindings() {
        this.saveNameButton.disableProperty().bind(projectNameField.textProperty().isEmpty());
        //this.projectNameField.textProperty().bindBidirectional(imageFxModel.getImageFxObjectProperty().imageProjectNameProperty());
    }

    public void setMainProjectController(MainProjectController mainController) {
        this.mainProjectController = mainController;
    }
}
