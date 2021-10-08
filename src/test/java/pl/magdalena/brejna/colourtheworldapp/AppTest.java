package pl.magdalena.brejna.colourtheworldapp;

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
import pl.magdalena.brejna.colourtheworldapp.database.dao.StageDao;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import java.util.concurrent.TimeoutException;

public final class AppTest extends ApplicationTest {

    private final String FXML_FILE = "/fxml.files/MainProjectLayout.fxml";
    private final String MAIN_PROJECT_BORDER_PANE_ID = "mainProjectBorderPane";

    private final String PROJECT_TEST_NAME = "testProject";
    private final String PROJECT_TEST_FILE = "";
    private final double DEFAULT_DILATION_VALUE = 0.0;
    private final double DEFAULT_CONTRAST_VALUE = 150.0;

    private final int MAXIMIZED_VALUE = 1;
    private final int NOT_MAXIMIZED_VALUE = 0;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start (Stage stage) { }

    @After
    public void after()throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void updateStageSizeTest(){
        final StageDao dao = new StageDao();
        final int size = dao.getMainStageSize();
        if(size == NOT_MAXIMIZED_VALUE)
            App.setMainStageSize(MAXIMIZED_VALUE);
        else
            App.setMainStageSize(NOT_MAXIMIZED_VALUE);
        Assertions.assertThat(size != dao.getMainStageSize());
    }

    @Test
    public final void storedProjectEqualsNullTest(){
        final Project storedProject = App.getStoredProject();
        Assertions.assertThat(storedProject == null);
    }

    @Test
    public final void storedProjectNotEqualsNullTest(){
        App.setStoredProject(new Project(new SimpleStringProperty(PROJECT_TEST_NAME), PROJECT_TEST_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE));
        final Project storedProject = App.getStoredProject();
        Assertions.assertThat(storedProject != null);
    }

    @Test
    public final void alwaysOnTopTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(!Main.getPrimaryStage().isAlwaysOnTop());
            App.setAppOnTop();
            Assertions.assertThat(Main.getPrimaryStage().isAlwaysOnTop());
        });
    }

    @Test
    public final void switchStageSizeTest(){
        Platform.runLater(() -> {
            Main.getPrimaryStage().setMaximized(true);
            App.switchSize();
            Assertions.assertThat(!Main.getPrimaryStage().isMaximized());
        });
    }

    @Test
    public final void closeStageTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(Main.getPrimaryStage().isShowing());
            App.closeWindow(Main.getPrimaryStage());
            Assertions.assertThat(!Main.getPrimaryStage().isShowing());
        });
    }

    @Test
    public final void minimizeStageTest(){
        Platform.runLater(() -> {
            Assertions.assertThat(Main.getPrimaryStage().isIconified());
            App.minimize(Main.getPrimaryStage());
            Assertions.assertThat(!Main.getPrimaryStage().isIconified());
        });
    }

    @Test
    public final void setCenterLayoutTest(){
        Platform.runLater(() -> {
            App.setCenterLayout(FXML_FILE);
            Assertions.assertThat(App.getMainPane().getCenter().getId().equals(MAIN_PROJECT_BORDER_PANE_ID));
        });
    }
}