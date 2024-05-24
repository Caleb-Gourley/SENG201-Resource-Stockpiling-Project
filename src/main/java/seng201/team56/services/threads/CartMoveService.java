package seng201.team56.services.threads;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import seng201.team56.models.Cart;
import seng201.team56.models.Round;
import seng201.team56.services.RoundService;

import java.util.List;

public class CartMoveService extends Service<Boolean> {
    private Cart cart;
    private RoundService roundService;
    public CartMoveService(Cart cart, RoundService roundService) {
        this.cart = cart;
        this.roundService = roundService;
    }
    @Override
    protected Task<Boolean> createTask() {
        return new CartMoveTask(cart,roundService);
    }
}
