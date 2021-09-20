package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;

public class AboutWindowController {

    @FXML
    private void closeAboutWindow(){
        App.closeWindow(AboutWindow.getNewWindow());
    }

    @FXML
    private void minimizeAboutWindow(){
        App.minimize(AboutWindow.getNewWindow());
    }
}
