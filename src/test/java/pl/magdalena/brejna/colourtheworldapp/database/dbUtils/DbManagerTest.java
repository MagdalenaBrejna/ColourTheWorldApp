package pl.magdalena.brejna.colourtheworldapp.database.dbUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public final class DbManagerTest extends ApplicationTest{

    private final String INSERT_SQL = "INSERT INTO projects (projectName, sourceFile, dilationValue, contrastValue) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE projects SET projectName=?, sourceFile=?, dilationValue=?, contrastValue=? WHERE projectName=?";
    private final String DELETE_SQL = "DELETE FROM projects WHERE projectName=?";
    private final String QUERY_SQL = "SELECT projectName FROM projects";
    private final String SIZE_UPDATE_SQL = "UPDATE stages SET name=\"main\", isMaximized=? WHERE name=\"main\"";
    private final String SIZE_QUERY_SQL = "SELECT isMaximized FROM stages WHERE name=\"main\"";
    private final String COLUMN_NAME = "projectName";
    private final String COLUMN_IS_MAXIMIZED = "isMaximized";

    private final String PROJECT_TEST_NAME = "testProject";
    private final String PROJECT_TEST_FILE = "";
    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

    private ProjectDao dao;
    private final int MINIMIZED_VALUE = 1;
    private final int NOT_MINIMIZED_VALUE = 0;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
        dao = new ProjectDao();
    }

    @Override
    public void start (Stage stage){ }

    @After
    public void after()throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void insertTest(){
        final Project testProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        DbManager.executeTableUpdate(testProject, INSERT_SQL);
        Assertions.assertThat(dao.showAllProjects().contains(testProject));
        dao.deleteProject(testProject);
    }

    @Test
    public final void updateTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(0);
            Assertions.assertThat(ProjectListModel.containsProject(testProject));

            final String projectName = testProject.getProjectName();
            testProject.setProjectName(PROJECT_TEST_NAME);
            DbManager.executeTableUpdate(testProject, UPDATE_SQL);
            Assertions.assertThat(dao.showAllProjects().contains(testProject));

            testProject.setProjectName(projectName);
            DbManager.executeTableUpdate(testProject, UPDATE_SQL);
        }
    }

    @Test
    public final void deleteTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(0);
            Assertions.assertThat(ProjectListModel.containsProject(testProject));
            DbManager.executeTableUpdate(testProject, DELETE_SQL);

            Assertions.assertThat(!dao.showAllProjects().contains(testProject));
            DbManager.executeTableUpdate(testProject, INSERT_SQL);
        }
    }

    @Test
    public final void executeQueryTest(){
        final CachedRowSet resultSet = DbManager.executeQuery(QUERY_SQL);
        final ArrayList<Object> nameList = new ArrayList<>();
        try {
            while (resultSet.next())
                nameList.add(resultSet.getString(COLUMN_NAME));
        }catch(SQLException databaseException){
            throw new DatabaseException("Query exception");
        }
        Assertions.assertThat(nameList.size() == ProjectListModel.getProjectList().size());
    }

    @Test
    public final void executeStageSizeUpdateTest(){
        final int size = getMaximizedValue();
        if(size == NOT_MINIMIZED_VALUE)
            DbManager.executeStageSizeUpdate(MINIMIZED_VALUE, SIZE_UPDATE_SQL);
        else
            DbManager.executeStageSizeUpdate(NOT_MINIMIZED_VALUE, SIZE_UPDATE_SQL);
        int newSize = getMaximizedValue();
        Assertions.assertThat(size != newSize);
        DbManager.executeStageSizeUpdate(size, SIZE_UPDATE_SQL);
    }

    private final int getMaximizedValue() throws DatabaseException {
        final CachedRowSet resultSet = DbManager.executeQuery(SIZE_QUERY_SQL);
        int isMaximized = 0;
        try {
            while(resultSet.next())
                isMaximized = resultSet.getInt(COLUMN_IS_MAXIMIZED);
        }catch(SQLException databaseException){
            throw new DatabaseException("Query exception");
        }
        return isMaximized;
    }
}