package pl.magdalena.brejna.colourtheworldapp.objects;

import javafx.application.Platform;
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
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;
import java.util.concurrent.TimeoutException;

public final class ProjectListTest extends ApplicationTest {

    private final String PROJECT_TEST_NAME = "testProject";
    private final String PROJECT_TEST_FILE = "";
    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

    private final int ZERO = 0;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
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
    public final void addProjectToListTest(){
       final Project testProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
       ProjectListModel.addProjectToList(testProject);
       Assertions.assertThat(ProjectListModel.containsProject(testProject));
       ProjectListModel.deleteProject();
    }

    @Test
    public final void setProjectListTest(){
        final ProjectDao dao = new ProjectDao();
        ProjectListModel.setProjectList(dao.showAllProjects());
        Assertions.assertThat(ProjectListModel.getProjectList().equals(dao.showAllProjects()));
    }

    @Test
    public final void deleteProjectFromListTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            Platform.runLater(() ->{
                final Project testProject = (Project) ProjectListModel.getProjectList().get(ZERO);
                Assertions.assertThat(ProjectListModel.containsProject(testProject));
                ProjectListModel.deleteProjectOnMouseClick(testProject);
                Assertions.assertThat(!ProjectListModel.containsProject(testProject));

                if(!ProjectListModel.containsProject(testProject)) {
                    final ProjectDao dao = new ProjectDao();
                    dao.insertProject(testProject);
                    ProjectListModel.addProjectToList(testProject);
                }
            });
        }
    }

    @Test
    public final void containsTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(ZERO);
            Assertions.assertThat(ProjectListModel.containsProject(testProject));
        }
    }

    @Test
    public final void notContainsTest(){
        final Project testProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        Assertions.assertThat(!ProjectListModel.containsProject(testProject));
    }

    @Test
    public final void getProjectFromListTest(){
        if(!ProjectListModel.getProjectList().isEmpty()) {
            final Project testProject = (Project) ProjectListModel.getProjectList().get(ZERO);
            final Project expectedProject = ProjectListModel.getProjectFromList(testProject.getSourceFile());
            Assertions.assertThat(testProject.equals(expectedProject));
        }
    }
}