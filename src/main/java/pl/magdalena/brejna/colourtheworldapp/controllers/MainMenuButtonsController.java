package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.utils.ResourceBundleFactory;

import java.util.Locale;

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

   @FXML
   private void setPolishLanguage(){
      ResourceBundleFactory.setLocale(new Locale("pl", "PL"));
   }

   @FXML
   private void setEnglishLanguage(){
      ResourceBundleFactory.setLocale(Locale.ENGLISH);
   }

   @FXML
   private void setGermanLanguage(){
      ResourceBundleFactory.setLocale(Locale.GERMAN);
   }

}
