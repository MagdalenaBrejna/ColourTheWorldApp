package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.Main;
import java.util.concurrent.TimeoutException;

public final class ResizeHelperTest extends ApplicationTest {

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
    public final void resizeTest(){
        Platform.runLater(() -> {
            Stage testStage = createStage();
            ResizeHelper resizeHelper = new ResizeHelper(testStage);

            Assertions.assertThat(!testStage.isResizable());
            testStage.getScene().addEventFilter(MouseEvent.ANY, resizeHelper);
            Assertions.assertThat(testStage.isResizable());
        });
    }

    private final Stage createStage(){
        Stage testStage = new Stage();
        StackPane root = new StackPane();
        testStage.setScene(new Scene(root));
        testStage.initStyle(StageStyle.UNDECORATED);
        return testStage;
    }
}