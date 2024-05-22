package seng201.team56.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng201.team56.models.RoundDifficulty;
import seng201.team56.services.RoundService;

import java.util.List;

public class RoundDiffPopupController {
    @FXML
    private Button option1Button;
    @FXML
    private Button option2Button;
    @FXML
    private Button option3Button;
    @FXML
    private Label option1Label;
    @FXML
    private Label option2Label;
    @FXML
    private Label option3Label;
    private RoundService roundService;
    private RoundDifficulty selectedDifficulty;

    public RoundDiffPopupController(RoundService roundService) {
        this.roundService = roundService;
    }

    @FXML
    public void initialize() {
        List<RoundDifficulty> difficulties = roundService.generateRoundDifficulties();
        List<Button> buttons = List.of(option1Button,option2Button,option3Button);
        List<Label> labels = List.of(option1Label,option2Label,option3Label);
        for (int i = 0; i < difficulties.size(); i++) {
            RoundDifficulty difficulty = difficulties.get(i);
            Label label = labels.get(i);
            label.setText(difficulties.get(i).toString());
            Button button = buttons.get(i);
            button.setOnAction(event -> {
                selectedDifficulty = difficulty;
            });
        }
    }

    @FXML
    public void onAccept() {
        roundService.setRoundDifficulty(selectedDifficulty);
        ((Stage) option1Label.getScene().getWindow()).close();
    }

    @FXML
    public void onCancel() {
        ((Stage) option1Label.getScene().getWindow()).close();
    }

}
