package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Inventory;
import seng201.team56.models.Tower;
import seng201.team56.models.Upgrade;

import java.util.List;
import java.util.function.IntConsumer;

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
    private int selectedTower = -1;
    private final GameEnvironment gameEnvironment;

    public MainController(GameEnvironment gameEnvironment) { this.gameEnvironment = gameEnvironment; }

    @FXML
    public void initialize() {
        List<Button> fieldButtons = List.of(fieldTower1Button,fieldTower2Button,fieldTower3Button,fieldTower4Button,fieldTower5Button);
        List<Button> resButtons = List.of(resTower1Button,resTower2Button,resTower3Button,resTower4Button,resTower5Button);
        Tower[] resTowers = new Tower[5];
        Tower[] fieldTowers = new Tower[5];
        Inventory inventory = gameEnvironment.getPlayer().getInventory();
        for (int i = 0; i < inventory.getTowers().size(); i++) {
            resTowers[i] = inventory.getTowers().get(i);
        }
        for (int i = 0; i < inventory.getFieldTowers().size(); i++) {
            fieldTowers[i] = inventory.getFieldTowers().get(i);
        }
        assignButtonGraphics(fieldButtons, fieldTowers);
        assignButtonGraphics(resButtons, resTowers);
        assignButtonActions(fieldButtons,fieldTowers,resTowers,resButtons,inventory::moveReserveTower);
        assignButtonActions(resButtons,resTowers,fieldTowers,fieldButtons,inventory::moveFieldTower);
    }

    private void assignButtonGraphics(List<Button> buttonList,Tower[] towers) {
        for (int i = 0; i < towers.length && towers[i] != null; i++) {
            Tower tower = towers[i];
            ImageView image = new ImageView(String.format("/images/tower_%s.png", tower.getRarity().getDescription().toLowerCase()));
            buttonList.get(i).setGraphic(image);
            buttonList.get(i).setContentDisplay(ContentDisplay.TOP);
        }
    }

    private void assignButtonActions(List<Button> buttonList,Tower[] towers, Tower[] otherTowers, List<Button> otherButtonList, IntConsumer inventoryAction) {
        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            int finalI = i;
            button.setOnAction(e -> {
                if (selectedTower == -1) {
                    if (towers[finalI] != null) selectedTower = finalI;
                } else if (otherTowers[selectedTower] != null) {
                    Tower tower = otherTowers[selectedTower];
                    Button otherButton = otherButtonList.get(selectedTower);
                    otherTowers[selectedTower] = towers[finalI];
                    towers[finalI] = tower;
                    Node otherButtonGraphic = otherButton.getGraphic();
                    otherButton.setGraphic(button.getGraphic());
                    button.setGraphic(otherButtonGraphic);
                    inventoryAction.accept(selectedTower);
                    selectedTower = -1;
                }
            });
        }
    }
}
