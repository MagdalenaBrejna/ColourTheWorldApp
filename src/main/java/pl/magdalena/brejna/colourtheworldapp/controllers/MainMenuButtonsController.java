package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;

public class MainMenuButtonsController {

   private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";

   private MainLayoutController mainLayoutController;

   public void setMainLayoutController(MainLayoutController mainController) {
       this.mainLayoutController = mainController;
   }

   @FXML
   private void openProject(){
       App.setCenterLayout(MAIN_PROJECT_FXML);
   }
}
