package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.ObservableList;

public class ProjectListModel {

    public static ProjectList list = new ProjectList();

    public static void addProject(UserProject project){
        list.addNewProject(project);
    }

    public static ObservableList getList(){
        return list.getProjectObservableList();
    }
}
