package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
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
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeoutException;

public final class NegativeFilterTest extends ApplicationTest {

    private final String JPG_FILE = "file:/C:/Users/magda/Pictures/iceCream.jpg";
    private final String PNG_FILE = "file:/C:/Users/magda/Pictures/palm.png";

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
    public final void finalJPGImagePixelsColorTest() {
        final BufferedImage testImage = createImage(JPG_FILE);
        testImageTranslation(testImage);
    }

    @Test
    public final void finalPNGImagePixelsColorTest() {
        final BufferedImage testImage = createImage(PNG_FILE);
        testImageTranslation(testImage);
    }

    private final BufferedImage createImage(final String file){
        final Image image = new Image(file);
        return SwingFXUtils.fromFXImage(image, null);
    }

    private final void testImageTranslation(final BufferedImage testImage){
        final BufferedImage negativeTestImage = NegativeFilter.makeNegativeImage(testImage);
        testPixelsColors(testImage, negativeTestImage);
    }

    private void testPixelsColors(final BufferedImage testImage, final BufferedImage negativeTestImage){
        for (int y = 0; y < testImage.getHeight(); y++)
            for (int x = 0; x < testImage.getWidth(); x++)
                checkIfPixelsColorsOpposite(new Point2D(x, y), testImage, negativeTestImage);
    }

    private void checkIfPixelsColorsOpposite(final Point2D pixel, final BufferedImage testImage, final BufferedImage negativeTestImage){
        final int imagePixelRGBColor = testImage.getRGB((int)pixel.getX(), (int)pixel.getY());
        final int negativeImagePixelRGBColor = negativeTestImage.getRGB((int)pixel.getX(), (int)pixel.getY());
        Assertions.assertThat(negativeImagePixelRGBColor == (255 - imagePixelRGBColor));
    }
}