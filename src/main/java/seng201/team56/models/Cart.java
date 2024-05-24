package seng201.team56.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Represents an in-game cart.
 * @author Sean Reitsma
 */
public class Cart {
    private double speed;
    private int size;
    private double distance;
    private double trackDistance;
    private ResourceType resourceType;
    private int resourceAmount;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    /**
     * Constructor.
     * Sets the initial resourceAmount to 0.
     * @param speed the speed the cart goes around the track (m/s)
     * @param size the size of the cart
     * @param resourceType the ResourceType the cart can hold
     * @param trackDistance the distance of the track which the cart needs to complete
     */
    public Cart(double speed, int size, ResourceType resourceType, double trackDistance) {
        this.speed = speed;
        this.size = size;
        this.resourceType = resourceType;
        this.resourceAmount = 0;
        this.trackDistance = trackDistance;
    }

    /**
     * Getter for resourceAmount
     * @return resourceAmount
     */
    public int getResourceAmount() {
        return resourceAmount;
    }

    /**
     * Getter for resourceType
     * @return resourceType
     */
    public ResourceType getResourceType() {
        return resourceType;
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
     * Convenience method which returns true if the cart has reached the end of the track
     * @return distance >= trackDistance
     */
    public boolean isDone() {
        return distance >= trackDistance;
    }

    /**
     * Fill the cart up with amount
     * @param amount the amount to add to the cart
     */
    public void fillAmount(int amount) {
        if (resourceAmount + amount > size) {
            resourceAmount += size - resourceAmount;
        } else {
            resourceAmount += amount;
        }
    }


    /**
     * Move the cart by speed metres. That is, this method equates to 1 second of movement.
     * Fires a {@link PropertyChangeSupport#firePropertyChange(String, Object, Object)} to notify listeners that the cart has moved.
     */
    public synchronized void move() {
        double oldValue = distance;
        distance += speed;
        pcs.firePropertyChange("distance", oldValue, distance);
    }

    public void addDistanceListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }



}
