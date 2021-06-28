package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class ProjectList {

    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();

    public void addNewProject(Project project){
        projectObservableList.add(project);
    }

    public ObservableList getProjectObservableList(){
        return projectObservableList;
    }

    public void deleteProjectOnMouseClick(Project project){
        showDeleteConfirmationDialog(project);
    }

    private void showDeleteConfirmationDialog(Project project){
        DialogsUtils.showConfirmationDialog("delete.title", "delete.text")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> deleteProject(project));
    }

    private void deleteProject(Project project){
        projectObservableList.remove(project);
    }

    public boolean containsProject(Project project){
        return projectObservableList.contains(project);
    }

}
