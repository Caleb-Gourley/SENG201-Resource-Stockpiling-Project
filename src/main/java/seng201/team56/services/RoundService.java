package seng201.team56.services;

import java.util.Random;

import seng201.team56.models.*;

/**
 * A service to handle creation and running of rounds.
 * 
 * @author Sean Reitsma
 */
public class RoundService {
	private Round currentRound;
	private int roundNum;
	private Player player;
	
	/**
	 * Constructor.
	 * Initialises roundNum to 0.
	 * @param player the current {@link Player} (game state) object
	 */
	public RoundService(Player player) {
		this.player = player;
		this.roundNum = 0;
		this.currentRound = null;
	}

	/**
	 * Creates a round with the given (player selected) difficulty information.
	 * @param trackDistance the distance of the track for the round
	 * @param numCarts the number of carts in the round
	 * @param cartMinSize the minimum size of any cart in the round
	 * @param cartMaxSize the maximum size of any cart in the round
	 * @param cartMinSpeed the minimum speed of any cart in the round
	 * @param cartMaxSpeed the maximum speed of any cart in the round
	 */
	public void createRound(double trackDistance, int numCarts, int cartMinSize, int cartMaxSize, float cartMinSpeed,
							float cartMaxSpeed) {
		Random rng = new Random();
		this.currentRound = new Round(trackDistance, roundNum);
		for (int i = 1; i < numCarts; i++) {
			int size = rng.nextInt(cartMinSize,cartMaxSize);
			float speed = rng.nextFloat(cartMinSpeed,cartMaxSpeed);
			ResourceType type = Rarity.pickRarity(roundNum, player.getMaxRounds()).getRandomType();
			Cart cart = new Cart(speed, size, type);
			currentRound.addCart(cart);
		}
	}
	

}
