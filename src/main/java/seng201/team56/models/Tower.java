package seng201.team56.models;

/**
 * Represents an in-game tower.
 * @author sean
 */
public class Tower {
    private int resourceFullAmount;
    private int resourceAmount;
    private float reloadSpeed;
    private ResourceType resourceType;
    private int level;
    private double cost;

    /**
     * Constructor
     * Sets the default level to 0
     * @param type the ResourceType of the new Tower
     * @param amount the amount the tower fills each reload
     * @param speed the reload speed of the tower
     * @param cost the tower's cost
     */
    public Tower(ResourceType type, int amount, float speed, double cost) {
        this.resourceType = type;
        this.resourceFullAmount = amount;
        this.reloadSpeed = speed;
        this.cost = cost;
        this.level = 0;
    }

    /**
     * Getter for cost
     * @return cost
     */
    public double getCost() {
        return cost;
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
    public float getReloadSpeed() {
        return reloadSpeed;
    }

    /**
     * Reload the tower back to the full resource amount
     */
    public void reload() {
        //TODO implement reloading, making use of reload speed
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
