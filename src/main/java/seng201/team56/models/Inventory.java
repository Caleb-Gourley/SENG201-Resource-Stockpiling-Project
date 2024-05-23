package seng201.team56.models;

import seng201.team56.models.upgrades.Upgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's inventory.
 * @author Caleb Gourley
 */
public class Inventory {

    private final ArrayList<Tower> towers;
    private final ArrayList<Upgrade<?>> upgrades;
    private final ArrayList<Tower> fieldTowers;

    /**
     * Initialises an Inventory object with a list of starting towers.
     * @param startTowers the list of starting towers
     * @throws ArrayStoreException if there are more than 5 towers
     */
    public Inventory(List<Tower> startTowers) throws ArrayStoreException{
        this.towers = new ArrayList<>(5);
        if (startTowers.size() <= 5) {
            this.towers.addAll(startTowers);
        } else {
            throw new ArrayStoreException("Max number of towers reached");
        }
        this.upgrades = new ArrayList<>();
        this.fieldTowers = new ArrayList<>(5);
    }

    /**
     * Adds tower objects to towers ArrayList that are in the player's inventory.
     * @param tower the tower object to be added to the ArrayList
     * @throws ArrayStoreException if the tower object is already in the towers list or the player already has 5 towers.
     */
    public void addTower(Tower tower) throws ArrayStoreException {
        if(!towers.contains(tower) && towers.size() < 5) {
            towers.add(tower);
        } else if (towers.contains(tower)) {
            throw new ArrayStoreException("Tower already in inventory");
        } else if (towers.size() >= 5) {
            throw new ArrayStoreException("Max number of towers reached");
        }
    }

    /**
     * Adds tower objects to fieldTowers ArrayList that are placed into the field.
     * @param tower the tower object to be added to the ArrayList
     * @throws ArrayStoreException if the tower object is already in the towers list or the player already has 5 field towers.
     */
    public void addFieldTower(Tower tower) throws ArrayStoreException {
        if (!fieldTowers.contains(tower) && fieldTowers.size() < 5) {
            fieldTowers.add(tower);
        } else if (towers.contains(tower)) {
            throw new ArrayStoreException("Tower already in inventory");
        } else if (towers.size() >= 5) {
            throw new ArrayStoreException("Max number of towers reached");
        }
    }

    /**
     * Increment tower use counts.
     */
    public void incFieldTowers() {
        for (Tower tower : fieldTowers) {
            tower.incUseCount();
        }
    }

    /**
     * Adds upgrade objects to upgrades ArrayList that are in the player's inventory.
     * @param upgrade the upgrade object to be added to the ArrayList
     */
    public void addUpgrade(Upgrade<?> upgrade) { upgrades.add(upgrade); }

    /**
     * Getter for towers.
     * @return towers
     */
    public ArrayList<Tower> getTowers() { return towers; }

    /**
     * Getter for upgrades.
     * @return upgrades
     */
    public ArrayList<Upgrade<?>> getUpgrades() { return upgrades; }

    /**
     * Getter for fieldTowers.
     * @return fieldTowers
     */
    public ArrayList<Tower> getFieldTowers() { return fieldTowers; }

    /**
     * Toggles a tower from one group to another. If the tower is not in either group this method does nothing.
     * @param tower the tower to move
     * @throws ArrayStoreException if the group the tower is moved to is full or already contains the tower
     * @see Inventory#addFieldTower(Tower)
     * @see Inventory#addTower(Tower)
     */
    public void moveTower(Tower tower) throws ArrayStoreException{
        if (towers.contains(tower)) {
            towers.remove(tower);
            addFieldTower(tower);
        } else if (fieldTowers.contains(tower)) {
            fieldTowers.remove(tower);
            addTower(tower);
        }
    }
}
