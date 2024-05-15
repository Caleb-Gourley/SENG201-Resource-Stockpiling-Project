package seng201.team56.models;

import java.util.ArrayList;

/**
 * Represents a round.
 * @author Caleb Gourley
 */
public class Round {

    private ArrayList<Cart> carts = new ArrayList<>();
    private double trackDistance;
    private int roundNumber;

    /**
     * Constructor
     * Sets the initial trackDistance and roundNumber to 0
     * @param trackDistance the trackDistance for each round
     * @param roundNumber the roundNumber keeps track of each round
     */
    public Round(double trackDistance, int roundNumber) {
        this.trackDistance = trackDistance;
        this.roundNumber = roundNumber;
    }

    /**
     * Getter for carts
     * @return carts
     */
    public ArrayList<Cart> getCarts() { return carts; }

    /**
     * Adds Cart objects to carts ArrayList for that round
     * @param cart the cart object to be added to the ArrayList
     */
    public void addCart(Cart cart) { carts.add(cart); }

    /**
     * Remove Cart object from ArrayList when cart is full
     */
    public void removeCart() { carts.remove(-1); }

    public double getTrackDistance() { return trackDistance; }
}
