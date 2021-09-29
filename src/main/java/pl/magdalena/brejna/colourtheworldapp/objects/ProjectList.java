package pl.magdalena.brejna.colourtheworldapp.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        if(project != null)
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
            databaseException.callErrorMessage();
        }
    }
    public boolean containsProject(Project project){
        return projectObservableList.contains(project);
    }

    public void presentProjects(){
        System.out.print("\n");
        for(Project project : projectObservableList)
            System.out.print(project.getProjectName() + " + " + project.getSourceFile() + "\n");
        System.out.print("\n");
    }

    public Project getProjectFromList(String sourceFile){
        ArrayList<Project> list = ((ArrayList<Project>) projectObservableList.stream()
                .filter(project -> (project.getSourceFile().equals(sourceFile)))
                .collect(Collectors.toList()));
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }
}
