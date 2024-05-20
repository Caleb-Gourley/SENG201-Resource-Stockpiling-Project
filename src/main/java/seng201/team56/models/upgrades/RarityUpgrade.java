package seng201.team56.models.upgrades;

import seng201.team56.models.Rarity;
import seng201.team56.models.Tower;

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

    @Override
    public Tower applyUpgrade(Tower tower) {
        return new Tower(Rarity.getNextRarity(tower.getRarity()));
    }
}
