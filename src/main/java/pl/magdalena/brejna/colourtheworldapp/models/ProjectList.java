package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

public class ProjectList {

    private ObservableList<UserProject> projectObservableList = FXCollections.observableArrayList();

    public void addNewProject(UserProject project){
        projectObservableList.add(project);
    }

    public ObservableList getProjectObservableList(){
        return projectObservableList;
    }

    public void deleteProjectOnMouseEvent(UserProject project){
        showDeleteConfirmationDialog(project);
    }
    private void showDeleteConfirmationDialog(UserProject project){
        DialogsUtils.showDeleteConfirmationDialog()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> deleteProject(project));
    }
    private void deleteProject(UserProject project){
        projectObservableList.remove(project);
    }
    public boolean containsProject(UserProject project){
        return projectObservableList.contains(project);
    }

}
