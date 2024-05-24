package seng201.team56.services.util;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import seng201.team56.models.Tower;

import java.util.List;

/**
 * A listener which updates a list of tower buttons when a tower is added to an inventory list.
 */
public class TowerButtonListener implements ListChangeListener<Tower> {
    private final List<Button> buttonList;

    /**
     * Constructs a TowerButtonListener.
     * @param buttonList the list of buttons to update
     */
    public TowerButtonListener(List<Button> buttonList) {
        this.buttonList = buttonList;

    }

    /**
     * Updates the first button with a null user data (i.e. an empty tower button) with the added tower(s).
     * @param change an object representing the change that was done
     */
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
