package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import pl.magdalena.brejna.colourtheworldapp.Main;
import java.util.concurrent.TimeoutException;

public final class ImageSettingsTest extends ApplicationTest {

    private final double SLIDER_HALF_VALUE = 0.5;
    private final int ZERO = 0;

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
    public final void scrollInitialValuesTest(){
        final ScrollPane testScrollPane = new ScrollPane();
        ImageSettings.setScrollInitialValues(testScrollPane);
        Assertions.assertThat((testScrollPane.getHvalue() == SLIDER_HALF_VALUE) && (testScrollPane.getVvalue() == SLIDER_HALF_VALUE));
    }

    @Test
    public final void mouseCoordinatesStoredTest(){
        final ObjectProperty<Point2D> mouseCoordinatesBeforePress = ImageSettings.getLastMouseCoordinates();
        final ScrollPane testScrollPane = new ScrollPane();
        testScrollPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event ->  ImageSettings.pressImage(event));
        press(MouseButton.PRIMARY);
        Assertions.assertThat(!mouseCoordinatesBeforePress.equals(ImageSettings.getLastMouseCoordinates()));
    }

    @Test
    public final void scrollPaneValuesAfterDragTest(){
        final ScrollPane testScrollPane = new ScrollPane();
        final Group groupContent = new Group();
        final ImageView imageView = new ImageView();
        groupContent.getChildren().addAll(imageView);
        testScrollPane.setContent(groupContent);
        groupContent.addEventHandler(MouseEvent.DRAG_DETECTED, event ->  ImageSettings.dragImage(event, testScrollPane, groupContent));

        drag(MouseButton.PRIMARY);
        Assertions.assertThat((testScrollPane.getHvalue() >= ZERO) && (testScrollPane.getHvalue() <= testScrollPane.getHmax()));
        Assertions.assertThat((testScrollPane.getVvalue() >= ZERO) && (testScrollPane.getVvalue() <= testScrollPane.getVmax()));
    }

    @Test
    public final void ImageScrollDependenceTest() {
        final ScrollPane testScrollPane1 = new ScrollPane();
        final Group groupContent1 = new Group();
        final ImageView testImageView1 = new ImageView();
        groupContent1.getChildren().addAll(testImageView1);
        testScrollPane1.setContent(groupContent1);

        final ScrollPane testScrollPane2 = new ScrollPane();
        final Group groupContent2 = new Group();
        final ImageView testImageView2 = new ImageView();
        groupContent1.getChildren().addAll(testImageView2);
        testScrollPane2.setContent(groupContent2);

        addScrollEventHandler(testScrollPane1, testImageView1, testImageView2);
        addScrollEventHandler(testScrollPane2, testImageView2, testImageView1);
    }

    private final void addScrollEventHandler(final ScrollPane scrollPane, final ImageView imageView1, final ImageView imageView2){
        scrollPane.addEventHandler(ScrollEvent.SCROLL, event -> {
            ImageSettings.scrollImage(event, imageView1, imageView2);
            Assertions.assertThat(imageView1.getTransforms().equals(imageView2.getTransforms()));
        });
    }
}