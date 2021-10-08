package pl.magdalena.brejna.colourtheworldapp.database.dao;

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
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public final class ProjectDaoTest extends ApplicationTest {

    private ProjectDao dao;
    private final String TEMPORARY_NAME = "temporaryName";
    private final String PROJECT_TEST_NAME = "testProject";
    private final String PROJECT_TEST_FILE = "";
    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

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
    public final void insertProjectTest(){
        final Project testProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        Assertions.assertThat(!dao.showAllProjects().contains(testProject));
        dao.insertProject(testProject);
        Assertions.assertThat(dao.showAllProjects().contains(testProject));
        dao.deleteProject(testProject);
    }

    @Test
    public final void deleteProjectTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(0);
            Assertions.assertThat(ProjectListModel.containsProject(testProject));
            dao.deleteProject(testProject);
            Assertions.assertThat(!dao.showAllProjects().contains(testProject));
            dao.insertProject(testProject);
        }
    }

    @Test
    public final void updateProjectTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(0);
            Assertions.assertThat(ProjectListModel.containsProject(testProject));

            final String projectName = testProject.getProjectName();
            testProject.setProjectName(TEMPORARY_NAME);
            Assertions.assertThat(!dao.showAllProjects().contains(testProject));
            dao.updateProject(testProject);
            Assertions.assertThat(dao.showAllProjects().contains(testProject));

            testProject.setProjectName(projectName);
            dao.updateProject(testProject);
        }
    }

    @Test
    public final void showAllProjectsTest(){
        final ArrayList<Project> projectList = dao.showAllProjects();
        Assertions.assertThat(projectList.size() == ProjectListModel.getProjectList().size());
    }

    @Test
    public final void isProjectTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(0);
            Assertions.assertThat(dao.isProject(testProject.getProjectName()));
        }
    }

    @Test
    public final void isNotProjectTest(){
        final Project testProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        Assertions.assertThat(!dao.isProject(testProject.getProjectName()));
    }
}