package seng201.team56.services.util;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import seng201.team56.models.Tower;

import java.util.List;

public class TowerButtonListener implements ListChangeListener<Tower> {
    private List<Button> buttonList;
    public TowerButtonListener(List<Button> buttonList) {
        this.buttonList = buttonList;

    }
    @Override
    public void onChanged(Change<? extends Tower> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Tower tower: change.getAddedSubList()){
                    buttonList.stream().filter(button -> button.getUserData() == null).findFirst().ifPresent(button -> button.setUserData(tower));
                }
            }
        }
    }
}
