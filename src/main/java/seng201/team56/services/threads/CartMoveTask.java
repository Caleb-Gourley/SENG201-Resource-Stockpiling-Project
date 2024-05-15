package seng201.team56.services.threads;

import seng201.team56.models.Cart;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CartMoveTask implements Runnable {
    private final Cart cart;
    private final double trackDistance;
    private final ScheduledExecutorService scheduler;

    public CartMoveTask(Cart cart, double trackDistance, ScheduledExecutorService scheduler) {
        this.cart = cart;
        this.trackDistance = trackDistance;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        // Continue to schedule this task as long as the cart has not reached the end of the track.
        if (cart.getDistance() < trackDistance) {
            cart.move();
            scheduler.schedule(this, 1, TimeUnit.SECONDS);
        }
    }
}
