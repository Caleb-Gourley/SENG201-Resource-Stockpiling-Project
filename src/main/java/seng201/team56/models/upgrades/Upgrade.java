package seng201.team56.models.upgrades;

import seng201.team56.models.Purchasable;
import seng201.team56.models.Tower;

/**
 * Represents an upgrade.
 * @author Caleb Gourley
 */
public abstract class Upgrade<T> implements Purchasable {

    private final int cost;
    private final String modifierType;
    private final T modifierAmount;

    /**
     * Constructor
     * Sets the initial cost and modifierAmount to 0 and modifierType to null
     * @param cost the upgrades cost
     * @param modifierType the description of the modifier
     * @param modifierAmount the strength of the modifier
     */
    public Upgrade(int cost, String modifierType, T modifierAmount) {
        this.cost = cost;
        this.modifierType = modifierType;
        this.modifierAmount = modifierAmount;
    }

    /**
     * Getter for buy price
     * @return cost
     */
    public int getBuyPrice() { return cost; }

    /**
     * Getter for selling price
     * @return cost / 0.5
     */
    public int getSellPrice() { return cost; }

    /**
     * Getter for Description
     * @return modiferType
     */
    public String getDescription() { return modifierType + ": " + modifierAmount.toString(); }

    /**
     * Getter for modifierAmount
     * @return modifierAmount
     */
    public T getModifierAmount() {
        return modifierAmount;
    }

    /**
     * Applies the upgrade to the given tower.
     * @param tower Tower to apply the upgrade to
     * @return the modified tower
     */
    public abstract Tower applyUpgrade(Tower tower);
}
