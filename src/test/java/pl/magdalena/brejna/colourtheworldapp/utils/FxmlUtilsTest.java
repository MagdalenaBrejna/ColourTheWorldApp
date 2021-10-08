package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
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

public final class FxmlUtilsTest extends ApplicationTest {

    private final String FXML_FILE = "/fxml.files/MainProjectLayout.fxml";
    private final String MAIN_PROJECT_BORDER_PANE_ID = "mainProjectBorderPane";
    private final String MAIN_LOCATION = "file:/C:/Moje%20dokumenty/STUDIA/NAUKA/JAVA/ColourTheWorldApp/target/classes/fxml.files/MainProjectLayout.fxml";

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
    public final void correctFxmlLoaderTest(){
        Platform.runLater(() -> {
            final BorderPane appPane = App.getMainPane();
            appPane.setCenter(FxmlUtils.fxmlLoader(FXML_FILE));
            Assertions.assertThat(appPane.getCenter().getId().equals(MAIN_PROJECT_BORDER_PANE_ID));
        });
    }

    @Test
    public final void getLoaderTest(){
        Platform.runLater(() -> {
            final FXMLLoader fxmlLoader = FxmlUtils.getLoader(FXML_FILE);
            Assertions.assertThat(fxmlLoader.getLocation().equals(MAIN_LOCATION));
        });
    }
}