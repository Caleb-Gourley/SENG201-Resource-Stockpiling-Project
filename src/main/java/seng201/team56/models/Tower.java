package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * Represents an in-game tower.
 * @author Sean Reitsma
 */
public class Tower implements Purchasable {
    private static final int LEVELUP_THRESHOLD = 100;
    private int resourceAmount;
    private long reloadSpeed;
    private ResourceType resourceType;
    private int level;
    private int xp;
    private int cost;
    private String name;
    private Rarity rarity;
    private int useCount;
    private final Random rng = new Random();

    /**
     * Construct a new Tower.
     * Sets the default level to 0.
     * @param type the ResourceType of the new Tower
     * @param amount the amount the tower fills each reload
     * @param speed the reload speed of the tower (time between reloads in milliseconds)
     * @param cost the tower's cost
     */
    public Tower(ResourceType type, int amount, long speed, int cost) {
        this.resourceType = type;
        this.resourceAmount = amount;
        this.reloadSpeed = speed;
        this.cost = cost;
        this.level = 0;
        this.xp = 0;
        //A negative distance means the Tower is not on the track
    }

    /**
     * Construct a new randomised Tower based on {@link Rarity}.
     * @param rarity the {@link Rarity} to base the tower on
     */
    public Tower(Rarity rarity) {
        List<ResourceType> types = rarity.getTypes();
        this.resourceType = types.get(rng.nextInt(types.size()));
        this.resourceAmount = rng.nextInt(rarity.getResourceAmountMin(), rarity.getResourceAmountMax() + 1);
        this.reloadSpeed = rng.nextLong(rarity.getSpeedMin(), rarity.getSpeedMax());
        this.cost = rng.nextInt(rarity.getCostMin(),rarity.getCostMax());
        this.level = 0;
        this.xp = 0;
        //A negative distance means the Tower is not on the track
        this.rarity = rarity;
    }

    /**
     * Getter for rarity
     * @return the rarity of the tower
     */
    public Rarity getRarity() {
        return rarity;
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
     * Returns the sell price of the tower. At this stage this is simply the value of cost, but we
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
        return String.format("%s: A %s restaurant with reload speed: %d, capacity: %d. The chef has %d years experience.",
                name,resourceType.toString(),reloadSpeed,resourceAmount,level);
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

    /**
     * Add xp to the tower.
     * @param xp amount to increment the tower's current xp by.
     */
    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp >= LEVELUP_THRESHOLD) {
            levelUp();
            this.xp = 0;
        }
    }

    /**
     * Increment tower use count.
     */
    public void incUseCount() {
        this.useCount++;
    }

    /**
     * Getter for the use count of the tower.
     * @return useCount
     */
    public int getUseCount() {
        return useCount;
    }

    /**
     * Level up the tower
     */
    public void levelUp() {
        level++;
        increaseResourceAmount(rng.nextInt(level + 5, (level + 5) * 2));
        decreaseReloadInterval(rng.nextLong(500,(level * 100 + 500) * 2));
    }

    /**
     * Getter for the resource amount when the tower is full
     * @return resourceFullAmount
     */
    public int getResourceAmount() {
        return resourceAmount;
    }

    /**
     * Getter for the reload speed of the tower
     * @return reloadSpeed
     */
    public long getReloadSpeed() {
        return reloadSpeed;
    }

    /**
     * Fills all carts in carts with resourceAmount (if the type matches).
     * If the cart cannot take a full resourceAmount then simply set resourceAmount to the difference
     * @param carts The carts to be filled
     */
    public synchronized void fillCarts(List<Cart> carts) {
        for (Cart cart: carts) {
            if (cart.getResourceType() == resourceType) {
                cart.fillAmount(resourceAmount);
            }
        }
    }

    /**
     * Sets the tower broken.
     */
    public void setBroken() {
    }

    /**
     * Increases the tower's capacity by a set amount.
     * @param amount the amount to add to the tower's capacity
     */
    public void increaseResourceAmount(int amount) {
        this.resourceAmount += amount;
    }

    /**
     * Decreases the tower's capacity by a set amount (if the amount is less than the current capacity).
     * @param amount the amount to subtract
     */
    public void decreaseResourceAmount(int amount) {
        if(this.resourceAmount > amount) {
            this.resourceAmount -= amount;
        }
    }

    /**
     * Increases the interval between reloads (decreases the speed).
     * @param amount the amount (in milliseconds) to add to the interval
     */
    public void increaseReloadInterval(long amount) {
        this.reloadSpeed += amount;
    }

    /**
     * Decreases the interval between reloads (increases the speed).
     * @param amount the amount (in milliseconds) to add to the interval
     */
    public void decreaseReloadInterval(long amount) {
        if (this.reloadSpeed > amount) {
            this.reloadSpeed -= amount;
        }
    }

    /**
     * Setter for name.
     * @param name the tower's new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
