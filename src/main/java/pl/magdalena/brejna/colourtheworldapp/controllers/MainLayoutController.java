package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
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
    public void closeApplication() {
        DialogsUtils.confirmationDialog()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }

    @FXML
    private void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
    }
}
