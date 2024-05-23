package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * Represents different rarities for towers.
 * @author Sean Reitsma
 */
public enum Rarity {
    /**
     * Common
     */
    COMMON("Common",10,20,1500,3000, 10, 20, List.of(ResourceType.BCO_QUICHE,ResourceType.COQ_A_VIN)),
    /**
     * Uncommon
     */
    UNCOMMON("Uncommon",20, 40, 800, 1400, 20, 30, List.of(ResourceType.BCO_QUICHE, ResourceType.COQ_A_VIN, ResourceType.NICOISE_SALAD)),
    /**
     * Rare
     */
    RARE("Rare",40, 80, 300, 1000, 30, 40, List.of(ResourceType.GRUYER_CHEESE_SOUFFLE, ResourceType.BOUILLABAISSE)),
    /**
     * Epic
     */
    EPIC("Epic",90, 110, 100, 250, 40, 50, List.of(ResourceType.CREME_BRULEE)),
    /**
     * Legendary
     */
    LEGENDARY("Legendary",150, 200, 1, 100, 100, 200, List.of(ResourceType.RATATOUILLE));
    private final int resourceAmountMin;
    private final int resourceAmountMax;
    private final long speedMin;
    private final long speedMax;
    private final int costMin;
    private final int costMax;
    private final List<ResourceType> types;
    private final String description;

    /**
     * Constructor
     *
     * @param description       The description of the rarity
     * @param resourceAmountMin Minimum resource amount
     * @param resourceAmountMax Maximum resource amount
     * @param speedMin          Minimum speed (max duration)
     * @param speedMax          Maximum speed (min duration)
     * @param costMin           The minimum cost of any tower
     * @param costMax           The maximum cost of any tower
     * @param types             A list of possible {@link ResourceType}'s
     */
    Rarity(String description, int resourceAmountMin, int resourceAmountMax, long speedMin, long speedMax, int costMin, int costMax, List<ResourceType> types) {
        this.description = description;
        this.resourceAmountMin = resourceAmountMin;
        this.resourceAmountMax = resourceAmountMax;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.costMin = costMin;
        this.costMax = costMax;
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
        if (roundNumber <= maxRoundNumber / 3) {
            return VALUES.get(RANDOM.nextInt(0, 2));
        } else if (roundNumber <= maxRoundNumber * 2 / 3) {
            return VALUES.get(RANDOM.nextInt(0,3));
        } else {
            return VALUES.get(RANDOM.nextInt(1,SIZE - 1));
        }
    }

    /**
     * Gets the rarity 1 level above this one (i.e. if the rarity passed in is "Common" getNextRarity returns "Uncommon")
     * @param rarity the current Rarity
     * @return the next Rarity
     */
    public static Rarity getNextRarity(Rarity rarity) {
        return VALUES.get(VALUES.indexOf(rarity) + 1);
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
    public long getSpeedMin() {
        return speedMin;
    }

    /**
     * Getter for speedMax
     * @return speedMax
     */
    public long getSpeedMax() {
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

    /**
     * String representation of Rarity
     * @return description
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * Getter for costMin.
     * @return costMin
     */
    public int getCostMin() {
        return costMin;
    }

    /**
     * Getter for costMax.
     * @return costMax
     */
    public int getCostMax() {
        return costMax;
    }
}
