package pl.magdalena.brejna.colourtheworldapp.database.dao;

import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;

import java.util.ArrayList;

public interface Dao {
    void insertProject(Project project);
    void deleteProject(Project project);
    void updateProject(Project project);
    boolean isProject(String projectName);
    ArrayList showAllProjects() throws DatabaseException;
}
