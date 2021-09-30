package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class ProjectDao {

    //add given project to the projects table
    public final void insertProject(final Project project){
        final String sql = "INSERT INTO projects (projectName, sourceFile, dilationValue, contrastValue) VALUES (?, ?, ?, ?)";
        DbManager.executeTableUpdate(project, sql);
    }

    //delete given project from the table
    public final void deleteProject(final Project project){
        final String sql = "DELETE FROM projects WHERE projectName=?";
        DbManager.executeTableUpdate(project, sql);
    }

    //update given project in the table
    public final void updateProject(final Project project){
        final String sql = "UPDATE projects SET projectName=?, sourceFile=?, dilationValue=?, contrastValue=? WHERE projectName=?";
        DbManager.executeTableUpdate(project, sql);
    }

    //get all projects from the table
    public final ArrayList showAllProjects() throws DatabaseException{
        final String sql = "SELECT projectName, sourceFile, dilationValue, contrastValue FROM projects";
        final CachedRowSet resultSet = DbManager.executeQuery(sql);
        return getAllProjects(resultSet);
    }

    //get projects from the query result
    private final ArrayList getAllProjects(final CachedRowSet resultSet) throws DatabaseException {
        final ArrayList<Object> projectList = new ArrayList<>();
        try {
            while (resultSet.next())
                projectList.add(getProject(resultSet));
        }catch(SQLException databaseException){
            throw new DatabaseException("Query exception");
        }
        return projectList;
    }

    //create a project from the row of the query result set
    private final Project getProject(final CachedRowSet resultSet) throws SQLException {
        final Project project = new Project();
        project.setProjectName(resultSet.getString("projectName"));
        project.setSourceFile(resultSet.getString("sourceFile"));
        project.setDilationValue(resultSet.getDouble("dilationValue"));
        project.setContrastValue(resultSet.getDouble("contrastValue"));
        return project;
    }

    //check whether the given project is in the table or not
    public final boolean isProject(final String projectName){
        ArrayList<Project> projectList = showAllProjects();
        projectList = (ArrayList)projectList.stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .collect(Collectors.toList());
        if(projectList.isEmpty())
            return false;
        return true;
    }
}
