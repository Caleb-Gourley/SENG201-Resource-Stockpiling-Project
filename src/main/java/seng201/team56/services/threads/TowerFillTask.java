package seng201.team56.services.threads;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import seng201.team56.models.Cart;
import seng201.team56.models.Tower;

import java.util.List;

/**
 * A wrapper task for {@link Tower#fillCarts(List)}
 *
 * @author Sean Reitsma
 */
public class TowerFillTask extends Service<Boolean> {
    private final List<Cart> carts;
    private final Tower tower;

    /**
     * Construct a TowerFIllTask.
     * @param carts the list of carts to fill
     * @param tower the Tower which will do the filling
     */
    public TowerFillTask(List<Cart> carts, Tower tower) {
        this.carts = carts;
        this.tower = tower;
    }

    /**
     * Runs {@link Tower#fillCarts(List)}.
     */
    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                while (!(carts.stream().allMatch(cart -> cart.isDone() && cart.isFull()) || carts.stream().anyMatch(cart -> cart.isDone() && !cart.isFull()))) {
                    tower.fillCarts(carts);
                    Thread.sleep(tower.getReloadSpeed());

                }
                return true;
            }
        };
    }
}
