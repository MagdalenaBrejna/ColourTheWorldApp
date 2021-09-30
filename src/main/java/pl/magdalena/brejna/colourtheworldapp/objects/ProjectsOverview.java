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
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.models.ProjectListModel;

public final class ProjectsOverview {

    private final String MAIN_MENU_BUTTONS_FXML = "/fxml.files/MainMenuButtonsLayout.fxml";
    private final String MAIN_PROJECT_FXML = "/fxml.files/MainProjectLayout.fxml";
    private final ProjectDao activeProjectDao = new ProjectDao();

    //close overview stage
    public final void closeOverview(){
        App.setCenterLayout(MAIN_MENU_BUTTONS_FXML);
    }

    //prepare the projects' list. Call the method that loads an overview layout
    public final void presentOverview(final TilePane tile){
        updateProjectList();
        ObservableList<Project> projectList = ProjectListModel.getProjectList();
        loadLayout(projectList, tile);
    }

    //update the list with projects stored in the table
    private final void updateProjectList(){
        try {
            ProjectListModel.setProjectList(activeProjectDao.showAllProjects());
        } catch (DatabaseException databaseException) {
            databaseException.callErrorMessage();
        }
    }

    //prepare layout that presents projects
    private final void loadLayout(final ObservableList<Project> projectList, final TilePane tile){
        for (final Project project : projectList)
            if(!project.getSourceFile().equals(""))
                createProjectButton(project, tile);
    }

    //prepare a button that presents a project
    private final void createProjectButton(final Project project, final TilePane tile){
        try {
            final ImageView imageView = createImageView(project);
            final Button projectButton = makeButton(imageView);
            tile.getChildren().addAll(projectButton);
            setPressButtonAction(projectButton);
        }catch(ImageProcessingException imageLoadingException){ }
    }

    //create an image view with project image
    private final ImageView createImageView(final Project project){
        ImageView imageView = new ImageView(new Image(project.getSourceFile()));
        checkIfPhotoCorrected(project);
        imageView.setFitHeight(152);
        imageView.setFitWidth(152);
        return imageView;
    }

    //check whether the project's photo is correct or not
    private final void checkIfPhotoCorrected(final Project project){
        EdgeDetection.detectEdges(project);
    }

    //create button that contains project image view
    private final Button makeButton(final ImageView imageView){
        final Button projectButton = new Button();
        projectButton.setGraphic(imageView);
        projectButton.setStyle("-fx-background-color: transparent");
        return projectButton;
    }

    //set project's button action -> load project on a mouse click
    private final void setPressButtonAction(final Button projectButton){
        projectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent pressEvent) {
                String sourceFile = getSelectedProjectSourceFile(pressEvent);
                Project project = ProjectListModel.getProjectFromList(sourceFile);
                loadSelectedProject(project);
            }
        });
    }

    //get the source file of the selected project
    private final String getSelectedProjectSourceFile(final ActionEvent pressEvent){
        final Button button = (Button) pressEvent.getSource();
        final ImageView imageView = (ImageView) button.getGraphic();
        return imageView.getImage().getUrl();
    }

    //save the selected project in the class app and open project creation layout
    private final void loadSelectedProject(final Project project){
        if(project != null){
            App.setStoredProject(project);
            App.setCenterLayout(MAIN_PROJECT_FXML);
        }
    }
}
