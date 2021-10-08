package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.application.Platform;
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
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.windows.FullView;

import java.util.concurrent.TimeoutException;

public final class FullViewControllerTest extends ApplicationTest {

    private final String CORRECT_JPG_FILE = "file:/C:/Users/magda/Pictures/iceCream.jpg";

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
            final FullView fullView = new FullView();
            final Image testImage = new Image(CORRECT_JPG_FILE);
            fullView.showFullView(testImage);
            Assertions.assertThat(!FullView.getNewWindow().isIconified());
        });
    }
}