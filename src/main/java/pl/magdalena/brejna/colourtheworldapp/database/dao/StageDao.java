package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.database.dbUtils.DbManager;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public final class StageDao{

    //update the main stage size with the given value
    public final void updateMainStageSize(final int sizeValue) throws DatabaseException{
        final String sql = "UPDATE stages SET name=\"main\", isMaximized=? WHERE name=\"main\"";
        DbManager.executeStageSizeUpdate(sizeValue, sql);
    }

    //get the main stage size from the table
    public final int getMainStageSize() throws DatabaseException {
        final String sql = "SELECT isMaximized FROM stages WHERE name=\"main\"";
        final CachedRowSet resultSet = DbManager.executeQuery(sql);
        return getMaximizedValue(resultSet);
    }

    //get the stage size from the result set
    private final int getMaximizedValue(final CachedRowSet resultSet) throws DatabaseException {
        int isMaximized = 0;
        try {
            while(resultSet.next())
                isMaximized = resultSet.getInt("isMaximized");
        }catch(SQLException databaseException){
            throw new DatabaseException("Query exception");
        }
        return isMaximized;
    }
}
