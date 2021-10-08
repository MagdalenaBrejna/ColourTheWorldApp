package pl.magdalena.brejna.colourtheworldapp.objects;

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
import java.util.concurrent.TimeoutException;

public final class ProjectsOverviewTest extends ApplicationTest {

    private final String SHOW_PROJECTS_BUTTON = "#showProjects";
    private final String BACK_TO_MAIN_BUTTON = "#backToMainButton";
    private final String BUTTON = ".button";

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
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
    public final void closeTest(){
        clickOn(SHOW_PROJECTS_BUTTON);
        clickOn(BACK_TO_MAIN_BUTTON);
    }

    @Test
    public final void clickOnProjectTest(){
        clickOn(SHOW_PROJECTS_BUTTON);
        clickOn(BUTTON);
        Assertions.assertThat(App.getStoredProject() != null);
    }
}