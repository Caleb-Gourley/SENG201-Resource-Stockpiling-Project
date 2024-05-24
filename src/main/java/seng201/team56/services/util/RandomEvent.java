package seng201.team56.services.util;

import seng201.team56.models.Difficulty;

/**
 * Represents a random event having a weight and an action.
 * @author Sean Reitsma
 */
public class RandomEvent {
    private int weight;
    private final Runnable action;
    private String description;

    /**
     * Constructs a RandomEvent.
     * @param roundNum the current round number
     * @param action the action to be performed if the event is selected
     * @param difficulty the current game difficulty (this sets a modifier for the weight)
     * @param description a string describing this event
     */
    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty, String description) {
        this.action = action;
        this.description = description;
        this.weight = difficulty.getProbabilityModifier() + roundNum;
    }

    /**
     * Constructs a RandomEvent.
     * @param roundNum the current round number
     * @param action the action to be performed if the event is selected
     * @param difficulty the current game difficulty
     * @param towerUseCount the number of times the tower which this event may apply to have been used (modifies the weight)
     * @param description a string describing this event
     */
    public RandomEvent(int roundNum, Runnable action, Difficulty difficulty, int towerUseCount, String description) {
        this.action = action;
        this.description = description;
        this.weight = difficulty.getProbabilityModifier() + roundNum + towerUseCount;
    }

    /**
     * Constructs a RandomEvent with a specific weight.
     * @param action the action to be performed if the event is selected
     * @param weight the weight of this event (higher weight means the event is more likely to be selected)
     * @param description a string describing this event
     */
    public RandomEvent(Runnable action, int weight, String description) {
        this.action = action;
        this.weight = weight;
        this.description = description;
    }

    /**
     * Execute this event
     */
    public void act() {
        action.run();
    }

    /**
     * Set the weight of this event. Higher weight means the event is more likely.
     * @param weight the weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Getter for weight
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * String representation of a RandomEvent
     * @return the representation of the event
     */
    @Override
    public String toString() {
        return description;
    }
}
