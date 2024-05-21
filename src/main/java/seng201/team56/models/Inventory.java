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
        this.towers = new ArrayList<>(5);
        this.towers.addAll(startTowers);
        this.upgrades = new ArrayList<>();
        this.fieldTowers = new ArrayList<>(5);
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
     * Increment tower use counts.
     */
    public void incFieldTowers() {
        for (Tower tower : fieldTowers) {
            tower.incUseCount();
        }
    }

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

    /**
     * Move a tower from an index in the field list to the reserve list.
     * @param index the index in the field list of the tower to remove
     */
    public void moveFieldTower(int index) {
        Tower tower = fieldTowers.get(index);
        removeFieldTowers(tower);
        towers.add(tower);
    }

    /**
     * Move a tower from an index in the reserve list to the field list.
     * @param index the index in the reserve list of the tower to remove
     */
    public void moveReserveTower(int index) {
        Tower tower = towers.get(index);
        towers.remove(index);
        fieldTowers.add(tower);
    }

    public void moveTower(Tower tower) {
        if (towers.contains(tower)) {
            towers.remove(tower);
            fieldTowers.add(tower);
        } else if (fieldTowers.contains(tower)) {
            fieldTowers.remove(tower);
            towers.add(tower);
        }
    }
}
