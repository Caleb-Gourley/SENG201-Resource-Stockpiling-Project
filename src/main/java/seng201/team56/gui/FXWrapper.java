package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team56.GameEnvironment;

import java.io.IOException;

/**
 * Holds implementation to launch scenes
 * @author Caleb Gourley
 */
public class FXWrapper {

    @FXML
    private Pane pane;
    private Stage stage;

    /**
     * Initialises stage
     * Sets up methods to launch setup, main screen, and clearPane
     * @param stage JavaFX container
     */
    public void init(Stage stage) {
        this.stage = stage;
        new GameEnvironment(this::launchSetupScreen, this::launchMainScreen, this::clearPane);
    }

    /**
     * Opens the gui with the fxml content specified in resources/fxml/TowerEatsSetup.fxml
     * @param gameEnvironment the {@link GameEnvironment} object in charge of the game state
     */
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

    /**
     * Clears all children from the pane
     */
    public void clearPane() { pane.getChildren().removeAll(pane.getChildren()); }

    /**
     * Opens the gui with the fxml content specified in resources/fxml/TowerEatsMain.fxml
     * @param gameEnvironment instance of gameEnvironment
     */
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
