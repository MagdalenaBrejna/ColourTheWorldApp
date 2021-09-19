package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public ArrayList showAllProjects() throws DatabaseException{
        String sql = "SELECT projectName, sourceFile, dilationValue, contrastValue FROM projects";
        CachedRowSet resultSet = DbManager.executeQuery(sql);
        return getAllProjects(resultSet);
    }

    private ArrayList getAllProjects(CachedRowSet resultSet) throws DatabaseException {
        ArrayList<Project> projectList = new ArrayList<>();
        try {
            while (resultSet.next())
                projectList.add(getProject(resultSet));
        }catch(SQLException databaseException){
            throw new DatabaseException("Query exception");
        }
        return  projectList;
    }

    private Project getProject(CachedRowSet resultSet) throws SQLException {
        Project project = new Project();
        project.setProjectName(resultSet.getString("projectName"));
        project.setSourceFile(resultSet.getString("sourceFile"));
        project.setDilationValue(resultSet.getDouble("dilationValue"));
        project.setContrastValue(resultSet.getDouble("contrastValue"));
        return project;
    }

    public boolean isProject(String projectName){
        ArrayList<Project> projectList = showAllProjects();
        projectList = (ArrayList)projectList.stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .collect(Collectors.toList());
        if(projectList.isEmpty())
            return false;
        return true;
    }
}
