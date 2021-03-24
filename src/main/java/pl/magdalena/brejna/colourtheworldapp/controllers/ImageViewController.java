package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageSettingException;
import pl.magdalena.brejna.colourtheworldapp.models.ImageFxModel;

public class ImageViewController {

    private MainProjectController mainProjectController;

    private static ImageFxModel imageFxModel;

    @FXML
    private ImageView imageViewBefore;

    @FXML
    private void choosePhoto(){
        try {
            imageViewBefore.setImage(imageFxModel.loadImage());
        }catch(ImageSettingException e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        this.imageFxModel = new ImageFxModel();
        this.imageFxModel.init();
        bindings();
    }

    public void bindings(){
       //this.imageViewBefore.bindBidarectional(this.imageFxModel.getImageFxObjectProperty().imageProperty());
    }

    public void setMainProjectController(MainProjectController mainController) {
        this.mainProjectController = mainController;
    }
}
