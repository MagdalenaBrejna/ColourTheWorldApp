package pl.magdalena.brejna.colourtheworldapp.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.controllers.ZoomController;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import java.io.IOException;

public class Zoom {

    private final String ZOOM_FXML = "/fxml.files/ZoomLayout.fxml";

    //elements necessary to serve zoomLayout
    private FXMLLoader loader;
    private ZoomController zoomController;
    private Stage newWindow;

    public Zoom(){
        setZoomSettings();
    }

    //set necessary entry settings
    private void setZoomSettings(){
        loader = new FXMLLoader(getClass().getResource(ZOOM_FXML));
        loader.setResources(FxmlUtils.getResourceBundle());

        Parent root = setRoot();
        updateZoomImage(null);

        newWindow = new Stage();
        newWindow.setTitle("Zoom");
        newWindow.setScene(new Scene(root));

    }

    //update image set in zoom window
    public void updateZoomImage(Image image){
        zoomController = loader.getController();
        zoomController.setZoomImage(image);
    }

    //set root (only once)
    private Parent setRoot(){
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    //show created zoom image
    public void showZoom(Project activeProject){
        updateZoomImage(activeProject.getColouringBookImage());

        newWindow.setWidth(activeProject.getColouringBookImage().getWidth());
        newWindow.setHeight(activeProject.getColouringBookImage().getHeight());
        newWindow.show();
    }
}
