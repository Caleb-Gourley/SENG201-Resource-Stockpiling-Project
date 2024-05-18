package seng201.team56.services.util;

import seng201.team56.models.Difficulty;

public class RandomEvent {
    private int weight;
    private final Runnable action;

    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty) {
        this.action = action;
        this.weight = difficulty.getProbabilityModifier() + roundNum;
    }

    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty, int towerUseCount) {
        this.action = action;
        this.weight = difficulty.getProbabilityModifier() + roundNum + towerUseCount;
    }

    public RandomEvent(Runnable action, int weight) {
        this.action = action;
        this.weight = weight;
    }

    public void act() {
        action.run();
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getProbability() {
        return weight;
    }

    @Override
    public String toString() {
        return "RandomEvent{" +
                ", weight=" + weight +
                ", action=" + action +
                '}';
    }
}
