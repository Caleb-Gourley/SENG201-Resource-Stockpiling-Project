package seng201.team56.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng201.team56.models.*;
import seng201.team56.models.upgrades.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A service to handle the shop items
 */
public class ShopService {
    private final Player player;
    private final ObservableList<Purchasable> items;

    /**
     * Constructor
     * @param player the player (current game state)
     */
    public ShopService(Player player) {
        this.player = player;
        this.items = FXCollections.observableArrayList();
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
        int numUpgrades = rng.nextInt(5,8);
        for (int i = 0; i < numUpgrades; i++) {
            int typeSelection = rng.nextInt(4);
            Rarity rarity = Rarity.pickRarity(roundNumber, player.getMaxRounds());
            int cost = rng.nextInt(rarity.getCostMin() - 5, rarity.getCostMax() - 5);
            Upgrade<?> upgrade = null;
            switch (typeSelection) {
                case 0 -> upgrade = new RarityUpgrade(cost);
                case 1 -> {
                    long speed = rng.nextLong(20,300);
                    upgrade = new SpeedUpgrade(cost, speed);
                }
                case 2 -> {
                    int resourceAmount = rng.nextInt(5,40);
                    upgrade = new CapacityUpgrade(cost, resourceAmount);
                }
                case 3 -> {
                    upgrade = new AnyResourceUpgrade(cost);
                }
            }
            items.add(upgrade);
        }


    }

    /**
     * Buy an item and add it to the players inventory.
     * Fails if the player does not have enough money.
     * @param index the index of the item in the items {@link ArrayList} to buy
     * @return true if the purchase succeeded, false if the player doesn't have enough money
     */
    public boolean buyItem(int index) {
        Purchasable item = items.get(index);
        if (player.spendMoney(item.getBuyPrice())) {
            player.addItem(item);
            items.remove(index);
            return true;
        } else {
            return false;
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
    public ObservableList<Purchasable> getItems() {
        return items;
    }
}
