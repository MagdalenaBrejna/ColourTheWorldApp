package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.models.Project;
import java.sql.SQLException;

public interface Dao {
    void insertProject(Project project) throws SQLException;
}
