package seng201.team56.models;

/**
 * Interface for Upgrade and Tower.
 * @author Caleb Gourley
 */
public interface Purchasable {

    /**
     * Getter for BuyPrice
     * @return buyPrice
     */
    public double getBuyPrice();

    /**
     * Getter for SellPrice
     * @return sellPrice
     */
    public double getSellPrice();

    /**
     * Getter for Description
     * @return description
     */
    public String getDescription();
}
