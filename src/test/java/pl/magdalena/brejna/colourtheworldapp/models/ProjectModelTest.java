package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import java.util.concurrent.TimeoutException;

public final class ProjectModelTest extends ApplicationTest {

    private final String CORRECT_JPG_FILE = "file:/C:/Users/magda/Pictures/iceCream.jpg";
    private final String WRONG_JPG_FILE = "file:/C:/Users/magda/Pictures/Cream.jpg";

    private final String MAIN_BORDER_PANE_ID = "mainProjectPane";
    private final String MAIN_BUTTONS_BORDER_PANE_ID = "mainMenuButtonsPane";

    private final String PROJECT_TEST_NAME = "testProject";
    private final String PROJECT_TEST_FILE = "";
    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

    private final double DILATION_VALUE = 1.0;
    private final double CONTRAST_VALUE = 200.0;

    private ProjectModel projectModel;
    private ProjectDao activeProjectDao;
    private Project testClassProject;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
        projectModel = new ProjectModel();
        testClassProject = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);

        Platform.runLater(() -> {
            projectModel.init();
            activeProjectDao = new ProjectDao();
        });
    }

    @Override
    public void start(Stage stage){ }

    @After
    public void after() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void updateProjectListTest(){
        Platform.runLater(() -> {
            projectModel.updateProjectList();
            Assertions.assertThat(ProjectListModel.getProjectList().equals(activeProjectDao.showAllProjects()));
        });
    }

    @Test
    public final void getActiveProjectTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
            Assertions.assertThat(projectModel.getActiveProject() == null);
            projectModel.loadProject(project);
            Assertions.assertThat(projectModel.getActiveProject() != null);
        });
    }

    @Test
    public final void getPhotoImageTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(projectModel.getPhotoImage() == null);
            projectModel.getActiveProject().setSourceFile(CORRECT_JPG_FILE);
            Assertions.assertThat(projectModel.getPhotoImage() != null);
        });
    }

    @Test
    public final void getProjectImageTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(projectModel.getProjectImage() == null);
            projectModel.getActiveProject().setSourceFile(CORRECT_JPG_FILE);
            Assertions.assertThat(projectModel.getProjectImage() != null);
        });
    }

    @Test
    public final void loadImageTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(projectModel.getActiveProject().getSourceFile() == null);
            projectModel.loadImage();
            Assertions.assertThat(projectModel.getActiveProject().getSourceFile() != null);
        });
    }

    @Test
    public final void openFileTest(){
        final Image testImage = projectModel.openFile(CORRECT_JPG_FILE);
        Assertions.assertThat(!testImage.equals(null));
    }

    @Test (expected = ImageLoadingException.class)
    public final void openWrongFileTest(){
        final Image testImage = projectModel.openFile(WRONG_JPG_FILE);
        Assertions.assertThat(testImage.equals(null));
    }

    @Test
    public final void loadProjectTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
            Assertions.assertThat(projectModel.getActiveProject().getProjectName() == null);
            projectModel.loadProject(project);
            Assertions.assertThat(projectModel.getActiveProject().getProjectName() != null);
        });
    }

    @Test
    public final void createColouringBookTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), CORRECT_JPG_FILE, DEFAULT_DILATION_VALUE,  DEFAULT_CONTRAST_VALUE);
            projectModel.loadProject(project);
            final Image image = projectModel.createColouringBook();
            Assertions.assertThat(image != null);
        });
    }

    @Test
    public final void saveActiveProjectTest(){
        Platform.runLater(() -> {
            projectModel.saveActiveProject(new SimpleStringProperty(PROJECT_TEST_NAME));
            final Project project = projectModel.getActiveProject();
            Assertions.assertThat(ProjectListModel.containsProject(project));

            activeProjectDao.deleteProject(project);
        });
    }

    @Test
    public void saveColouringBookTest(){
        Platform.runLater(() -> {
            projectModel.saveColouringBook();
        });
    }

    @Test
    public void closeProjectTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(App.getMainPane().getCenter().getId().equals(MAIN_BORDER_PANE_ID));
            projectModel.closeProject();
            Assertions.assertThat(App.getMainPane().getCenter().getId().equals(MAIN_BUTTONS_BORDER_PANE_ID));
        });
    }

    @Test
    public void isSavedTest(){
        Assertions.assertThat(!projectModel.isSaved());
        saveActiveProjectTest();
        Assertions.assertThat(projectModel.isSaved());
    }

    @Test
    public final void deleteImageTest() {
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), CORRECT_JPG_FILE, DEFAULT_DILATION_VALUE,  DEFAULT_CONTRAST_VALUE);
            projectModel.loadProject(project);
            projectModel.deleteImage();
            Assertions.assertThat(project.getSourceFile().equals(""));
        });
    }

    @Test
    public final void setDilationValueTest(){
        Platform.runLater(() -> {
            final double dilationValue = projectModel.getActiveProject().getDilationValue();
            projectModel.setProjectDilationValue(DILATION_VALUE);
            Assertions.assertThat(dilationValue != projectModel.getActiveProject().getDilationValue());
        });
    }

    @Test
    public void dilateTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE,  DEFAULT_CONTRAST_VALUE);
            projectModel.loadProject(project);
            projectModel.getActiveProject().setSourceFile(CORRECT_JPG_FILE);
            final Image image = EdgeDetection.detectEdges(projectModel.getActiveProject());
            projectModel.dilate(DILATION_VALUE);
            Assertions.assertThat(image.equals(EdgeDetection.detectEdges(projectModel.getActiveProject())));
        });
    }

    @Test
    public final void setContrastValueTest(){
        Platform.runLater(() -> {
            final double contrastValue = projectModel.getActiveProject().getContrastValue();
            projectModel.setProjectContrastValue(CONTRAST_VALUE);
            Assertions.assertThat(contrastValue != projectModel.getActiveProject().getContrastValue());
        });
    }

    @Test
    public final void makeContrastTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE,  DEFAULT_CONTRAST_VALUE);
            projectModel.loadProject(project);
            projectModel.getActiveProject().setSourceFile(CORRECT_JPG_FILE);
            final Image image = EdgeDetection.detectEdges(projectModel.getActiveProject());
            projectModel.makeContrast(CONTRAST_VALUE);
            Assertions.assertThat(image.equals(EdgeDetection.detectEdges(projectModel.getActiveProject())));
        });
    }

    @Test
    public final void showFullViewTest(){
        Platform.runLater(() -> {
            final Project project = new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
            projectModel.loadProject(project);
            projectModel.getActiveProject().setSourceFile(CORRECT_JPG_FILE);
            projectModel.showFullView();
        });
    }
}