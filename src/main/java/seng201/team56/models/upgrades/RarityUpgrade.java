package seng201.team56.models.upgrades;

import seng201.team56.models.Rarity;
import seng201.team56.models.Tower;

/**
 * An upgrade which converts a Tower to the next Rarity level. This essentially just takes a tower and returns a completely
 * new Tower which is rarer than the given one.
 */
public class RarityUpgrade extends Upgrade<Rarity>{
    /**
     * Constructor
     * Sets the initial cost and modiferAmount to 0 and modiferType to null
     *
     * @param cost          the upgrades cost
     */
    public RarityUpgrade(int cost) {
        super(cost, "Rarity", null);
    }

    /**
     * Constructs a new Tower one Rarity level higher.
     * @param tower Tower to apply the upgrade to
     * @return the new Tower with the increased rarity
     */
    @Override
    public Tower applyUpgrade(Tower tower) {
        if (!tower.isBroken()) {
            return new Tower(Rarity.getNextRarity(tower.getRarity()));
        } else {
            return tower;
        }
    }

    public String getDescription() {
        return "Rarity";
    }
}
