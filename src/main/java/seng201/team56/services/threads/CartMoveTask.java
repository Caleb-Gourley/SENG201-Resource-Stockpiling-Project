package seng201.team56.services.threads;

import javafx.concurrent.Task;
import seng201.team56.models.Cart;
import seng201.team56.services.RoundService;

/**
 * A task which moves the cart once a second while the cart is still on the track.
 */
public class CartMoveTask extends Task<Boolean> {
    private final Cart cart;
    private final RoundService roundService;

    /**
     * Construct a new CartMoveTask.
     * @param cart the {@link Cart} to move
     * @param roundService the {@link RoundService} object which instantiated the task
     */
    public CartMoveTask(Cart cart, RoundService roundService) {
        this.cart = cart;
        this.roundService = roundService;
    }

    /**
     * Move the cart once a second while cart.isDone() returns false. If the cart is done, calls {@link RoundService#endRound()}.
     */
    @Override
    protected Boolean call() throws Exception {
        // Continue to schedule this task as long as the cart has not reached the end of the track.
        while (!cart.isDone()) {
            cart.move();
            Thread.sleep(1000);
        }
        roundService.endRound();
        return true;



    }
}
