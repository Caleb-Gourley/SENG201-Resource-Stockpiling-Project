package seng201.team56.gui;

import javafx.stage.Stage;
import seng201.team56.models.Difficulty;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

import java.util.ArrayList;

public class SetupController {

    private SetupService setupService;

    public void init(Stage stage) {
        ArrayList<Tower> initTowers = new ArrayList<>();
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        initTowers.add(new Tower(ResourceType.CREME_BRULEE, 10, 10, 100));
        setupService = new SetupService("Name", Difficulty.EASY, initTowers, 10);
    }
}
