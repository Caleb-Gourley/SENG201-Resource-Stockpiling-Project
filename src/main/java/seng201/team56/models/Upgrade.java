package seng201.team56.models;

/**
 * Represents an upgrade.
 * @author Caleb Gourley
 */
public abstract class Upgrade implements Purchasable {

    private int cost;
    private String modiferType;
    private double modiferAmount;

    /**
     * Constructor
     * Sets the initial cost and modiferAmount to 0 and modiferType to null
     * @param cost the upgrades cost
     * @param modiferType the description of the modifer
     * @param modiferAmount the strength of the modifer
     */
    public Upgrade(int cost, String modiferType, double modiferAmount) {
        this.cost = cost;
        this.modiferType = modiferType;
        this.modiferAmount = modiferAmount;
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
    public String getDescription() { return modiferType; }

    /**
     * Getter for modifer strength
     * @return modiferAmount
     */
    public double getModiferAmount() { return modiferAmount; }

    public abstract Tower applyUpgrade(Tower tower);
}
