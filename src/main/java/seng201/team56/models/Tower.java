package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * Represents an in-game tower.
 * @author Sean Reitsma
 */
public class Tower implements Purchasable{
    private int resourceFullAmount;
    private int resourceAmount;
    private double reloadSpeed;
    private ResourceType resourceType;
    private int level;
    private int cost;
    private String name;

    /**
     * Constructor
     * Sets the default level to 0
     * @param type the ResourceType of the new Tower
     * @param amount the amount the tower fills each reload
     * @param speed the reload speed of the tower (time between reloads in seconds)
     * @param cost the tower's cost
     */
    public Tower(ResourceType type, int amount, double speed, int cost) {
        this.resourceType = type;
        this.resourceFullAmount = amount;
        this.reloadSpeed = speed;
        this.cost = cost;
        this.level = 0;
    }

    public Tower(Rarity rarity) {
        Random rng = new Random();
        List<ResourceType> types = rarity.getTypes();
        this.resourceType = types.get(rng.nextInt(types.size()));
        this.resourceFullAmount = rng.nextInt(rarity.getResourceAmountMin(), rarity.getResourceAmountMax() + 1);
        this.reloadSpeed = rng.nextDouble(rarity.getSpeedMin(), rarity.getSpeedMax());
        switch (rarity) {
            case COMMON -> this.cost = rng.nextInt(10,20);
            case UNCOMMON -> this.cost = rng.nextInt(20,30);
            case RARE -> this.cost = rng.nextInt(30,40);
            case EPIC -> this.cost = rng.nextInt(40,50);
            case LEGENDARY -> this.cost = rng.nextInt(100,200);
        }
    }

    /**
     * Getter for cost
     *
     * @return cost
     */
    @Override
    public int getBuyPrice() {
        return cost;
    }

    /**
     * Returns the sell price of the tower. At this stage this is simply the value of cost but we
     * could implement depreciation to make it more interesting.
     *
     * @return cost
     */
    @Override
    public int getSellPrice(){
        return cost;
    }

    /**
     * Returns a user-friendly description of the Tower.
     * @return A formatted string containing the stats of the Tower which can then be displayed to the user
     */
    @Override
    public String getDescription() {
        return String.format("%s: A %s restaurant with reload speed: %f, capacity: %d. The chef has %d years experience.",
                name,resourceType.getName(),reloadSpeed,resourceFullAmount,level);
    }

    /**
     * Getter for the resourceType
     * @return resourceType
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Getter for the tower's level
     * @return level
     */
    public int getLevel() {
        return level;
    }

    public void levelUp() {
        level++;
    }

    /**
     * Getter for the resource amount when the tower is full
     * @return resourceFullAmount
     */
    public int getResourceFullAmount() {
        return resourceFullAmount;
    }

    /**
     * Getter for the reload speed of the tower
     * @return reloadSpeed
     */
    public double getReloadSpeed() {
        return reloadSpeed;
    }

    /**
     * Reload the tower back to the full resource amount
     */
    public void reload() {
        //Delegate responsibility of reloadSpeed logic to the relevant service class
        resourceAmount = resourceFullAmount;
    }

    /**
     * Fills one cart with resourceAmount and then reloads.
     * If the cart cannot take a full resourceAmount then simply set resourceAmount to the difference
     * @param cart The cart to be filled
     */
    public void fillCart(Cart cart) {
        resourceAmount = cart.fillAmount(resourceAmount);
        if (resourceAmount == 0) {
            reload();
        }
    }
}
