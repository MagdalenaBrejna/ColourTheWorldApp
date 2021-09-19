package pl.magdalena.brejna.colourtheworldapp.objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import pl.magdalena.brejna.colourtheworldapp.App;
import pl.magdalena.brejna.colourtheworldapp.algorithms.EdgeDetection;
import pl.magdalena.brejna.colourtheworldapp.database.dao.ProjectDao;
import pl.magdalena.brejna.colourtheworldapp.exceptions.DatabaseException;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageLoadingException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;

public class ProjectsOverview {

    private final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtonsLayout.fxml";
    private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";
    private final ProjectDao activeProjectDao = new ProjectDao();

    public void closeOverview(){
        App.setCenterLayout(MAIN_MENU_BUTTONS_FXML);
    }

    public void presentOverview(TilePane tile){
        updateProjectList();
        ObservableList<Project> projectList = ProjectListModel.getProjectList();
        loadLayout(projectList, tile);
    }

    private void updateProjectList(){
        try {
            ProjectListModel.setProjectList(activeProjectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    private void loadLayout(ObservableList<Project> projectList, TilePane tile){
        for (final Project project : projectList)
            if(!project.getSourceFile().equals("")) {
                Button projectButton = createProjectButton(project, tile);
                setPressButtonAction(projectButton);
            }
    }

    private Button createProjectButton(Project project, TilePane tile){
        ImageView imageView = createImageView(project);
        Button projectButton = makeButton(imageView);
        if(!isProjectEmpty(imageView) && isPhotoCorrected(imageView))
            tile.getChildren().addAll(projectButton);

        return projectButton;
    }

    private ImageView createImageView(Project project){
        ImageView imageView = null;
        imageView = new ImageView(new Image(project.getSourceFile()));
        imageView.setFitHeight(152);
        imageView.setFitWidth(152);
        return imageView;
    }

    private Button makeButton(ImageView imageView){
        Button projectButton = new Button();
        projectButton.setGraphic(imageView);
        projectButton.setStyle("-fx-background-color: transparent");
        return projectButton;
    }

    private boolean isProjectEmpty(ImageView imageView){
        if(imageView.getImage() != null)
            return false;
        return true;
    }

    private boolean isPhotoCorrected(ImageView imageView){
        try{
            Project project = ProjectListModel.getProjectFromList(imageView.getImage().getUrl());
            Image image = EdgeDetection.detectEdges(project);
            return true;
        }catch(ImageLoadingException e){
            return false;
        }
    }

    private void setPressButtonAction(Button projectButton){
        projectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent pressEvent) {
                String sourceFile = getSelectedProjectSourceFile(pressEvent);
                Project project = ProjectListModel.getProjectFromList(sourceFile);
                loadSelectedProject(project);
            }
        });
    }

    private String getSelectedProjectSourceFile(ActionEvent pressEvent){
        Button button = (Button) pressEvent.getSource();
        ImageView imageView = (ImageView) button.getGraphic();
        return imageView.getImage().getUrl();
    }

    private void loadSelectedProject(Project project){
        if(project != null){
            App.setStoredProject(project);
            App.setCenterLayout(MAIN_PROJECT_FXML);
        }
    }
}
