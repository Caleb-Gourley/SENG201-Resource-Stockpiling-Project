package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Difficulty;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

import java.util.ArrayList;
import java.util.List;

public class SetupController {

    @FXML
    private Button EasyButton;
    @FXML
    private Button MediumButton;
    @FXML
    private Button HardButton;
    @FXML
    private Button Tower1Button;
    @FXML
    private Button Tower2Button;
    @FXML
    private Button Tower3Button;
    @FXML
    private Button Tower4Button;
    @FXML
    private Button Tower5Button;
    @FXML
    private Button Tower6Button;
    @FXML
    private Button Tower7Button;
    @FXML
    private Button Tower8Button;
    @FXML
    private Button Tower9Button;
    @FXML
    private Button SelectedTower1Button;
    @FXML
    private Button SelectedTower2Button;
    @FXML
    private Button SelectedTower3Button;
    @FXML
    private Button PlayButton;
    @FXML
    private TextField NameTextField;
    @FXML
    private Slider RoundsSlider;
    @FXML
    private Label TowerDescription;
    private SetupService setupService;
    private Difficulty difficulty;
    private ArrayList<Tower> selectedTower;
    private Tower[] selectedTowers = new Tower[3];
    private String name;
    private int maxRoundNum;
    private int SelectedTowerIndex = -1;
    private final GameEnvironment gameEnvironment;

    public SetupController(GameEnvironment gameEnvironment) { this.gameEnvironment = gameEnvironment; }

    @FXML
    public void initialize() {
        System.out.println("g");
        this.difficulty = Difficulty.EASY;
        List<Button> selectedTowerButtons = List.of(SelectedTower1Button, SelectedTower2Button, SelectedTower3Button);
        List<Button> towerButtons = List.of(Tower1Button, Tower2Button, Tower3Button, Tower4Button, Tower5Button, Tower6Button, Tower7Button, Tower8Button, Tower9Button);
        List<Tower> towers = SetupService.getTowersToChoose();

        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;
            towerButtons.get(i).setOnAction(event -> { updateStats(towers.get(finalI));
                System.out.println("Yay");
                SelectedTowerIndex = finalI;
                towerButtons.forEach(button -> {
                    if (button == towerButtons.get(finalI)) {
                        button.setStyle("-fx-background-color: #3b3b3; -fx-background-radius: 5");
                    } else {
                        button.setStyle("");
                    }
                });
            });
        }

        // maxRoundNum = (int)RoundsSlider.getValue();
        // PlayButton.setOnAction(event -> new SetupService(name, difficulty, List.of(selectedTowers),maxRoundNum));
    }

    public void Play() {
        // update to main scene
        // SetupService constructor call
    }

    public void updateStats(Tower tower) { TowerDescription.setText(tower.getDescription()); }
}
