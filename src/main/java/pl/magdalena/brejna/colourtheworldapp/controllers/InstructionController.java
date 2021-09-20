package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.Instruction;

public class InstructionController {

    @FXML
    private void closeInstruction(){
        App.closeWindow(Instruction.getNewWindow());
    }

    @FXML
    private void minimizeInstruction(){
        App.minimize(Instruction.getNewWindow());
    }
}
