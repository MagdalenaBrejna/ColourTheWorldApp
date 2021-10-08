package pl.magdalena.brejna.colourtheworldapp;

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
import pl.magdalena.brejna.colourtheworldapp.database.dao.StageDao;
import java.util.concurrent.TimeoutException;

public class MainTest extends ApplicationTest {

    private final String MAIN_PANE_ID = "mainPane";
    private final String IMAGE = "https://img.freepik.com/free-photo/recycled-crumpled-white-paper-texture-paper-background_34070-1016.jpg?size=626&ext=jpg";

    private final int MAXIMIZED_VALUE = 1;
    private final int NOT_MAXIMIZED_VALUE = 0;
    private final int ZERO = 0;

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
    public final void mainFxmlTest(){
        BorderPane pane = Main.getMainPane();
        Assertions.assertThat(pane.getCenter().getId().equals(MAIN_PANE_ID));
    }

    @Test
    public final void sizeTest(){
        StageDao dao = new StageDao();
        Assertions.assertThat(((dao.getMainStageSize() == MAXIMIZED_VALUE) && Main.getPrimaryStage().isMaximized()) ||
                ((dao.getMainStageSize() == NOT_MAXIMIZED_VALUE) && !Main.getPrimaryStage().isMaximized()));
    }

    @Test
    public final void backgroundPhotoTest(){
        String backgroundImageUrl = Main.getMainPane().getBackground().getImages().get(ZERO).getImage().getUrl();
        Assertions.assertThat(backgroundImageUrl.equals(IMAGE));
    }

    @Test
    public final void pressTest(){
        Assertions.assertThat(Main.getPrimaryStage().getScene().getOnMousePressed() != null);
    }

    @Test
    public final void dragTest(){
        Assertions.assertThat(Main.getPrimaryStage().getScene().getOnMouseDragged() != null);
    }
}