package seng201.team56.services.threads;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import seng201.team56.models.Cart;
import seng201.team56.services.RoundService;

/**
 * A javafx Service which moves carts around the track.
 * @author Sean Reitsma
 */
public class CartMoveService extends Service<Boolean> {
    private final Cart cart;
    private final RoundService roundService;

    /**
     * Constructs a CartMoveService.
     * @param cart the cart to move
     * @param roundService the roundService object which instantiated the service
     */
    public CartMoveService(Cart cart, RoundService roundService) {
        this.cart = cart;
        this.roundService = roundService;
    }

    /**
     * Creates a CartMoveTask to run.
     * @return a new {@link CartMoveTask}
     */
    @Override
    protected Task<Boolean> createTask() {
        return new CartMoveTask(cart,roundService);
    }
}
