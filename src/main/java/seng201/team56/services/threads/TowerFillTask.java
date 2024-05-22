package seng201.team56.services.threads;

import seng201.team56.models.Cart;
import seng201.team56.models.Tower;

import java.util.List;

public class TowerFillTask implements Runnable{
    private List<Cart> carts;
    private Tower tower;
    public TowerFillTask(List<Cart> carts, Tower tower) {
        this.carts = carts;
        this.tower = tower;
    }
    @Override
    public void run() {
        tower.fillCarts(carts);
    }
}
