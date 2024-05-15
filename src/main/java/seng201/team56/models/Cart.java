package seng201.team56.models;

/**
 * Represents an in-game cart
 * @author Sean Reitsma
 */
public class Cart {
    private float speed;
    private int size;
    private double distance;
    private ResourceType resourceType;
    private int resourceAmount;

    /**
     * Constructor
     * Sets the initial resourceAmount to 0
     * @param speed the speed the cart goes around the track (m/s)
     * @param size the size of the cart
     * @param resourceType the ResourceType the cart can hold
     */
    public Cart(float speed, int size, ResourceType resourceType) {
        this.speed = speed;
        this.size = size;
        this.resourceType = resourceType;
        this.resourceAmount = 0;
    }

    /**
     * Getter for resourceAmount
     * @return resourceAmount
     */
    public int getResourceAmount() {
        return resourceAmount;
    }

    /**
     * Getter for size
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for distance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Convenience method which returns true if the cart is full, false otherwise
     * @return resourceAmount == size
     */
    public boolean isFull() {
        return resourceAmount == size;
    }

    /**
     * Fill the cart up with amount
     * @param amount the amount to add to the cart
     * @return the leftover amount after filling the cart
     */
    public int fillAmount(int amount) {
        int leftOver = 0;
        if (resourceAmount + amount > size) {
            leftOver = amount - (size - resourceAmount);
            resourceAmount += size - resourceAmount;
        } else {
            resourceAmount += amount;
        }
        return leftOver;
    }

    /**
     * Move the cart by speed metres. That is, this method equates to 1 second of movement.
     */
    public void move() {
        distance += speed;
    }



}
