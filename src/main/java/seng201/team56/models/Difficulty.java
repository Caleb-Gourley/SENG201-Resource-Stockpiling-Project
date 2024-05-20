package seng201.team56.models;

/**
 * Game difficulty options
 * @author Sean Reitsma
 */
public enum Difficulty {
    EASY(100, 2),MEDIUM(50, 3),HARD(20, 5);

    private final int startMoney;
    private final int probabilityModifier;
    Difficulty(int startMoney, int probabilityModifier) {
        this.startMoney = startMoney;
        this.probabilityModifier = probabilityModifier;
    }
    public int getStartMoney() {
        return startMoney;
    }

    public int getProbabilityModifier() {
        return probabilityModifier;
    }
}
