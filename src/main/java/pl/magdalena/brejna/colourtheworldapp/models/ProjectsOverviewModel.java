package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.scene.layout.TilePane;
import pl.magdalena.brejna.colourtheworldapp.objects.ProjectsOverview;

public class ProjectsOverviewModel {

    private static ProjectsOverview projectsOverview = new ProjectsOverview();

    public static void closeOverview(){
        projectsOverview.closeOverview();
    }

    public static void presentOverview(TilePane tile){
        projectsOverview.presentOverview(tile);
    }
}
