package seng201.team56.models;

import java.util.Random;

/**
 * A class to represent round difficulties. This can be used to populate a round with randomised carts.
 */
public class RoundDifficulty {
    private double trackDistance;
    private int numCarts;
    private int cartMinSize;
    private int cartMaxSize;
    private double cartMinSpeed;
    private double cartMaxSpeed;
    private int monetaryReward;
    private int xpReward;

    /**
     * Constructs a round difficulty.
     * @param trackDistance the track distance for the round
     * @param numCarts the number of carts to include in the round
     * @param cartMinSize the minimum resourceAmount of any cart
     * @param cartMaxSize the maximum resourceAmount of any cart
     * @param cartMinSpeed the minimum speed of any cart
     * @param cartMaxSpeed the maximum speed of any cart
     * @param monetaryReward the amount of money the player gets if he/she wins a round
     * @param xpReward the amount of xp applied to each tower used in the round
     */
    public RoundDifficulty(double trackDistance, int numCarts, int cartMinSize, int cartMaxSize, double cartMinSpeed, double cartMaxSpeed, int monetaryReward, int xpReward) {
        this.trackDistance = trackDistance;
        this.numCarts = numCarts;
        this.cartMinSize = cartMinSize;
        this.cartMaxSize = cartMaxSize;
        this.cartMinSpeed = cartMinSpeed;
        this.cartMaxSpeed = cartMaxSpeed;
        this.monetaryReward = monetaryReward;
        this.xpReward = xpReward;
    }

    /**
     * Constructs a randomised round difficulty based on the round number and a given rarity. The rarity should be the
     * average tower rarity the player is expected to have in a given point in the game. Later rounds are harder.
     * @param rng a random number generator
     * @param roundNum the current round number
     * @param rarity the rarity we assume most of the player's towers are
     */
    public RoundDifficulty(Random rng, int roundNum, Rarity rarity) {
        int minNumCarts = 5 + 2 * (roundNum - 1);
        int maxNumCarts = minNumCarts + 2;
        numCarts = rng.nextInt(minNumCarts,maxNumCarts);
        int minSize = 15 + 5 * (roundNum - 1);
        int maxSize = minSize + 5;
        cartMinSize = rng.nextInt(minSize,maxSize);
        cartMaxSize = cartMinSize + 5;
        double minSpeed = 5 + 3 * (roundNum - 1);
        double maxSpeed = minSpeed + 3;
        cartMinSpeed = rng.nextDouble(minSpeed,maxSpeed);
        cartMaxSpeed = cartMinSpeed + 3;
        double minTrackDistance = (double) cartMinSize / rarity.getResourceAmountMin() * numCarts / 5 * rarity.getSpeedMax() * cartMinSpeed;
        double maxTrackDistance = (double) cartMaxSize / rarity.getResourceAmountMin() * numCarts / 5 * rarity.getSpeedMax() * cartMaxSpeed;
        trackDistance = (minTrackDistance + maxTrackDistance) / 2;
        int minMoney = 10 + 7 * (roundNum - 1);
        int maxMoney = minMoney + 7;
        monetaryReward = rng.nextInt(minMoney,maxMoney);
        xpReward = rng.nextInt(20,60);
    }

    /**
     * Getter for trackDistance.
     * @return trackDistance
     */
    public double trackDistance() {
        return trackDistance;
    }

    /**
     * Getter for numCarts.
     * @return numCarts
     */
    public int numCarts() {
        return numCarts;
    }

    /**
     * Getter for cartMinSize.
     * @return cartMinSize
     */
    public int cartMinSize() {
        return cartMinSize;
    }

    /**
     * Getter for cartMaxSize.
     * @return cartMaxSize
     */
    public int cartMaxSize() {
        return cartMaxSize;
    }

    /**
     * Getter for cartMinSpeed.
     * @return cartMinSpeed
     */
    public double cartMinSpeed() {
        return cartMinSpeed;
    }

    /**
     * Getter for cartMaxSpeed,
     * @return cartMaxSpeed
     */
    public double cartMaxSpeed() {
        return cartMaxSpeed;
    }

    /**
     * Getter for monetaryReward.
     * @return monetaryReward
     */
    public int monetaryReward() {
        return monetaryReward;
    }

    /**
     * Getter for xpReward
     * @return xpReward
     */
    public int xpReward() {
        return xpReward;
    }

    /**
     * String representation of the RoundDifficulty
     * @return a String giving a textual description of all the aspects of this RoundDifficulty
     */
    @Override
    public String toString() {
        return String.format("""
                Track distance: %.2f
                Number of carts: %d
                Min cart size: %d
                Max cart size: %d
                Min cart speed: %.2f
                Max cart speed: %.2f
                Monetary reward: %d
                Tower xp reward: %d""", trackDistance,numCarts,cartMinSize,cartMaxSize,cartMinSpeed,cartMaxSpeed,monetaryReward,xpReward);
    }
}
