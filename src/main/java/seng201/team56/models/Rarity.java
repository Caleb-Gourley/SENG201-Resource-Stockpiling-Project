package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * Represents different rarities for towers (and maybe upgrades)
 * @author Sean Reitsma
 */
public enum Rarity {
    COMMON(10,20,1.5f,3, List.of(ResourceType.BCO_QUICHE,ResourceType.COQ_A_VIN)),
    UNCOMMON(20, 40, 0.8f, 1.4f, List.of(ResourceType.BCO_QUICHE, ResourceType.COQ_A_VIN, ResourceType.NICOISE_SALAD)),
    RARE(40, 80, 0.3f, 1, List.of(ResourceType.GRUYER_CHEESE_SOUFFLE, ResourceType.BOUILLABAISSE)),
    EPIC(90, 110, 0.1f, 0.25f, List.of(ResourceType.CREME_BRULEE)),
    LEGENDARY(150, 200, 0.001f, 0.1f, List.of(ResourceType.RATATOUILLE));
    private final int resourceAmountMin;
    private final int resourceAmountMax;
    private final float speedMin;
    private final float speedMax;
    private final List<ResourceType> types;

    /**
     * Constructor
     * @param resourceAmountMin Minimum resource amount
     * @param resourceAmountMax Maximum resource amount
     * @param speedMin Minimum speed (max duration)
     * @param speedMax Maximum speed (min duration)
     * @param types A list of possible {@link ResourceType}'s
     */
    Rarity(int resourceAmountMin, int resourceAmountMax, float speedMin, float speedMax, List<ResourceType> types) {
        this.resourceAmountMin = resourceAmountMin;
        this.resourceAmountMax = resourceAmountMax;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.types = types;
    }

    private static final List<Rarity> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * Picks a randomised rarity, with rarer rarities included in later rounds.
     * @param roundNumber Current round number
     * @param maxRoundNumber Max round number
     * @return the chosen Rarity
     */
    public static Rarity pickRarity(int roundNumber, int maxRoundNumber) {
        if (roundNumber <= 1/3 * maxRoundNumber) {
            return VALUES.get(RANDOM.nextInt(0, 2));
        } else if (roundNumber <= 1/2 * maxRoundNumber) {
            return VALUES.get(RANDOM.nextInt(0,3));
        } else {
            return VALUES.get(RANDOM.nextInt(1,5));
        }
    }

    /**
     * Getter for resourceAmountMin
     * @return resourceAmountMin
     */
    int getResourceAmountMin() {
        return resourceAmountMin;
    }

    /**
     * Getter for resourceAmountMax
     * @return resourceAmountMax
     */
    public int getResourceAmountMax() {
        return resourceAmountMax;
    }

    /**
     * Getter for speedMin
     * @return speedMin
     */
    public float getSpeedMin() {
        return speedMin;
    }

    /**
     * Getter for speedMax
     * @return speedMax
     */
    public float getSpeedMax() {
        return speedMax;
    }

    /**
     * Getter for types
     * @return types
     */
    public List<ResourceType> getTypes() {
        return types;
    }

    /**
     * Returns a random {@link ResourceType} for the given rarity.
     * @return the type
     */
    public ResourceType getRandomType() {
        return types.get(RANDOM.nextInt(types.size()));
    }
}
