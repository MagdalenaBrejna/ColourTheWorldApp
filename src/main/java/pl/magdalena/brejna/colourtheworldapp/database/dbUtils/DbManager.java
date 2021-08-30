package pl.magdalena.brejna.colourtheworldapp.database.dbUtils;

import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.models.Project;
import java.sql.*;

public class DbManager {

    private static String CONNECTION_URL = "jdbc:mysql://localhost:3306/colourtheworlddatabase";
    private static String CONNECTION_USER = "root";
    private static String CONNECTION_PASSWORD = "Magda2001";

    private static Connection dbConnection = null;

    private static Connection connectDB() throws DatabaseException{
        try {
            dbConnection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
            return dbConnection;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Connection error");
        }
    }

    private static void disconnectDB() throws DatabaseException{
        try {
            if (dbConnection != null && !dbConnection.isClosed())
                dbConnection.close();
        } catch (SQLException sqlException){
            throw new DatabaseException("Disconnection error");
        }
    }

    public static void executeInsert(Project project, String sqlStatement){
        try {
            connectDB();
            insertProject(project, sqlStatement);
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.printStackTrace();
        }
    }

    private static void insertProject(Project project, String sqlStatement) throws DatabaseException{
        try (PreparedStatement statement = dbConnection.prepareStatement(sqlStatement)) {
            createInsertStatement(project, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Insert error");
        }
    }

    private static void createInsertStatement(Project project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
        if (project.getSourceFile() != null)
            statement.setString(2, project.getSourceFile().toString());
        else
            statement.setString(2, "");
        statement.setDouble(3, project.getDilationValue());
        statement.setDouble(4, project.getContrastValue());
    }

    public static void executeDeletion(Project project, String sqlStatement){
        try {
            connectDB();
            deleteProject(project, sqlStatement);
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.printStackTrace();
        }
    }

    private static void deleteProject(Project project, String sqlStatement) throws DatabaseException{
        try (PreparedStatement statement = dbConnection.prepareStatement(sqlStatement)) {
            createDeleteStatement(project, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Deletion error");
        }
    }

    private static void createDeleteStatement(Project project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
    }

    public static void executeProjectUpdate(Project project, String sqlStatement){
        try {
            connectDB();
            updateProject(project, sqlStatement);
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.printStackTrace();
        }
    }

    private static void updateProject(Project project, String sqlStatement) throws DatabaseException{
        try (PreparedStatement statement = dbConnection.prepareStatement(sqlStatement)) {
            createUpdateStatement(project, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Update error");
        }
    }

    private static void createUpdateStatement(Project project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
        if (project.getSourceFile() != null)
            statement.setString(2, project.getSourceFile().toString());
        else
            statement.setString(2, "");
        statement.setDouble(3, project.getDilationValue());
        statement.setDouble(4, project.getContrastValue());
        statement.setString(5, project.getProjectName());
    }

}

