package seng201.team56.services;

import seng201.team56.models.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A service to initialise the game setup. The main role of this service is simply to create a player object once the
 * user has submitted all the required info on the setup screen. Also provides a static method getTowersToChoose()
 * to generate a list of 6 random towers.
 * @author Sean Reitsma
 */
public class SetupService {
    private final Player player;

    /**
     * Constructor
     * Initialises the player.
     *
     * @param name the player's chosen name.
     * @param difficulty the player's chosen difficulty.
     * @param startTowers an ArrayList of the player's chosen starting towers.
     */
    public SetupService(String name, Difficulty difficulty, ArrayList<Tower> startTowers, int maxRoundNum) {
        Inventory inventory = new Inventory(startTowers);
        this.player = new Player(name,difficulty,inventory, maxRoundNum);
    }

    /**
     * Get the created player object to be used by other Services
     * @return player
     */
    public Player getPlayer() {
        return player;
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
            int cost = 1;
            Tower tower = new Tower(type, amount, speed, cost);
            towers.add(tower);
        }
        return towers;
    }


}
