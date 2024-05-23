package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

/**
 * An upgrade which increases a Tower's capacity (resourceAmount)
 *
 * @author Sean Reitsma
 */
public class CapacityUpgrade extends Upgrade<Integer>{
    /**
     * Constructor
     * Sets the initial cost and modifierAmount to 0 and modiferType to null
     *
     * @param cost          the upgrades cost
     * @param modifierAmount the strength of the modifer
     */
    public CapacityUpgrade(int cost, int modifierAmount) {
        super(cost, "Capacity", modifierAmount);
    }

    /**
     * Increases the resourceAmount of the tower <em>in place.</em>
     * @param tower Tower to apply the upgrade to
     * @return the modified Tower
     */
    @Override
    public Tower applyUpgrade(Tower tower) {
        tower.increaseResourceAmount(getModifierAmount());
        return tower;
    }
}
