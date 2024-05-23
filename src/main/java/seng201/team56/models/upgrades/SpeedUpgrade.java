package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

public class SpeedUpgrade extends Upgrade<Long>{
    /**
     * Constructor
     * Sets the initial cost and modifierAmount to 0 and modifierType to null
     *
     * @param cost          the upgrades cost
     * @param modifierAmount the strength of the modifier
     */
    public SpeedUpgrade(int cost, long modifierAmount) {
        super(cost, "Speed", modifierAmount);
    }

    @Override
    public Tower applyUpgrade(Tower tower) {
        tower.decreaseReloadInterval(getModifierAmount());
        return null;
    }
}
