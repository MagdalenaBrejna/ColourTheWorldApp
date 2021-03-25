package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class MainLayoutController {

   @FXML
   private MainMenuButtonsController mainMenuButtonsController;

    @FXML
    private void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
    }

    @FXML
    public void closeApplication() {
        DialogsUtils.confirmationDialog()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }
}