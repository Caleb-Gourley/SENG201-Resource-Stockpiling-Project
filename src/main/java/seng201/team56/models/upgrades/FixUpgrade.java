package seng201.team56.models.upgrades;

import seng201.team56.models.Tower;

/**
 * An upgrade which fixes a broken tower
 * @author Sean Reitsma
 */
public class FixUpgrade extends Upgrade<Boolean>{
    /**
     * Constructs a new FixUpgrade
     * @param cost the cost of this upgrade
     */
    public FixUpgrade(int cost) {
        super(cost, "Fix tower", true);
    }

    /**
     * Fixes the tower in place and returns it.
     * @param tower Tower to apply the upgrade to
     * @return the fixed Tower
     */
    @Override
    public Tower applyUpgrade(Tower tower) {
        tower.setBroken(false);
        return tower;
    }
}
