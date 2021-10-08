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
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;
import java.util.concurrent.TimeoutException;

public final class AboutWindowControllerTest extends ApplicationTest {

    private final String ABOUT_WINDOW_FXML = "/fxml.files/AboutWindowLayout.fxml";
    private final String MINIMIZE_BUTTON = "#minimizeAboutWindowButton";

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
    public final void minimizeTest(){
        Platform.runLater(() -> {
            final AboutWindow aboutWindow = new AboutWindow(ABOUT_WINDOW_FXML);
            aboutWindow.showWindow();
            Assertions.assertThat(!AboutWindow.getNewWindow().isIconified());
            clickOn(MINIMIZE_BUTTON);
            Assertions.assertThat(AboutWindow.getNewWindow().isIconified());
        });
    }
}