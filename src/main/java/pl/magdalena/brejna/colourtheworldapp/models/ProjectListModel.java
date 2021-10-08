package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.ObservableList;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import pl.magdalena.brejna.colourtheworldapp.objects.ProjectList;

import java.util.ArrayList;

public final class ProjectListModel {

    private final static ProjectList list = new ProjectList();

    public final static void addProjectToList(final Project project){
        list.addNewProject(project);
    }

    public final static ObservableList getProjectList(){
        return list.getProjectObservableList();
    }

    public final static void setProjectList(final ArrayList<Project> projectObservableList){
        list.setProjectObservableList(projectObservableList);
    }

    public final static Project getProjectFromList(final String sourceFile){
        return list.getProjectFromList(sourceFile);
    }

    public final static void deleteProjectOnMouseClick(final Project project){
        list.deleteProjectOnMouseClick(project);
    }

    public final static void deleteProject(){
        list.delete();
    }

    public final static boolean containsProject(final Project project){
        return list.containsProject(project);
    }
}
