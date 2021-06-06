module ColourTheWorldApp {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.swing;
    requires opencv;

    opens pl.magdalena.brejna.colourtheworldapp.controllers to javafx.fxml;
    opens pl.magdalena.brejna.colourtheworldapp.algorithms to opencv;
    exports pl.magdalena.brejna.colourtheworldapp;
}