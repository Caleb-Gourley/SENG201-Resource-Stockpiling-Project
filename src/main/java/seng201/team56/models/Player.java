package seng201.team56.models;

import seng201.team56.models.upgrades.Upgrade;

/**
 * Represents the player and stores player-specific game-wide data.
 * @author Sean Reitsma
 */
public class Player {
    private final String name;
    private final Inventory inventory;
    private Difficulty difficulty;
    private final int maxRounds;
    private int money;
    private int xp;

    /**
     * Getter for maxRounds
     * @return maxRounds
     */
    public int getMaxRounds() {
        return maxRounds;
    }

    /**
     * Constructor.
     * Sets the player name, difficulty, and the initial inventory. Sets money and xp to 0.
     * @param name the player's chosen name
     * @param difficulty the {@link Difficulty} for the game
     * @param startInventory the {@link Inventory} the player starts with
     * @param maxRounds the maximum number of rounds in the game
     */
    public Player(String name, Difficulty difficulty, Inventory startInventory, int maxRounds) {
        this.name = name;
        this.difficulty = difficulty;
        this.inventory = startInventory;
        this.maxRounds = maxRounds;
        this.money = difficulty.getStartMoney();
        this.xp = 40;
    }

    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for difficulty.
     * @param difficulty the new difficulty to set
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter for the game difficulty.
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Getter for how much money the player has.
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Add money to the player.
     * @param amount the amount to add
     */
    public void addMoney(int amount) {
        money += amount;
    }

    /**
     * Add xp to the player.
     * @param amount the amount to add
     */
    public void addXp(int amount) {
        xp += amount;
    }

    /**
     * Remove xp from the player.
     * @param amount the amount to remove
     */
    public void subXp(int amount) {
        xp -= amount;
    }

    /**
     * Getter for xp
     * @return xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * Add an item to the player's inventory.
     * Determine whether the item is an upgrade or a tower and add it to the appropriate inventory list.
     * @param item a {@link Purchasable} to add to the player's inventory
     */
    public void addItem(Purchasable item) {
        if (item instanceof Tower) {
            inventory.addTower((Tower) item);
        } else if (item instanceof Upgrade) {
            inventory.addUpgrade((Upgrade<?>) item);
        }
    }

    /**
     * Getter for Inventory.
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Subtracts amount from the player's money if the player has enough.
     * @param amount the amount to subtract
     * @return true if the money is subtracted, false if the player does not have enough money
     */
    public boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        } else {
            return false;
        }
    }
}
