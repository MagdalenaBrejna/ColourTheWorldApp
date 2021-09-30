package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.AboutWindow;

public final class AboutWindowController {

    //close AboutWindow's stage
    @FXML
    private final void closeAboutWindow(){
        App.closeWindow(AboutWindow.getNewWindow());
    }

    //minimize AboutWindow's stage
    @FXML
    private final void minimizeAboutWindow(){
        App.minimize(AboutWindow.getNewWindow());
    }
}
