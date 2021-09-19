package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.utils.DialogsUtils;
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
        showExitConfirmationDialog();
    }

    @FXML
    private void minimizeApplication(ActionEvent evt){
        App.minimize();
    }

    @FXML
    private void switchApplicationSize(){
        App.switchSize();
    }

    private void showExitConfirmationDialog(){
        DialogsUtils.showConfirmationDialog("close.title", "close.text")
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
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