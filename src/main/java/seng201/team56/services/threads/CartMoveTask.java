package seng201.team56.services.threads;

import seng201.team56.models.Cart;
import seng201.team56.services.RoundService;
import seng201.team56.services.SetupService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A task which moves the cart once a second while the cart is still on the track.
 */
public class CartMoveTask implements Runnable {
    private final Cart cart;
    private final ScheduledExecutorService scheduler;
    private final RoundService roundService;

    /**
     * Construct a new CartMoveTask.
     * @param cart the {@link Cart} to move
     * @param scheduler the {@link ScheduledExecutorService} which schedules the task
     * @param roundService the {@link RoundService} object which instantiated the task
     */
    public CartMoveTask(Cart cart, ScheduledExecutorService scheduler, RoundService roundService) {
        this.cart = cart;
        this.scheduler = scheduler;
        this.roundService = roundService;
    }

    /**
     * Move the cart once a second while cart.isDone() returns false. If the cart is done, calls {@link RoundService#endRound()}.
     */
    @Override
    public void run() {
        // Continue to schedule this task as long as the cart has not reached the end of the track.
        if (!cart.isDone()) {
            cart.move();
            scheduler.schedule(this, 1, TimeUnit.SECONDS);
        } else {
            //roundService.endRound() handles checking whether the round should in fact end
            roundService.endRound();
        }
    }
}
