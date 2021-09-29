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

public class ImageSettings {

    static ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();

    //set start settings of a ScrollPane
    public static void setScrollInitialValues(ScrollPane scrollPane){
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
    }

    //scroll given image
    public static void scrollImage(ScrollEvent mouseScrollEvent,  ImageView imageView, ImageView bindedImageView){
        if (!mouseScrollEvent.isControlDown())
            return;
        double zoomFactor = setZoomFactor(mouseScrollEvent);
        scaleImage(zoomFactor, imageView);
        scaleImage(zoomFactor, bindedImageView);
        mouseScrollEvent.consume();
    }

    //choose value of zoom value
    private static double setZoomFactor(ScrollEvent mouseScrollEvent){
        if(mouseScrollEvent.getDeltaY() < 0)
            return 0.95;
        else
            return 1.05;
    }

    //transform image using calculated zoom factor
    private static void scaleImage(double zoomFactor, ImageView imageView){
        imageView.getTransforms().add(new Scale(zoomFactor, zoomFactor, 0, 0));
    }

    //set mouse coordinates of the last mouse press
    public static void pressImage(MouseEvent mousePressEvent){
        lastMouseCoordinates.set(new Point2D(mousePressEvent.getX(), mousePressEvent.getY()));
    }

    //drag image using stored mouse coordinates
    public static void dragImage(MouseEvent mousePressEvent, ScrollPane scrollPane, Group scrollContent){
        setScrollPaneHvalue(mousePressEvent, scrollPane, scrollContent);
        setScrollPaneVvalue(mousePressEvent, scrollPane, scrollContent);
    }

    //find ScrollPane H-value
    private static void setScrollPaneHvalue(MouseEvent mousePressEvent, ScrollPane scrollPane, Group scrollContent){
        double deltaX = mousePressEvent.getX() - lastMouseCoordinates.get().getX();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
        double deltaH = deltaX * (scrollPane.getHmax() - scrollPane.getHmin()) / extraWidth;
        double desiredH = scrollPane.getHvalue() - deltaH;
        scrollPane.setHvalue(Math.max(0, Math.min(scrollPane.getHmax(), desiredH)));
    }

    //find ScrollPane V-value
    private static void setScrollPaneVvalue(MouseEvent mousePressEvent, ScrollPane scrollPane, Group scrollContent){
        double deltaY = mousePressEvent.getY() - lastMouseCoordinates.get().getY();
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
        double deltaV = deltaY * (scrollPane.getHmax() - scrollPane.getHmin()) / extraHeight;
        double desiredV = scrollPane.getVvalue() - deltaV;
        scrollPane.setVvalue(Math.max(0, Math.min(scrollPane.getVmax(), desiredV)));
    }
}
