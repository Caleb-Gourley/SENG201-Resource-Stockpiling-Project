package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng201.team56.models.Difficulty;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

import java.util.ArrayList;

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
    private SetupService setupService;

    public void init(Stage stage) {
        ArrayList<Tower> initTowers = new ArrayList<>();
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        setupService = new SetupService("Name", Difficulty.EASY, initTowers, 10);
    }
}
