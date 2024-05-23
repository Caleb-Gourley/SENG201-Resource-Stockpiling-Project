package seng201.team56.models;

/**
 * Game difficulty options.
 * @author Sean Reitsma
 */
public enum Difficulty {
    /**
     * Easy, gives the player 100 coins, applies a modifier of 2 to random events.
     */
    EASY(100, 2, "Easy"),
    /**
     * Medium, gives the player 50 coins, applies a modifier of 3 to random events.
     */
    MEDIUM(50, 3, "Medium"),
    /**
     * Hard, gives the player 20 coins, applies a modifier of 5 to random events.
     */
    HARD(20, 5, "Hard");

    private final int startMoney;
    private final int probabilityModifier;
    private final String description;

    /**
     * Construct a difficulty
     * @param startMoney the amount of money the player starts with
     * @param probabilityModifier the modifier to be applied to random events
     * @param description the text description of the difficulty
     */
    Difficulty(int startMoney, int probabilityModifier, String description) {
        this.startMoney = startMoney;
        this.probabilityModifier = probabilityModifier;
        this.description = description;
    }


    /**
     * Gets the money the player starts with for this difficulty.
     * @return startMoney
     */
    public int getStartMoney() {
        return startMoney;
    }

    /**
     * Gets the probability (weight) modifier which this difficulty applies to {@link seng201.team56.services.util.RandomEvent}s.
     * @return probabilityModifier
     */
    public int getProbabilityModifier() {
        return probabilityModifier;
    }

    /**
     * String representation of the difficulty.
     * @return description
     */
    @Override
    public String toString() {
        return description;
    }
}
