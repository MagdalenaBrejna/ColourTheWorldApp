package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.models.Project;

public class ProjectDao implements Dao{

    public void insertProject(Project activeProject){
        String sql = "INSERT INTO projects (projectName, sourceFile, dilationValue, contrastValue) VALUES (?, ?, ?, ?)";
        DbManager.executeTableUpdate(activeProject, sql);
    }

    public void deleteProject(Project project){
        String sql = "DELETE FROM projects WHERE projectName=?";
        DbManager.executeTableUpdate(project, sql);
    }

    public void updateProject(Project project){
        String sql = "UPDATE projects SET projectName=?, sourceFile=?, dilationValue=?, contrastValue=? WHERE projectName=?";
        DbManager.executeTableUpdate(project, sql);
    }
}
