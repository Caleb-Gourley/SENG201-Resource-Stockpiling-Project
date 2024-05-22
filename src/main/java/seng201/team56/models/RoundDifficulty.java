package seng201.team56.models;

import java.util.Random;

public class RoundDifficulty {
    private double trackDistance;
    private int numCarts;
    private int cartMinSize;
    private int cartMaxSize;
    private double cartMinSpeed;
    private double cartMaxSpeed;
    private int monetaryReward;
    private int xpReward;

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

    public double trackDistance() {
        return trackDistance;
    }

    public int numCarts() {
        return numCarts;
    }

    public int cartMinSize() {
        return cartMinSize;
    }

    public int cartMaxSize() {
        return cartMaxSize;
    }

    public double cartMinSpeed() {
        return cartMinSpeed;
    }

    public double cartMaxSpeed() {
        return cartMaxSpeed;
    }

    public int monetaryReward() {
        return monetaryReward;
    }

    public int xpReward() {
        return xpReward;
    }

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
