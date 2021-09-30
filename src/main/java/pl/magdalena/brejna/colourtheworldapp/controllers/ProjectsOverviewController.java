package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectsOverviewModel;

public final class ProjectsOverviewController {

    @FXML
    private TilePane overviewPane;

    //open projects' overview stage
    public final void initialize(){
        ProjectsOverviewModel.presentOverview(overviewPane);
    }

    //close projects' overview stage
    @FXML
    private final void closeOverview(){
        ProjectsOverviewModel.closeOverview();
    }
}
