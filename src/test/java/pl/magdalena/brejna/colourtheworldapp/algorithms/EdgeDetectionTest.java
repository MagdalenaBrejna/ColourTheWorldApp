package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import java.util.concurrent.TimeoutException;

public final class EdgeDetectionTest extends ApplicationTest {

    private final String CORRECT_JPG_FILE = "file:/C:/Users/magda/Pictures/iceCream.jpg";
    private final String CORRECT_PNG_FILE = "file:/C:/Users/magda/Pictures/palm.png";
    private final String TEST_NAME_1 = "test1";
    private final String TEST_NAME_2 = "test2";
    private final Double DEFAULT_DILATION_VALUE = 0.0;
    private final Double DEFAULT_CONTRAST_VALUE = 150.0;

    @Before
    public final void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public final void start(Stage stage){ }

    @After
    public final void after() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public final void detectionWithCorrectJPGSourceFileTest() {
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_1), CORRECT_JPG_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
    }

    @Test
    public final void detectionWithCorrectPNGSourceFileTest2() {
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_2), CORRECT_PNG_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
    }

    @Test(expected = ImageProcessingException.class)
    public final void detectionWithWrongJPGSourceFileTest() {
        final String incorrectFile = CORRECT_JPG_FILE.replace('i', 'a');
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_1), incorrectFile, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
    }

    @Test(expected = ImageProcessingException.class)
    public final void detectionWithWrongPNGSourceFileTest2() {
        final String incorrectFile = CORRECT_PNG_FILE.replace('a', 'i');
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_2), incorrectFile, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
    }

    @Test
    public final void finalJPGImagePixelsColorTest() {
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_1), CORRECT_JPG_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
        checkPixelsColors(testImage);
    }

    @Test
    public final void finalPNGImagePixelsColorTest() {
        final Project testProject = new Project(new SimpleStringProperty(TEST_NAME_2), CORRECT_PNG_FILE, DEFAULT_DILATION_VALUE, DEFAULT_CONTRAST_VALUE);
        final Image testImage = EdgeDetection.detectEdges(testProject);
        checkPixelsColors(testImage);
    }

    private final void checkPixelsColors(final Image testImage){
        for (int y = 0; y < testImage.getHeight(); y++)
            for (int x = 0; x < testImage.getWidth(); x++)
                checkPixelColor(testImage.getPixelReader().getColor(x, y));
    }

    private final void checkPixelColor(final Color pixelColor){
        final java.awt.Color white = new java.awt.Color(255, 255, 255);
        final java.awt.Color black = new java.awt.Color(0, 0, 0);
        Assertions.assertThat(pixelColor.equals(white) || pixelColor.equals(black));
    }
}