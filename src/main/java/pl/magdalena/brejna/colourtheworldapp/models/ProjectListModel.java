package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.ObservableList;

public class ProjectListModel {

    private static ProjectList list = new ProjectList();

    public static void addProject(UserProject project){
        list.addNewProject(project);
    }

    public static ObservableList getList(){
        return list.getProjectObservableList();
    }

    public static void deleteProjectOnRightClick(UserProject project){
        list.deleteProjectOnMouseEvent(project);
    }

    public static boolean containsProject(UserProject project){
        return list.containsProject(project);
    }
}
