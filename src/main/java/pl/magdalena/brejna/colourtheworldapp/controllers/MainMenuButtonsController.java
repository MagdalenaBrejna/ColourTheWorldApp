package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.utils.ResourceBundleFactory;
import java.util.Locale;

public final class MainMenuButtonsController {

   private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";
   private final String PROJECTS_OVERVIEW_FXML = "/fxml.files/ProjectsOverViewLayout.fxml";

   private MainLayoutController mainLayoutController;

   //set value of the MainLayoutController
   public final void setMainLayoutController(final MainLayoutController mainController) {
       this.mainLayoutController = mainController;
   }

   //open project edition layout
   @FXML
   private final void openProject(){
       App.setCenterLayout(MAIN_PROJECT_FXML);
   }

   //open projects overview layout
   @FXML
   private final void openProjectsOverview(){
      App.setCenterLayout(PROJECTS_OVERVIEW_FXML);
   }

   //switch application language to polish
   @FXML
   private final void setPolishLanguage(){
      ResourceBundleFactory.setLocale(new Locale("pl", "PL"));
   }

   //switch application language to english
   @FXML
   private final void setEnglishLanguage(){
      ResourceBundleFactory.setLocale(Locale.ENGLISH);
   }

   //switch application language to german
   @FXML
   private final void setGermanLanguage(){
      ResourceBundleFactory.setLocale(Locale.GERMAN);
   }
}
