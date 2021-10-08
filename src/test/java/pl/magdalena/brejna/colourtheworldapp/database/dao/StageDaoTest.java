package pl.magdalena.brejna.colourtheworldapp.database.dao;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.Main;
import java.util.concurrent.TimeoutException;

public final class StageDaoTest extends ApplicationTest {

    private StageDao dao;
    private final int MINIMIZED_VALUE = 1;
    private final int NOT_MINIMIZED_VALUE = 0;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
        dao = new StageDao();
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
    public final void getStageSizeTest(){
        final int size = dao.getMainStageSize();
        Assertions.assertThat(size == NOT_MINIMIZED_VALUE || size == MINIMIZED_VALUE);
    }

    @Test
    public final void updateStageSize(){
        final int size = dao.getMainStageSize();
        if(size == NOT_MINIMIZED_VALUE)
            dao.updateMainStageSize(MINIMIZED_VALUE);
        else
            dao.updateMainStageSize(NOT_MINIMIZED_VALUE);
        Assertions.assertThat(size != dao.getMainStageSize());
        dao.updateMainStageSize(size);
    }
}