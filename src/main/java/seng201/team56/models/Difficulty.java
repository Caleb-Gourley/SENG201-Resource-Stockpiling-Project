package seng201.team56.models;

/**
 * Game difficulty options
 * @author Sean Reitsma
 */
public enum Difficulty {
    EASY(100, 1.5),MEDIUM(50, 1.8),HARD(20, 2);

    private final int startMoney;
    private final double probabilityModifier;
    Difficulty(int startMoney, double probabilityModifier) {
        this.startMoney = startMoney;
        this.probabilityModifier = probabilityModifier;
    }
    public int getStartMoney() {
        return startMoney;
    }

    public double getProbabilityModifier() {
        return probabilityModifier;
    }
}
