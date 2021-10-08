package pl.magdalena.brejna.colourtheworldapp.database.dbUtils;

import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public final class DbManager {

    private final static String CONNECTION_ERROR_MESSAGE = "Connection error";
    private final static String DATABASE_ERROR_MESSAGE = "Database error";

    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/colourtheworlddatabase";
    private final static String CONNECTION_USER = "root";
    private final static String CONNECTION_PASSWORD = "Magda2001";

    private static Connection dbConnection = null;

    //connect application with the database
    private final static Connection connectDB() throws DatabaseException{
        try {
            dbConnection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
            return dbConnection;
        } catch (SQLException sqlException) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE);
        }
    }

    //disconnect application from the database
    private final static void disconnectDB() throws DatabaseException{
        try {
            if (dbConnection != null && !dbConnection.isClosed())
                dbConnection.close();
        } catch (SQLException sqlException){
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE);
        }
    }

    //execute table update
    public final static void executeTableUpdate(final Project project, final String sqlStatement){
        try {
            connectDB();
            executeDatabaseAction(project, sqlStatement);
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.callErrorMessage();
        }
    }

    //create sql statement and execute it
    private final static void executeDatabaseAction(final Project project, final String sqlStatement) throws DatabaseException{
        try (PreparedStatement statement = dbConnection.prepareStatement(sqlStatement)) {
            executeRequestedAction(project, sqlStatement, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_ERROR_MESSAGE);
        }
    }

    //call proper method creating sql statement
    private final static void executeRequestedAction(final Project project, final String sqlStatement, final PreparedStatement statement) throws SQLException {
        switch(sqlStatement.charAt(0)){
            case 'I':
                createInsertStatement(project, statement);
                break;
            case 'U':
                createUpdateStatement(project, statement);
                break;
            case 'D':
                createDeleteStatement(project, statement);
                break;
        }
    }

    //build insert statement
    private final static void createInsertStatement(final Project project, final PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
        statement.setString(2, project.getSourceFile());
        statement.setDouble(3, project.getDilationValue());
        statement.setDouble(4, project.getContrastValue());
    }

    //build update statement
    private final static void createUpdateStatement(final Project project, final PreparedStatement statement) throws SQLException{
        createInsertStatement(project, statement);
        statement.setString(5, project.getProjectName());
    }

    //build delete statement
    private final static void createDeleteStatement(final Project project, final PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getProjectName());
    }

    //execute query
    public final static CachedRowSet executeQuery(final String query){
        CachedRowSet cachedRowSet = null;
        try {
            connectDB();
            cachedRowSet = callQuery(query);
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.callErrorMessage();
        }
        return cachedRowSet;
    }

    //create a query statement and call the method that creates a query result set
    private final static CachedRowSet callQuery(final String query) throws DatabaseException{
        try (Statement statement = dbConnection.createStatement()) {
            return createCachedRowSet(statement, query);
        } catch (SQLException databaseException) {
            throw new DatabaseException(DATABASE_ERROR_MESSAGE);
        }
    }

    //create a query result set
    private final static CachedRowSet createCachedRowSet(final Statement statement, final String query) throws SQLException{
        final ResultSet resultSet = statement.executeQuery(query);
        final RowSetFactory factory = RowSetProvider.newFactory();
        final CachedRowSet cachedRowSet = factory.createCachedRowSet();
        cachedRowSet.populate(resultSet);
        return cachedRowSet;
    }

    //update a stage size in the table stages
    public static void executeStageSizeUpdate(final int sizeValue, final String sqlStatement){
        try {
            connectDB();
            try (PreparedStatement statement = dbConnection.prepareStatement(sqlStatement)) {
                statement.setInt(1, sizeValue);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(DATABASE_ERROR_MESSAGE);
            }
            disconnectDB();
        }catch(DatabaseException databaseException){
            databaseException.callErrorMessage();
        }
    }
}

