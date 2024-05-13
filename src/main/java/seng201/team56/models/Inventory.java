package seng201.team56.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a players inventory.
 * @author Caleb Gourley
 */
public class Inventory {

    private final ArrayList<Tower> towers;
    private final ArrayList<Upgrade> upgrades;
    private final ArrayList<Tower> fieldTowers;

    /**
     * Initialises an Inventory object with a list of starting towers
     * @param startTowers the list of starting towers
     */
    public Inventory(List<Tower> startTowers) {
        this.towers = new ArrayList<>();
        this.towers.addAll(startTowers);
        this.upgrades = new ArrayList<>();
        this.fieldTowers = new ArrayList<>();
    }

    /**
     * Adds tower objects to towers ArrayList that are in the players inventory
     * @param tower the tower object to be added to the ArrayList
     */
    public void addTower(Tower tower) { towers.add(tower); }

    /**
     * Adds tower objects to field_towers ArrayList that are placed into the field
     * @param tower the tower object to be added to the ArrayList
     */
    public void addFieldTower(Tower tower) { fieldTowers.add(tower); }

    /**
     * Adds upgrade objects to upgrades ArrayList that are in the players inventory
     * @param upgrade the upgrade object to be added to the ArrayList
     */
    public void addUpgrade(Upgrade upgrade) { upgrades.add(upgrade); }

    /**
     * Getter for towers
     * @return towers
     */
    public ArrayList<Tower> getTowers() { return towers; }

    /**
     * Getter for upgrades
     * @return upgrades
     */
    public ArrayList<Upgrade> getUpgrades() { return upgrades; }

    /**
     * Getter for field_towers
     * @return field_towers
     */
    public ArrayList<Tower> getFieldTowers() { return fieldTowers; }

    /**
     * Remove tower object when tower is removed from the field
     * @param tower the tower object to be removed from field_towers ArrayList
     */
    public void removeFieldTowers(Tower tower) { fieldTowers.remove(tower); }
}
