module ColourTheWorldApp {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens pl.magdalena.brejna.colourtheworldapp.controllers to javafx.fxml;
    exports pl.magdalena.brejna.colourtheworldapp;
}