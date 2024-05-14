package seng201.team56.services;

import seng201.team56.models.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A service to handle the shop items
 */
public class ShopService {
    private final Player player;
    private ArrayList<Purchasable> items;

    /**
     * Constructor
     * @param player the player (current game state)
     */
    public ShopService(Player player) {
        this.player = player;
    }

    /**
     * Clears and refreshes the towers according to roundNumber.
     * @param roundNumber the number of the round the game is up to
     */
    public void updateItems(int roundNumber) {
        items.clear();
        Random rng = new Random();
        int numTowers = rng.nextInt(3,6);
        for (int i = 0; i < numTowers; i++) {
            Rarity rarity = Rarity.pickRarity(roundNumber, player.getMaxRounds());
            Tower tower = new Tower(rarity);
            items.add(tower);
        }


    }

    /**
     * Buy an item and add it to the players inventory.
     * Fails if the player does not have enough money.
     * @param index the index of the item in the items {@link ArrayList} to buy
     */
    public void buyItem(int index) {
        Purchasable item = items.get(index);
        if (player.spendMoney(item.getBuyPrice())) {
            items.remove(index);
            player.addItem(item);
        }
    }

    /**
     * Sell an item and pay the player
     * @param item the {@link Purchasable} to sell
     */
    public void sellItem(Purchasable item) {
        items.add(item);
        player.addMoney(item.getSellPrice());
    }

    /**
     * Getter for the list of items in the shop
     * @return items
     */
    public ArrayList<Purchasable> getItems() {
        return items;
    }
}
