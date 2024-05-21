package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Tower;
import seng201.team56.models.Upgrade;
import seng201.team56.services.CounterService;

import javax.swing.plaf.basic.BasicTableUI;
import java.util.List;

/**
 * Controller for the main.fxml window
 * @author Sean Reitsma
 */
public class MainController {
    @FXML
    private Button resTower1Button;
    @FXML
    private Button resTower2Button;
    @FXML
    private Button resTower3Button;
    @FXML
    private Button resTower4Button;
    @FXML
    private Button resTower5Button;
    @FXML
    private Button fieldTower1Button;
    @FXML
    private Button fieldTower2Button;
    @FXML
    private Button fieldTower3Button;
    @FXML
    private Button fieldTower4Button;
    @FXML
    private Button fieldTower5Button;
    @FXML
    private Polyline trackLine;
    @FXML
    private ListView<Upgrade> upgradesView;
    private final GameEnvironment gameEnvironment;

    public MainController(GameEnvironment gameEnvironment) { this.gameEnvironment = gameEnvironment; }

    @FXML
    public void initialize() {
        List<Button> fieldButtons = List.of(fieldTower1Button,fieldTower2Button,fieldTower3Button,fieldTower4Button,fieldTower5Button);
        List<Button> resButtons = List.of(resTower1Button,resTower2Button,resTower3Button,resTower4Button,resTower5Button);
        for (int i = 0; i < gameEnvironment.getPlayer().getInventory().getFieldTowers().size(); i++) {
            Tower tower = gameEnvironment.getPlayer().getInventory().getFieldTowers().get(i);
            ImageView image = new ImageView(String.format("/images/tower_%s.png", tower.getRarity().getDescription().toLowerCase()));

            fieldButtons.get(i).setGraphic(image);
            fieldButtons.get(i).setContentDisplay(ContentDisplay.TOP);
            System.out.println("Hello");
        }
    }




}
