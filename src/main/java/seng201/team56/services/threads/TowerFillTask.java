package seng201.team56.services.threads;

import seng201.team56.models.Cart;
import seng201.team56.models.Tower;

import java.util.List;

/**
 * A wrapper task for {@link Tower#fillCarts(List)}
 *
 * @author Sean Reitsma
 */
public class TowerFillTask implements Runnable{
    private List<Cart> carts;
    private Tower tower;

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
    public void run() {
        tower.fillCarts(carts);
    }
}
