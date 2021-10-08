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

public final class InstructionTest extends ApplicationTest {

    private final String INSTRUCTION_LAYOUT_FXML = "/fxml.files/InstructionLayout.fxml";

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
    public final void stageContentTest(){
        Platform.runLater(() -> {
            final Instruction instruction = new Instruction(INSTRUCTION_LAYOUT_FXML);
            final URL url = instruction.getLoader().getLocation();
            Assertions.assertThat(url.equals(INSTRUCTION_LAYOUT_FXML));
        });
    }

    @Test
    public void stageShowTest(){
        Platform.runLater(() -> {
            final Instruction instruction = new Instruction(INSTRUCTION_LAYOUT_FXML);
            Assertions.assertThat(!instruction.getNewWindow().isShowing());
            instruction.showWindow();
            Assertions.assertThat(instruction.getNewWindow().isShowing());
        });
    }
}