package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class ProjectDao {

    private final String INSERT_SQL = "INSERT INTO projects (projectName, sourceFile, dilationValue, contrastValue) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE projects SET projectName=?, sourceFile=?, dilationValue=?, contrastValue=? WHERE projectName=?";
    private final String DELETE_SQL = "DELETE FROM projects WHERE projectName=?";
    private final String QUERY_SQL = "SELECT projectName, sourceFile, dilationValue, contrastValue FROM projects";

    private final String COLUMN_NAME = "projectName";
    private final String COLUMN_SOURCE_FILE = "sourceFile";
    private final String COLUMN_DILATION_VALUE = "dilationValue";
    private final String COLUMN_CONTRAST_VALUE = "contrastValue";

    private final String DATABASE_ERROR_MESSAGE = "Query exception";

    //add given project to the projects table
    public final void insertProject(final Project project){
        DbManager.executeTableUpdate(project, INSERT_SQL);
    }

    //delete given project from the table
    public final void deleteProject(final Project project){
        DbManager.executeTableUpdate(project, DELETE_SQL);
    }

    //update given project in the table
    public final void updateProject(final Project project){
        DbManager.executeTableUpdate(project, UPDATE_SQL);
    }

    //get all projects from the table
    public final ArrayList showAllProjects() throws DatabaseException{
        final CachedRowSet resultSet = DbManager.executeQuery(QUERY_SQL);
        return getAllProjects(resultSet);
    }

    //get projects from the query result
    private final ArrayList getAllProjects(final CachedRowSet resultSet) throws DatabaseException {
        final ArrayList<Object> projectList = new ArrayList<>();
        try {
            while (resultSet.next())
                projectList.add(getProject(resultSet));
        }catch(SQLException databaseException){
            throw new DatabaseException(DATABASE_ERROR_MESSAGE);
        }
        return projectList;
    }

    //create a project from the row of the query result set
    private final Project getProject(final CachedRowSet resultSet) throws SQLException {
        final Project project = new Project();
        project.setProjectName(resultSet.getString(COLUMN_NAME));
        project.setSourceFile(resultSet.getString(COLUMN_SOURCE_FILE));
        project.setDilationValue(resultSet.getDouble(COLUMN_DILATION_VALUE));
        project.setContrastValue(resultSet.getDouble(COLUMN_CONTRAST_VALUE));
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
