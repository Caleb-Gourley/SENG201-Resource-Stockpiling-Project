package seng201.team56.models;

/**
 * Represents the player and stores player-specific game-wide data
 * @author Sean Reitsma
 */
public class Player {
    private String name;
    private Inventory inventory;
    private Difficulty difficulty;
    private int maxRounds;
    private double money;
    private int xp;

    public int getMaxRounds() {
        return maxRounds;
    }

    /**
     * Constructor
     * Sets the player name, difficulty, and the initial inventory. Sets money and xp to 0.
     * @param name
     * @param difficulty
     * @param startInventory
     */
    public Player(String name, Difficulty difficulty, Inventory startInventory, int maxRounds) {
        this.name = name;
        this.difficulty = difficulty;
        this.inventory = startInventory;
        this.maxRounds = maxRounds;
        this.money = 0;
        this.xp = 0;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for difficulty
     * @param difficulty the new difficulty to set
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter for the game difficulty
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Getter for how much money the player has
     * @return money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Add money to the player
     * @param amount the amount to add
     */
    public void addMoney(double amount) {
        money += amount;
    }

    public void addItem(Purchasable item) {
        if (item.getClass() == Tower.class) {
            inventory.addTower((Tower) item);
        } else if (item.getClass() == Upgrade.class) {
            inventory.addUpgrade((Upgrade) item);
        }
    }

    /**
     * Subtracts amount from the player's money if the player has enough
     * @param amount the amount to subtract
     * @return true if the money is subtracted, false if the player does not have enough money
     */
    public boolean spendMoney(double amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter for player xp
     * @return xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * Add experience to the player
     * @param amount xp amount to add
     */
    public void addXp(int amount) {
        xp += amount;
    }
}
