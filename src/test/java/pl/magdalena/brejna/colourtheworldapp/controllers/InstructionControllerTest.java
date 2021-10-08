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
import pl.magdalena.brejna.colourtheworldapp.windows.Instruction;
import java.util.concurrent.TimeoutException;

public final class InstructionControllerTest extends ApplicationTest{

    private final String INSTRUCTION_WINDOW_FXML = "/fxml.files/InstructionLayout.fxml";
    private final String MINIMIZE_BUTTON = "#minimizeInstructionButton";

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
            final Instruction instruction = new Instruction(INSTRUCTION_WINDOW_FXML);
            instruction.showWindow();
            Assertions.assertThat(!Instruction.getNewWindow().isIconified());
            clickOn(MINIMIZE_BUTTON);
            Assertions.assertThat(Instruction.getNewWindow().isIconified());
        });
    }
}