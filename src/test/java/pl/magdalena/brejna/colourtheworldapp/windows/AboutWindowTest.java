package pl.magdalena.brejna.colourtheworldapp.windows;

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
import java.net.URL;
import java.util.concurrent.TimeoutException;

public final class AboutWindowTest extends ApplicationTest {

    private final String ABOUT_LAYOUT_FXML = "/fxml.files/AboutWindowLayout.fxml";

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start (Stage stage){ }

    @After
    public void after() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void stageContentTest(){
        Platform.runLater(() -> {
            final AboutWindow aboutWindow = new AboutWindow(ABOUT_LAYOUT_FXML);
            final URL url = aboutWindow.getLoader().getLocation();
            Assertions.assertThat(url.equals(ABOUT_LAYOUT_FXML));
        });
    }

    @Test
    public final void stageShowTest(){
        Platform.runLater(() -> {
            final AboutWindow aboutWindow = new AboutWindow(ABOUT_LAYOUT_FXML);
            Assertions.assertThat(!aboutWindow.getNewWindow().isShowing());
            aboutWindow.showWindow();
            Assertions.assertThat(aboutWindow.getNewWindow().isShowing());
        });
    }
}