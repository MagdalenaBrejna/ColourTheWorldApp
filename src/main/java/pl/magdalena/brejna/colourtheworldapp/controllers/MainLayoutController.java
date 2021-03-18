package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;

public class MainLayoutController {

    @FXML
    private MainMenuButtonsController mainMenuButtonsController;

    // @FXML
    //private UserChoiceController userChoiceController;

    @FXML
    private void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
       // userChoiceController.setMainLayoutController(this);
    }
}
