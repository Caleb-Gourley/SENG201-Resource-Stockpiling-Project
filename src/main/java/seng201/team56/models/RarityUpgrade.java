package seng201.team56.models;

public class RarityUpgrade extends Upgrade{
    /**
     * Constructor
     * Sets the initial cost and modiferAmount to 0 and modiferType to null
     *
     * @param cost          the upgrades cost
     * @param modiferAmount the strength of the modifer
     */
    public RarityUpgrade(int cost, double modiferAmount) {
        super(cost, "Rarity", modiferAmount);
    }

    @Override
    public Tower applyUpgrade(Tower tower) {
        return new Tower(Rarity.getNextRarity(tower.getRarity()));
    }
}
