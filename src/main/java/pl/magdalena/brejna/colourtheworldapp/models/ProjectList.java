package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectList {

    ObservableList<UserProject> projectObservableList = FXCollections.observableArrayList();

    public void addNewProject(UserProject project){
        projectObservableList.add(project);
    }

    public ObservableList getProjectObservableList(){
        return projectObservableList;
    }

}
