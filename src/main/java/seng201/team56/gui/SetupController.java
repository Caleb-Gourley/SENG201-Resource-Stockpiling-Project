package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Difficulty;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

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
    private final Tower[] selectedTower = new Tower[3];
    private String playerName;
    private int maxRoundNum;
    private int SelectedTowerIndex = -1;
    private final GameEnvironment gameEnvironment;

    public SetupController(GameEnvironment gameEnvironment) { this.gameEnvironment = gameEnvironment; }

    @FXML
    public void initialize() {
        this.difficulty = Difficulty.EASY;
        List<Button> selectedTowerButtons = List.of(SelectedTower1Button, SelectedTower2Button, SelectedTower3Button);
        List<Button> towerButtons = List.of(Tower1Button, Tower2Button, Tower3Button, Tower4Button, Tower5Button, Tower6Button, Tower7Button, Tower8Button, Tower9Button);
        List<Tower> towers = SetupService.getTowersToChoose();
        List<Button> difficultyButtons = List.of(EasyButton, MediumButton, HardButton);
        List<Difficulty> difficulties = List.of(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD);

        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;
            towerButtons.get(i).setOnAction(event -> { updateStats(towers.get(finalI));
                SelectedTowerIndex = finalI;
                towerButtons.forEach(button -> {
                    if (button == towerButtons.get(finalI)) {
                        button.setStyle("-fx-background-color: #3b3b3b; -fx-background-radius: 5");
                    } else {
                        button.setStyle("");
                    }
                });
            });
        }

        for (int i = 0; i < selectedTowerButtons.size(); i++) {
            int finalI = i;
            selectedTowerButtons.get(i).setOnAction(event -> {
                if (SelectedTowerIndex != -1) {
                    selectedTowerButtons.get(finalI).setText(String.valueOf(towers.get(SelectedTowerIndex).getResourceType()));
                    selectedTower[finalI] = towers.get(SelectedTowerIndex);
                }
            });
        }

        RoundsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                maxRoundNum = newValue.intValue();
        });

        NameTextField.textProperty().addListener((observable, oldValue, newValue) -> playerName = NameTextField.getText());

        for (int i = 0; i < difficultyButtons.size(); i++) {
            int finalI = i;
            difficultyButtons.get(i).setOnAction(event -> {
                difficulty = difficulties.get(finalI);
                System.out.println(difficulty);
            });
        }
    }

    public void Play() {
        // update to main scene
        // SetupService constructor call
    }

    public void updateStats(Tower tower) { TowerDescription.setText(tower.getDescription()); }
}
