package pl.magdalena.brejna.colourtheworldapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.utils.FxmlUtils;
import pl.magdalena.brejna.colourtheworldapp.utils.ResizeHandler;

public class Main extends Application {

    private static final String MAIN_LAYOUT_FXML = "/fxml.files/MainLayout.fxml";

    private static Stage primaryStage;
    private static BorderPane mainPane;
    private double xOffset;
    private double yOffset;

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
        stage.initStyle(StageStyle.UNDECORATED);
    }

    private void setStageMoving(Scene scene){
        ResizeHandler resizeHelper = new ResizeHandler(primaryStage);
        primaryStage.getScene().addEventFilter(MouseEvent.ANY, resizeHelper);

        scene.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    private void setStageSize(){
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1050);
        ProjectDao dao = new ProjectDao();
        if(dao.getMainStageSize() == 1)
            primaryStage.setMaximized(true);
        else
            primaryStage.setMaximized(false);
    }

    public void start(Stage stage)  {
        setPrimaryStage(stage);
        mainPane = FxmlUtils.fxmlLoader(MAIN_LAYOUT_FXML);
        Scene scene = new Scene(mainPane);

        setStageStyle(stage);
        primaryStage.setScene(scene);
        setStageSize();
        setStageMoving(scene);
        primaryStage.show();
    }
}
