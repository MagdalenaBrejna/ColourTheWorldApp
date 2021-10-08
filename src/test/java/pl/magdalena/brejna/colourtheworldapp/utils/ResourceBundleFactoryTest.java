package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.application.Platform;
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
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public final class ResourceBundleFactoryTest extends ApplicationTest {

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage stage){ }

    @After
    public void after() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void setLocaleTest(){
        Platform.runLater(() -> {
            ResourceBundleFactory.setLocale(Locale.FRANCE);
            Assertions.assertThat(Locale.getDefault() == Locale.FRANCE);
        });
    }
}