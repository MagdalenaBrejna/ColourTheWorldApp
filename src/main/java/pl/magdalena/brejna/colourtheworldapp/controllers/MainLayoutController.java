package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.Main;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;
import pl.magdalena.brejna.colourtheworldapp.windows.Instruction;

public final class MainLayoutController {

    private final String INSTRUCTION_LAYOUT_FXML = "/fxml.files/InstructionLayout.fxml";
    private final String ABOUT_WINDOW_LAYOUT_FXML = "/fxml.files/AboutWindowLayout.fxml";

    @FXML
    private MainMenuButtonsController mainMenuButtonsController;

    //initialize main application layout
    @FXML
    private final void initialize() {
        mainMenuButtonsController.setMainLayoutController(this);
    }

    //close application layout
    @FXML
    private final void closeApplication() {
        App.closeApplication();
    }

    //minimize application layout
    @FXML
    private final void minimizeApplication(final ActionEvent event){
        App.minimize(Main.getPrimaryStage());
    }

    //switch application size
    @FXML
    private final void switchApplicationSize(){
        App.switchSize();
    }

    //make application always on top
    @FXML
    private final void setAlwaysOnTop(){
        App.setAppOnTop();
    }

    //open instruction window
    @FXML
    private final void showInstruction(){
        Instruction instruction = new Instruction(INSTRUCTION_LAYOUT_FXML);
        instruction.showWindow();
    }

    //open about window
    @FXML
    private final void showAboutSection(){
        AboutWindow aboutWindow = new AboutWindow(ABOUT_WINDOW_LAYOUT_FXML);
        aboutWindow.showWindow();
    }
}