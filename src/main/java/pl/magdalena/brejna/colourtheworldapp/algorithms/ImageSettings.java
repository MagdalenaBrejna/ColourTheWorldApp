package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

public final class ImageSettings {

    private static final double SLIDER_HALF_VALUE = 0.5;
    private static final double MINIMIZE_FACTOR_VALUE = 0.95;
    private static final double MAXIMIZE_FACTOR_VALUE = 1.05;
    private static ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();

    public static ObjectProperty<Point2D> getLastMouseCoordinates(){
        return lastMouseCoordinates;
    }

    //set start settings of a ScrollPane
    public static final void setScrollInitialValues(final ScrollPane scrollPane){
        scrollPane.setHvalue(SLIDER_HALF_VALUE);
        scrollPane.setVvalue(SLIDER_HALF_VALUE);
    }

    //scroll given images
    public static final void scrollImage(final ScrollEvent mouseScrollEvent,  final ImageView imageView, final ImageView bindedImageView){
        if (mouseScrollEvent.isControlDown()) {
            final double zoomFactor = setZoomFactor(mouseScrollEvent);
            scaleImage(zoomFactor, imageView);
            scaleImage(zoomFactor, bindedImageView);
            mouseScrollEvent.consume();
        }
    }

    //choose value of zoom value
    private static final double setZoomFactor(final ScrollEvent mouseScrollEvent){
        if(mouseScrollEvent.getDeltaY() < 0)
            return MINIMIZE_FACTOR_VALUE;
        else
            return MAXIMIZE_FACTOR_VALUE;
    }

    //transform image using calculated zoom factor
    private static final void scaleImage(final double zoomFactor, final ImageView imageView){
        imageView.getTransforms().add(new Scale(zoomFactor, zoomFactor, 0, 0));
    }

    //set mouse coordinates of the last mouse press
    public static final void pressImage(final MouseEvent mousePressEvent){
        lastMouseCoordinates.set(new Point2D(mousePressEvent.getX(), mousePressEvent.getY()));
    }

    //drag image using stored mouse coordinates
    public static final void dragImage(final MouseEvent mousePressEvent, final ScrollPane scrollPane, final Group scrollContent){
        setScrollPaneHvalue(mousePressEvent, scrollPane, scrollContent);
        setScrollPaneVvalue(mousePressEvent, scrollPane, scrollContent);
    }

    //find ScrollPane H-value
    private static final void setScrollPaneHvalue(final MouseEvent mousePressEvent, final ScrollPane scrollPane, final Group scrollContent){
        final double deltaX = mousePressEvent.getX() - lastMouseCoordinates.get().getX();
        final double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
        final double deltaH = deltaX * (scrollPane.getHmax() - scrollPane.getHmin()) / extraWidth;
        final double desiredH = scrollPane.getHvalue() - deltaH;
        scrollPane.setHvalue(Math.max(0, Math.min(scrollPane.getHmax(), desiredH)));
    }

    //find ScrollPane V-value
    private static final void setScrollPaneVvalue(final MouseEvent mousePressEvent, final ScrollPane scrollPane, final Group scrollContent){
        final double deltaY = mousePressEvent.getY() - lastMouseCoordinates.get().getY();
        final double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
        final double deltaV = deltaY * (scrollPane.getHmax() - scrollPane.getHmin()) / extraHeight;
        final double desiredV = scrollPane.getVvalue() - deltaV;
        scrollPane.setVvalue(Math.max(0, Math.min(scrollPane.getVmax(), desiredV)));
    }
}
