package pl.magdalena.brejna.colourtheworldapp.controllers;

import javafx.fxml.FXML;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.windows.Instruction;

public final class InstructionController {

    //close instruction's stage
    @FXML
    private final void closeInstruction(){
        App.closeWindow(Instruction.getNewWindow());
    }

    //minimize instruction's stage
    @FXML
    private final void minimizeInstruction(){
        App.minimize(Instruction.getNewWindow());
    }
}
