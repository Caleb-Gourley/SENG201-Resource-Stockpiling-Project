package seng201.team56.models;

public record RoundDifficulty(double trackDistance, int numCarts, int cartMinSize, int cartMaxSize, double cartMinSpeed,
                              double cartMaxSpeed, int monetaryReward, int xpReward) {}
