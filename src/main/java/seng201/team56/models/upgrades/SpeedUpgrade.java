package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

/**
 * An upgrade which increases a tower's speed.
 *
 * @author Sean Reitsma
 */
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

    /**
     * Increases the tower's reload speed <em>in place</em>.
     * @param tower Tower to apply the upgrade to
     * @return the modified Tower
     */
    @Override
    public Tower applyUpgrade(Tower tower) {
        if (!tower.isBroken()) {
            tower.decreaseReloadInterval(getModifierAmount());
        }
        return tower;
    }
}
