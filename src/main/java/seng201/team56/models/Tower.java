package seng201.team56.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;

/**
 * Represents an in-game tower.
 * @author Sean Reitsma
 */
public class Tower implements Purchasable, PropertyChangeListener {
    private static final int LEVELUP_THRESHOLD = 100;
    private int resourceFullAmount;
    private int resourceAmount;
    private long reloadSpeed;
    private ResourceType resourceType;
    private int level;
    private int xp;
    private int cost;
    private String name;
    private double distance;
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
        this.resourceFullAmount = amount;
        this.reloadSpeed = speed;
        this.cost = cost;
        this.level = 0;
        this.xp = 0;
        //A negative distance means the Tower is not on the track
        this.distance = -1;
        reload();
    }

    /**
     * Construct a new randomised Tower based on {@link Rarity}.
     * @param rarity the {@link Rarity} to base the tower on
     */
    public Tower(Rarity rarity) {
        List<ResourceType> types = rarity.getTypes();
        this.resourceType = types.get(rng.nextInt(types.size()));
        this.resourceFullAmount = rng.nextInt(rarity.getResourceAmountMin(), rarity.getResourceAmountMax() + 1);
        this.reloadSpeed = rng.nextLong(rarity.getSpeedMin(), rarity.getSpeedMax());
        switch (rarity) {
            case COMMON -> this.cost = rng.nextInt(10,20);
            case UNCOMMON -> this.cost = rng.nextInt(20,30);
            case RARE -> this.cost = rng.nextInt(30,40);
            case EPIC -> this.cost = rng.nextInt(40,50);
            case LEGENDARY -> this.cost = rng.nextInt(100,200);
        }
        this.level = 0;
        this.xp = 0;
        //A negative distance means the Tower is not on the track
        this.distance = -1;
        this.rarity = rarity;
        reload();
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
        return String.format("%s: A %s restaurant with reload speed: %d, capacity: %d. The chef has %d years experience.",
                name,resourceType.toString(),reloadSpeed,resourceFullAmount,level);
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
        increaseResourceFullAmount(rng.nextInt(level + 5, (level + 5) * 2));
        decreaseReloadInterval(rng.nextLong(500,(level * 100 + 500) * 2));
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
    public long getReloadSpeed() {
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
        if (resourceAmount > 0) {
            resourceAmount = cart.fillAmount(resourceAmount);
        }
    }

    /**
     * This method gets called when the bound property the Tower is listening for changes. In this case the property is
     * Cart.distance.
     * @param propertyChangeEvent A {@link PropertyChangeEvent} object describing the event source and the property that
     *                            has changed
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getSource() instanceof Cart cart) {
            if (propertyChangeEvent.getPropertyName().equals("distance")
                    && (propertyChangeEvent.getNewValue() instanceof Double)
                    && ((double) propertyChangeEvent.getNewValue() >= distance)) {
                fillCart(cart);
                if(xp++ >= LEVELUP_THRESHOLD) {
                    levelUp();
                }
            }
        }
    }

    /**
     * Get the distance of the Tower on the track.
     * If the distance is negative, the Tower is not on the track (i.e. it is in the reserve group).
     * @see Tower#setDistance(double)
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set the distance of the Tower on the track. This distance corresponds to the slot the Player puts the Tower in.
     * @see Tower#getDistance()
     * @param distance the distance in metres
     */
    public void setDistance(double distance) {
        this.distance = distance;
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
    public void increaseResourceFullAmount(int amount) {
        this.resourceFullAmount += amount;
    }

    /**
     * Decreases the tower's capacity by a set amount (if the amount is less than the current capacity).
     * @param amount the amount to subtract
     */
    public void decreaseResourceFullAmount(int amount) {
        if(this.resourceFullAmount > amount) {
            this.resourceFullAmount -= amount;
        }
    }

    /**
     * Increases the interval between reloads (decreases the speed).
     * @param amount the amount (in milliseconds) to add to the interval
     */
    public void increaseReloadInvterval(long amount) {
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
