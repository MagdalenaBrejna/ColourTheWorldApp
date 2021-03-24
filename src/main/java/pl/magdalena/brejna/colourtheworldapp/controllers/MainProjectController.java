package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;

public class MainProjectController {

    @FXML
    private ImageViewController imageViewController;

    @FXML
    private ProjectMenuController projectMenuController;

   // @FXML
   // private SplitPane splitPane;

    @FXML
    private void initialize() {
        imageViewController.setMainProjectController(this);
        projectMenuController.setMainProjectController(this);
        //splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {});
    }
}
