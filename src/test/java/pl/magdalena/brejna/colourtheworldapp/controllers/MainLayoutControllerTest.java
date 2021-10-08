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
import java.util.concurrent.TimeoutException;

public final class MainLayoutControllerTest extends ApplicationTest {

    private final String MINIMIZE_BUTTON = "#minimizeButton";
    private final String RESIZE_BUTTON = "#winodwResizeButton";

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
        Assertions.assertThat(Main.getPrimaryStage().isIconified() == false);
        clickOn(MINIMIZE_BUTTON);
        Assertions.assertThat(Main.getPrimaryStage().isIconified());
    }

    @Test
    public final void sizeUnitTest(){
        final boolean isMaximized = Main.getPrimaryStage().isMaximized();
        clickOn(RESIZE_BUTTON);
        Assertions.assertThat(isMaximized != Main.getPrimaryStage().isMaximized());
    }

    @Test
    public final void sizeIntegrationTest(){
        Platform.runLater(() -> {
            final boolean isMaximized = Main.getPrimaryStage().isMaximized();
            clickOn(RESIZE_BUTTON);
            Main.getPrimaryStage().close();
            Platform.runLater(() -> new Main().start(new Stage()));
            Assertions.assertThat(Main.getPrimaryStage().isMaximized() == isMaximized);
        });
    }
}