package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.ObservableList;
import java.util.ArrayList;

public class ProjectListModel {

    private static ProjectList list = new ProjectList();

    public static void addProjectToList(Project project){
        list.addNewProject(project);
    }

    public static ObservableList getProjectList(){
        return list.getProjectObservableList();
    }

    public static void setProjectList(ArrayList<Project> projectObservableList){
        list.setProjectObservableList(projectObservableList);
    }

    public static void deleteProjectOnRightClick(Project project){
        list.deleteProjectOnMouseClick(project);
    }

    public static boolean containsProject(Project project){
        return list.containsProject(project);
    }
}
