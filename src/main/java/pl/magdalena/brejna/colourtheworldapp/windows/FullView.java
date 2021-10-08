package pl.magdalena.brejna.colourtheworldapp.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.magdalena.brejna.colourtheworldapp.controllers.FullViewController;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import java.io.IOException;

public final class FullView {

    private final String FULL_VIEW_FXML = "/fxml.files/FullViewLayout.fxml";
    private final String APPLICATION_EXCEPTION_MESSAGE = "root setting exception";
    private final int BORDER = 30;

    //elements necessary to serve zoomLayout
    private static FXMLLoader loader;
    private FullViewController fullViewController;
    private static Stage newWindow;
    private double xOffset;
    private double yOffset;

    public FullView(){
        setFullViewSettings();
    }

    public final static Stage getNewWindow(){
        return newWindow;
    }

    public final static FXMLLoader getLoader(){
        return loader;
    }

    //enable full view stage moving
    private final void setStageMoving(final Scene scene){
        scene.setOnMousePressed(event -> {
            xOffset = newWindow.getX() - event.getScreenX();
            yOffset = newWindow.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            newWindow.setX(event.getScreenX() + xOffset);
            newWindow.setY(event.getScreenY() + yOffset);
        });
    }

    //set necessary entry settings
    private final void setFullViewSettings(){
        loader = new FXMLLoader(getClass().getResource(FULL_VIEW_FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = null;
        try {
            root = setRoot();
        } catch (ApplicationException appException) {
            appException.callErrorMessage();
        }
        updateFullViewImage(null);

        newWindow = new Stage();
        newWindow.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        setStageMoving(scene);
        newWindow.setScene(scene);
    }

    //update image set in the full view
    public final void updateFullViewImage(final Image image){
        fullViewController = loader.getController();
        fullViewController.setFullViewImage(image);
    }

    //get image stored in the full view
    public final Image getFullViewImage(){
        fullViewController = loader.getController();
        return fullViewController.getFullViewImage();
    }

    //set root (only once)
    private final Parent setRoot() throws ApplicationException {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException exception) {
            throw new ApplicationException(APPLICATION_EXCEPTION_MESSAGE);
        }
        return root;
    }

    //show created full view image
    public final void showFullView(final Image projectImage){
        updateFullViewImage(projectImage);
        newWindow.setWidth(projectImage.getWidth());
        newWindow.setHeight(projectImage.getHeight() + BORDER);
        newWindow.show();
    }
}
