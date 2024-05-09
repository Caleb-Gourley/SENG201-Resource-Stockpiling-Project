package seng201.team56.services;

import seng201.team56.models.*;

import java.util.ArrayList;
import java.util.Random;

public class ShopService {
    private final Player player;
    private ArrayList<Purchasable> items;
    //private ArrayList<Tower> towers;
    //private ArrayList<Upgrade> upgrades;

    public ShopService(Player player) {
        this.player = player;
    }

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

    public Purchasable buyItem(int index) {
        Purchasable item = items.get(index);
        if (player.spendMoney(item.getBuyPrice())) {
            items.remove(index);
            player.addItem(item);
        }
        return item;
    }

    public void sellItem(Purchasable item) {
        items.add(item);
    }

    public ArrayList<Purchasable> getItems() {
        return items;
    }
}
