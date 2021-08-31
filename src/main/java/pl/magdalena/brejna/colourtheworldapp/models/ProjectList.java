package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

import java.util.ArrayList;

public class ProjectList {

    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();
    private ProjectDao projectDao = new ProjectDao();

    public void addNewProject(Project project){
        projectObservableList.add(project);
    }

    public ObservableList getProjectObservableList(){
        return projectObservableList;
    }

    public void setProjectObservableList(ArrayList<Project> projectList) {
        this.projectObservableList = FXCollections.observableArrayList();
        this.projectObservableList.addAll(projectList);
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
        try {
            projectDao.deleteProject(project);
            setProjectObservableList(projectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.printStackTrace();
        }
    }

    public boolean containsProject(Project project){
        return projectObservableList.contains(project);
    }
}
