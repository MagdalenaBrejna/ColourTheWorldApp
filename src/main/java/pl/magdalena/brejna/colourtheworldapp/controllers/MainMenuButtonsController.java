package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;

public class MainMenuButtonsController {

    private static final String mainProjectFXML = "/fxml.files/MainProject.fxml";

    private MainLayoutController mainLayoutController;

    public void setMainLayoutController(MainLayoutController mainController) {
        this.mainLayoutController = mainController;
    }

    @FXML
    public void openNewProject(){
        mainLayoutController.setCenter(mainProjectFXML);
    }
}
