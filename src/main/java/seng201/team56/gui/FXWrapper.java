package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team56.GameEnvironment;

import java.io.IOException;

// Implementation details of opening other screens
public class FXWrapper {

    @FXML
    private Pane pane;
    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
        new GameEnvironment(this::launchSetupScreen, this::launchMainScreen, this::clearPane);
    }

    public void launchSetupScreen(GameEnvironment gameEnvironment) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource("/fxml/TowerEatsSetup.fxml"));
            setupLoader.setControllerFactory(param -> new SetupController(gameEnvironment));
            Parent setupParent = setupLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Tower Eats");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearPane() { pane.getChildren().removeAll(pane.getChildren()); }

    public void launchMainScreen(GameEnvironment gameEnvironment) {
        try {
            FXMLLoader mainScreenLoader = new FXMLLoader(getClass().getResource("/fxml/TowerEatsMain.fxml"));
            mainScreenLoader.setControllerFactory(param -> new MainController(gameEnvironment));
            Parent setupParent = mainScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Tower Eats");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
