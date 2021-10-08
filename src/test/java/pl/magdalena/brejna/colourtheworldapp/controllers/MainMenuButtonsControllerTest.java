package pl.magdalena.brejna.colourtheworldapp.controllers;

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
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public final class MainMenuButtonsControllerTest extends ApplicationTest {

    private final String POLISH_BUTTON = "#polishButton";
    private final String ENGLISH_BUTTON = "#englishButton";
    private final String GERMAN_BUTTON = "#germanButton";
    private final String ADD_PROJECT = "#addProject";
    private final String SHOW_PROJECTS = "#showProjects";

    private final String PROJECT_BORDER_PANE_ID = "mainProjectBorderPane";
    private final String OVERVIEW_BORDER_PANE_ID = "overviewBorderPane";

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

    //languages tests
    @Test
    public final void PolishLocaleTest(){
        clickOn(POLISH_BUTTON);
        Assertions.assertThat(Locale.getDefault() == new Locale("pl", "PL"));
    }

    @Test
    public final void EnglishLocaleTest(){
        clickOn(ENGLISH_BUTTON);
        Assertions.assertThat(Locale.getDefault() == Locale.ENGLISH);
    }

    @Test
    public final void GermanLocaleTest(){
        clickOn(GERMAN_BUTTON);
        Assertions.assertThat(Locale.getDefault() == Locale.GERMAN);
    }

    //buttons tests
    @Test
    public final void NewProjectTest(){
        clickOn(ADD_PROJECT);
        Assertions.assertThat(App.getMainPane().getCenter().getId().equals(PROJECT_BORDER_PANE_ID));
    }

    @Test
    public final void OpenOverviewTest(){
        clickOn(SHOW_PROJECTS);
        Assertions.assertThat(App.getMainPane().getCenter().getId().equals(OVERVIEW_BORDER_PANE_ID));
    }
}