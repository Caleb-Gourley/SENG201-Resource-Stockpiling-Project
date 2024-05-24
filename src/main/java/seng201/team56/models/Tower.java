package seng201.team56.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Random;

/**
 * Represents an in-game tower.
 * @author Sean Reitsma
 */
public class Tower implements Purchasable {
    private static final int LEVELUP_THRESHOLD = 100;

    //Sourced from https://en.wikipedia.org/wiki/List_of_French_restaurants
    private static final String[] NAMES = {
            "Alain Ducasse at the Dorchester",
            "Alo",
            "Alobar Yorkville",
            "Les Amis",
            "Arpège",
            "L'Atelier de Joël Robuchon",
            "Aubergine",
            "Bagatelle restaurant",
            "Berowra Waters Inn",
            "Bistro 990",
            "Café des Artistes",
            "Café Rouge",
            "Café Royal",
            "Caprice",
            "Chez Bruce",
            "Chez Dominique",
            "Les Créations de Narisawa",
            "L'Escargot",
            "La Lune",
            "La Ferme de Mon Père",
            "Fitzgerald",
            "La Folie",
            "Fouquet's",
            "Gaddi's",
            "Galvin at Windows",
            "Le Gavroche",
            "La Grenouille",
            "Jean-Georges",
            "Jeune et Jolie",
            "Locke-Ober",
            "Lumière",
            "Lutèce",
            "La Madeleine",
            "Maison Novelli",
            "Le Manoir aux Quat' Saisons",
            "Mas",
            "Masa's Wine Bar & Kitchen",
            "Maxim's Paris",
            "Mimi's Cafe",
            "Mr & Mrs Bund",
            "L'Opéra restaurant",
            "Ortolan, Los Angeles",
            "Le Papillon",
            "Per Se",
            "Philippe's",
            "Pied à Terre",
            "Pierre",
            "Restaurant André",
            "The Restaurant Marco Pierre White",
            "Rhubarb Le Restaurant",
            "RIA",
            "Roussillon",
            "Seinpost",
            "Sketch",
            "St. Lawrence",
            "La Société",
            "La Tante Claire",
            "Tom Aikens",
            "Trois Mec",
            "Tru"
    };
    private int resourceAmount;
    private long reloadSpeed;
    private ResourceType resourceType;
    private int level;
    private int xp;
    private int cost;
    private String name;
    private Rarity rarity;
    private int useCount;
    private boolean anyResource;
    private boolean broken;
    private final Random rng = new Random();
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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
        this.broken = false;
        this.name = NAMES[rng.nextInt(NAMES.length)];
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
        this.broken = false;
        this.rarity = rarity;
        this.name = NAMES[rng.nextInt(NAMES.length)];
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
        return String.format("%s: Resource type: %s, reload speed: %d, capacity: %d, level: %d, Any Resource: %s, Broken %b",
                name,resourceType.toString(),reloadSpeed,resourceAmount,level,anyResource,broken);
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
        int oldvalue = level;
        level++;
        increaseResourceAmount(rng.nextInt(level + 5, (level + 5) * 2));
        decreaseReloadInterval(rng.nextLong(500,(level * 100L + 500) * 2));
        pcs.firePropertyChange("level", oldvalue, level);
    }

    /**
     * Add a listener to this tower.
     * @param listener the {@link PropertyChangeListener} to add
     */
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
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
        if (!broken) {
            for (Cart cart : carts) {
                if (cart.getResourceType() == resourceType) {
                    cart.fillAmount(resourceAmount);
                    pcs.firePropertyChange("fill", false, true);
                } else if (anyResource) {
                    cart.fillAmount(resourceAmount / 5);
                }
            }
        }
    }

    /**
     * Sets the tower broken.
     * @param broken true if the tower is broken
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * Returns true if the tower is broken
     * @return broken
     */
    public boolean isBroken() {
        return broken;
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
        if(this.resourceAmount >= amount) {
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
        if (this.reloadSpeed >= amount) {
            this.reloadSpeed -= amount;
        }
    }

    /**
     * Getter for the name of the tower
     * @return name
     */
    public String getName() { return name; }

    /**
     * Setter for name.
     * @param name the tower's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for anyResource
     * @param anyResource boolean for the state
     */
    public void setAnyResource(boolean anyResource) { this.anyResource = anyResource; }
}
