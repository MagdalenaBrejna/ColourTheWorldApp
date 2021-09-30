package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.scene.layout.TilePane;
import pl.magdalena.brejna.colourtheworldapp.objects.ProjectsOverview;

public final class ProjectsOverviewModel {

    private final static ProjectsOverview projectsOverview = new ProjectsOverview();

    //close overview stage
    public final static void closeOverview(){
        projectsOverview.closeOverview();
    }

    //show overview stage
    public final static void presentOverview(final TilePane tile){
        projectsOverview.presentOverview(tile);
    }
}
