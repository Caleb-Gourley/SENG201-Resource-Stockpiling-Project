package seng201.team56.models;

/**
 * Game difficulty options
 * @author Sean Reitsma
 */
public enum Difficulty {
    EASY(100, 2, "Easy"),MEDIUM(50, 3, "Medium"),HARD(20, 5, "Hard");

    private final int startMoney;
    private final int probabilityModifier;
    private final String description;
    Difficulty(int startMoney, int probabilityModifier, String description) {
        this.startMoney = startMoney;
        this.probabilityModifier = probabilityModifier;
        this.description = description;
    }
    public int getStartMoney() {
        return startMoney;
    }

    public int getProbabilityModifier() {
        return probabilityModifier;
    }

    @Override
    public String toString() {
        return description;
    }
}
