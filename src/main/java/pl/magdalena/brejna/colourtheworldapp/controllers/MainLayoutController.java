package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;
import pl.magdalena.brejna.colourtheworldapp.windows.Instruction;

public class MainLayoutController {

    @FXML
    private MainMenuButtonsController mainMenuButtonsController;

    @FXML
    private void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
    }

    @FXML
    private void closeApplication() {
        App.closeApplication();
    }

    @FXML
    private void minimizeApplication(ActionEvent evt){
        App.minimize(Main.getPrimaryStage());
    }

    @FXML
    private void switchApplicationSize(){
        App.switchSize();
    }

    @FXML
    private void setAlwaysOnTop(){
        App.setAppOnTop();
    }

    @FXML
    private void showInstruction(){
        Instruction instruction = new Instruction();
        instruction.showWindow();
    }

    @FXML
    private void showAboutSection(){
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.showWindow();
    }
}