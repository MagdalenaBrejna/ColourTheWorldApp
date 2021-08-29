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
            createStatement(project, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Insert error");
        }
    }

    private static void createStatement(Project project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
        if (project.getSourceFile() != null)
            statement.setString(2, project.getSourceFile().toString());
        else
            statement.setString(2, "");
        statement.setDouble(3, project.getDilationValue());
        statement.setDouble(4, project.getContrastValue());
    }
}

