package seng201.team56.services.util;

import seng201.team56.models.Difficulty;

public class RandomEvent {
    private final double probability;
    private static final double BASE_PROBABILITY = 0.10;
    private final Runnable action;

    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty) {
        this.action = action;
        this.probability = BASE_PROBABILITY * difficulty.getProbabilityModifier() + 0.01 * roundNum;
    }

    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty, int towerUseCount) {
        this.action = action;
        this.probability = BASE_PROBABILITY * difficulty.getProbabilityModifier() + 0.01 * roundNum + 0.01 * towerUseCount;
    }

    public RandomEvent(Runnable action, double probability) {
        this.action = action;
        this.probability = probability;
    }

    public void act() {
        action.run();
    }

    public double getProbability() {
        return probability;
    }
}
