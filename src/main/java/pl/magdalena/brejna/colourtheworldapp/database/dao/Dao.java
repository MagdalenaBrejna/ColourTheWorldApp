package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.models.Project;

import java.util.ArrayList;

public interface Dao {
    void insertProject(Project project);
    void deleteProject(Project project);
    void updateProject(Project project);
    ArrayList showAllProjects() throws DatabaseException;
}
