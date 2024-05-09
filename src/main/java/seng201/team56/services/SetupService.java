package seng201.team56.services;

import seng201.team56.models.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A service to initialise the game setup
 * @author Sean Reitsma
 */
public class SetupService {
    private final Player player;

    /**
     * Constructor
     * Initialises the player.
     * @throws IllegalArgumentException if startTowers does not have exactly 3 towers.
     * @param name the player's chosen name.
     * @param difficulty the player's chosen difficulty.
     * @param startTowers an ArrayList of the player's chosen starting towers.
     */
    public SetupService(String name, Difficulty difficulty, ArrayList<Tower> startTowers) throws IllegalArgumentException{
        Inventory inventory = new Inventory();
        if (startTowers.size() == 3) {
            for (Tower tower : startTowers) {
                inventory.addTower(tower);
            }
        } else {
            throw new IllegalArgumentException("startTowers must contain 3 elements");
        }
        this.player = new Player(name,difficulty,inventory);
    }

    /**
     * Generates a list of starting tower options. The stats of these towers is somewhat randomised.
     * @return a list of six towers to choose from.
     */
    public static ArrayList<Tower> getTowersToChoose() {
        Random rng = new Random();
        ArrayList<Tower> towers = new ArrayList<Tower>(6);
        for (int i = 0; i < 6; i++) {
            ResourceType type = ResourceType.randomResourceType();
            //TODO Tweak random values to make sure these numbers will make for a balanced game.
            int amount = rng.nextInt(5,30);
            float speed = rng.nextFloat(1.5f,3);
            //Starting towers are cheap as chips
            double cost = 0.5;
            Tower tower = new Tower(type, amount, speed, cost);
            towers.add(tower);
        }
        return towers;
    }


}
