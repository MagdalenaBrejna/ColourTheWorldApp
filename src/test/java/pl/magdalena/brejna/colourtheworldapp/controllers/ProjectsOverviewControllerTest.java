package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.application.Platform;
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

public final class ProjectsOverviewControllerTest extends ApplicationTest {

    private final String PROJECTS_OVERVIEW_FXML = "/fxml.files/ProjectsOverViewLayout.fxml";
    private final String BACK_TO_MAIN_BUTTON = "#backToMainButton";
    private final String MAIN_BUTTONS_BORDER_PANE_ID = "mainMenuButtonsPane";

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
    public final void backToMainLayoutTest(){
        Platform.runLater(() -> {
            App.setCenterLayout(PROJECTS_OVERVIEW_FXML);
            clickOn(BACK_TO_MAIN_BUTTON);
            Assertions.assertThat(App.getMainPane().getCenter().getId().equals(MAIN_BUTTONS_BORDER_PANE_ID));
        });
    }
}