package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public final class StageDao{

    private final String SIZE_UPDATE_SQL = "UPDATE stages SET name=\"main\", isMaximized=? WHERE name=\"main\"";
    private final String SIZE_QUERY_SQL = "SELECT isMaximized FROM stages WHERE name=\"main\"";
    private final String COLUMN_IS_MAXIMIZED = "isMaximized";
    private final String DATABASE_ERROR_MESSAGE = "Query exception";

    //update the main stage size with the given value
    public final void updateMainStageSize(final int sizeValue) throws DatabaseException{
        final String sql = SIZE_UPDATE_SQL;
        DbManager.executeStageSizeUpdate(sizeValue, sql);
    }

    //get the main stage size from the table
    public final int getMainStageSize() throws DatabaseException {
        final String sql = SIZE_QUERY_SQL;
        final CachedRowSet resultSet = DbManager.executeQuery(sql);
        return getMaximizedValue(resultSet);
    }

    //get the stage size from the result set
    private final int getMaximizedValue(final CachedRowSet resultSet) throws DatabaseException {
        int isMaximized = 0;
        try {
            while(resultSet.next())
                isMaximized = resultSet.getInt(COLUMN_IS_MAXIMIZED);
        }catch(SQLException databaseException){
            throw new DatabaseException(DATABASE_ERROR_MESSAGE);
        }
        return isMaximized;
    }
}
