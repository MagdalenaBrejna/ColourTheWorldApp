package pl.magdalena.brejna.colourtheworldapp.models;

public class ProjectListModel {

    public static ProjectList list = new ProjectList();

    public static void addProject(UserProject project){
        list.addNewProject(project);
    }
}
