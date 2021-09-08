package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ApplicationException;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;

public class Main extends Application {

    private static final String MAIN_LAYOUT_FXML = "/fxml.files/MainLayout.fxml";

    private static Stage primaryStage;
    private static BorderPane mainPane;

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static BorderPane getMainPane(){
        return mainPane;
    }

    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setStageStyle(Stage stage){
        Image image = new Image ("https://img.freepik.com/free-photo/recycled-crumpled-white-paper-texture-paper-background_34070-1016.jpg?size=626&ext=jpg");
        mainPane.setBackground(new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true))));
        stage.setFullScreen(true);
    }

    public void start(Stage stage)  {
        setPrimaryStage(stage);
        try {
            mainPane = FxmlUtils.fxmlLoader(MAIN_LAYOUT_FXML);
        } catch (ApplicationException appException) {
            appException.callErrorMessage();
        }
        Scene scene = new Scene(mainPane);

        setStageStyle(stage);

        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
