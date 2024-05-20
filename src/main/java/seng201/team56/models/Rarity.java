package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * Represents different rarities for towers (and maybe upgrades)
 * @author Sean Reitsma
 */
public enum Rarity {
    COMMON("Common",10,20,1500,3000, List.of(ResourceType.BCO_QUICHE,ResourceType.COQ_A_VIN)),
    UNCOMMON("Uncommon",20, 40, 800, 1400, List.of(ResourceType.BCO_QUICHE, ResourceType.COQ_A_VIN, ResourceType.NICOISE_SALAD)),
    RARE("Rare",40, 80, 300, 1000, List.of(ResourceType.GRUYER_CHEESE_SOUFFLE, ResourceType.BOUILLABAISSE)),
    EPIC("Epic",90, 110, 100, 250, List.of(ResourceType.CREME_BRULEE)),
    LEGENDARY("Legendary",150, 200, 1, 100, List.of(ResourceType.RATATOUILLE));
    private final int resourceAmountMin;
    private final int resourceAmountMax;
    private final long speedMin;
    private final long speedMax;
    private final List<ResourceType> types;
    private final String description;

    /**
     * Constructor
     * @param description The description of the rarity
     * @param resourceAmountMin Minimum resource amount
     * @param resourceAmountMax Maximum resource amount
     * @param speedMin Minimum speed (max duration)
     * @param speedMax Maximum speed (min duration)
     * @param types A list of possible {@link ResourceType}'s
     */
    Rarity(String description, int resourceAmountMin, int resourceAmountMax, long speedMin, long speedMax, List<ResourceType> types) {
        this.description = description;
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
        if (roundNumber <= (double) 1/3 * maxRoundNumber) {
            return VALUES.get(RANDOM.nextInt(0, 2));
        } else if (roundNumber <= (double) 2/3 * maxRoundNumber) {
            return VALUES.get(RANDOM.nextInt(0,3));
        } else {
            return VALUES.get(RANDOM.nextInt(1,SIZE));
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

    public String getDescription() {
        return description;
    }
}
