package pl.magdalena.brejna.colourtheworldapp.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class ProjectList {

    private final String DELETE_TITLE = "delete.title";
    private final String DELETE_TEXT = "delete.text";

    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();
    private final ProjectDao projectDao = new ProjectDao();

    public ObservableList getProjectObservableList(){
        return projectObservableList;
    }

    //add the given project to the list
    public final void addNewProject(final Project project){
        projectObservableList.add(project);
    }

    //update list with projects' list stored in the database
    public final void setProjectObservableList(final ArrayList<Project> projectList) {
        this.projectObservableList = FXCollections.observableArrayList();
        this.projectObservableList.addAll(projectList);
    }

    //ask for delete confirmation. Call the method that deletes the project
    public final void deleteProjectOnMouseClick(final Project project){
        if(project != null)
            showDeleteConfirmationDialog(project);
    }

    //show confirmation dialog
    private final void showDeleteConfirmationDialog(final Project project){
        DialogsUtils.showConfirmationDialog(DELETE_TITLE, DELETE_TEXT)
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> deleteProject(project));
    }

    //delete project from the table and update the list
    public final void deleteProject(final Project project){
        try {
            projectDao.deleteProject(project);
            setProjectObservableList(projectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    //delete project from the list
    public final void delete(){
        try {
            setProjectObservableList(projectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    //return whether the list contains the project or not
    public final boolean containsProject(final Project project){
        return projectObservableList.contains(project);
    }

    //get a project that stores the given source file
    public final Project getProjectFromList(final String sourceFile){
        ArrayList<Project> list = ((ArrayList<Project>) projectObservableList.stream()
                .filter(project -> (project.getSourceFile().equals(sourceFile)))
                .collect(Collectors.toList()));
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }
}
