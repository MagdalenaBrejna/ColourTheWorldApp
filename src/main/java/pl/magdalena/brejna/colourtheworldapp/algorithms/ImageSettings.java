package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

public class ImageSettings {

    //set image zooming
    public static void scrollImage(ScrollEvent mouseScrollEvent, AnchorPane scrollPane){
        double zoomFactor = setZoomFactor(mouseScrollEvent);
        scalePane(scrollPane, zoomFactor);
        mouseScrollEvent.consume();
    }

    //choose value of zoom value
    private static double setZoomFactor(ScrollEvent mouseScrollEvent){
        if(mouseScrollEvent.getDeltaY() < 0)
            return 0.95;
        else
            return 1.05;
    }

    //update scrollPane settings (zooming) with zoomFactor
    private static void scalePane(AnchorPane scrollPane, double zoomFactor){
        scrollPane.setScaleX(scrollPane.getScaleX()*zoomFactor);
        scrollPane.setScaleY(scrollPane.getScaleY()*zoomFactor);
    }
}
