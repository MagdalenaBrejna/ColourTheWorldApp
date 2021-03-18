package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class MainLayoutController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private MainMenuButtonsController mainMenuButtonsController;

    public void setCenter(String centerFxmlPath){
        mainPane.setCenter(FxmlUtils.fxmlLoader(centerFxmlPath));
    }

    @FXML
    private void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
    }
}
