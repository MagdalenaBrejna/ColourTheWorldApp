package pl.magdalena.brejna.colourtheworldapp.windows;

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
import java.net.URL;
import java.util.concurrent.TimeoutException;

public final class FullViewTest extends ApplicationTest {

    private final String FULL_VIEW_LAYOUT_FXML = "/fxml.files/FullViewLayout.fxml";
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
    public final void stageContentTest(){
        Platform.runLater(() -> {
            final FullView fullView = new FullView();
            final URL url = fullView.getLoader().getLocation();
            Assertions.assertThat(url.equals(FULL_VIEW_LAYOUT_FXML));
        });
    }

    @Test
    public final void stageShowTest(){
        Platform.runLater(() -> {
            final FullView fullView = new FullView();
            Assertions.assertThat(!fullView.getNewWindow().isShowing());
            final Image testImage = new Image(CORRECT_JPG_FILE);
            fullView.showFullView(testImage);
            Assertions.assertThat(fullView.getNewWindow().isShowing());
        });
    }

    @Test
    public final void fullViewContentTest(){
        Platform.runLater(() -> {
            final FullView fullView = new FullView();
            final Image testImage = new Image(CORRECT_JPG_FILE);
            fullView.showFullView(testImage);
            Assertions.assertThat(testImage.equals(fullView.getFullViewImage()));
        });
    }
}