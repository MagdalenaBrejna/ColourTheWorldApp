package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectsOverviewModel;

public class ProjectsOverviewController {

    @FXML
    TilePane overviewPane;

    public void initialize(){
        ProjectsOverviewModel.presentOverview(overviewPane);
    }

    @FXML
    private void closeOverview(){
        ProjectsOverviewModel.closeOverview();
    }
}
