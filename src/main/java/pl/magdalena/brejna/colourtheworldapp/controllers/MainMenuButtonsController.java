package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.magdalena.brejna.colourtheworldapp.App;

public class MainMenuButtonsController {

   private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";

   @FXML
   Button addProject;

   private MainLayoutController mainLayoutController;

  /* public void init(){
       addProject = new Button();
       addProject.setId("add-project");
   }
*/
   public void setMainLayoutController(MainLayoutController mainController) {
       this.mainLayoutController = mainController;
   }

   @FXML
   private void openProject(){
       App.setCenterLayout(MAIN_PROJECT_FXML);

   }
}
