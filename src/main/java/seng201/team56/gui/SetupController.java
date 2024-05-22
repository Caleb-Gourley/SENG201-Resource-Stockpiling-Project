package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Difficulty;
import seng201.team56.models.Player;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

import java.util.List;

/**
 * controller for the TowerEatsMain.fxml window
 * @author Caleb Gourley
 */
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
    private Difficulty difficulty;
    private String playerName;
    private int maxRoundNum;
    private int SelectedTowerIndex = -1;
    private final GameEnvironment gameEnvironment;

    /**
     * Constructor
     * @param gameEnvironment the {@link GameEnvironment} object in charge of the game state
     */
    public SetupController(GameEnvironment gameEnvironment) { this.gameEnvironment = gameEnvironment; }

    /**
     * Initialises default values, selectedTowerButtons, towerButtons, towers, difficultyButtons, difficulties
     * Handles Pick Starting Towers button interaction
     * Handles Selected Towers button interaction
     * Handles Select rounds slider
     * Handles Name text field
     * Handles Choose difficulty buttons
     * Handles Play button interaction
     */
    @FXML
    public void initialize() {
        this.difficulty = Difficulty.EASY;
        this.maxRoundNum = 5;
        this.playerName = "Default";

        List<Button> selectedTowerButtons = List.of(SelectedTower1Button, SelectedTower2Button, SelectedTower3Button);
        List<Button> towerButtons = List.of(Tower1Button, Tower2Button, Tower3Button, Tower4Button, Tower5Button, Tower6Button, Tower7Button, Tower8Button, Tower9Button);
        List<Tower> towers = SetupService.getTowersToChoose();
        List<Tower> selectedTowers = towers.subList(0, 3);
        List<Button> difficultyButtons = List.of(EasyButton, MediumButton, HardButton);
        List<Difficulty> difficulties = List.of(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD);
        for (int i = 0; i < towerButtons.size(); i++) {
            int finalI = i;
            towerButtons.get(finalI).setText(towers.get(finalI).getName());
            towerButtons.get(i).setOnAction(event -> { updateStats(towers.get(finalI));
                SelectedTowerIndex = finalI;
                towerButtons.forEach(button -> {
                    if (button == towerButtons.get(finalI)) {
                        button.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5");
                    } else {
                        button.setStyle("");
                    }
                });
            });
        }

        for (int i = 0; i < selectedTowerButtons.size(); i++) {
            int finalI = i;
           selectedTowerButtons.get(finalI).setText(selectedTowers.get(finalI).getName());
            selectedTowerButtons.get(i).setOnAction(event -> {
                if (SelectedTowerIndex != -1 && !selectedTowers.contains(towers.get(SelectedTowerIndex))) {
                    selectedTowerButtons.get(finalI).setText(towers.get(SelectedTowerIndex).getName());
                    selectedTowers.set(finalI, towers.get(SelectedTowerIndex));
                }
            });
        }

        RoundsSlider.valueProperty().addListener((observable, oldValue, newValue) -> maxRoundNum = newValue.intValue());

        NameTextField.textProperty().addListener((observable, oldValue, newValue) -> playerName = NameTextField.getText());

        for (int i = 0; i < difficultyButtons.size(); i++) {
            int finalI = i;
            difficultyButtons.get(i).setOnAction(event -> difficulty = difficulties.get(finalI));
        }

        PlayButton.setOnAction(event -> {
            SetupService setupService = new SetupService(playerName, difficulty, selectedTowers, maxRoundNum);
            Player player = setupService.getPlayer();
            gameEnvironment.setPlayer(player);
            gameEnvironment.closeSetupScreen();
        });
    }

    /**
     * Sets label with TowerDescription
     * @param tower object to get TowerDescription from
     */
    public void updateStats(Tower tower) { TowerDescription.setText(tower.getDescription()); }
}
