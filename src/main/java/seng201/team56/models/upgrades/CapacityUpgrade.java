package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

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

    @Override
    public Tower applyUpgrade(Tower tower) {
        tower.increaseResourceFullAmount(getModifierAmount());
        return null;
    }
}
