package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

/**
 * An upgrade which makes a Tower fill a cart with any resource
 * @author Caleb Gourley
 */
public class AnyResourceUpgrade extends Upgrade<Boolean>{

    /**
     * Constructor
     * Sets the initial cost, modifierAmount to 0 and false, and modiferType to "Any Resource"
     * @param cost the upgrades cost
     */
    public AnyResourceUpgrade(int cost) { super(cost, "Any Resource", false); }

    /**
     * Set the AnyResource of the tower to true
     * @param tower Tower to apply the upgrade to
     * @return the modified Tower
     */
    @Override
    public Tower applyUpgrade(Tower tower) {
        tower.setAnyResource(true);
        return tower;
    }
}
