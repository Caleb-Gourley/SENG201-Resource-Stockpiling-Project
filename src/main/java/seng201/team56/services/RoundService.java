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
	private RoundDifficulty roundDifficulty;
	
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

	public void setRoundDifficulty(RoundDifficulty difficulty) {
		this.roundDifficulty = difficulty;
	}

	/**
	 * Creates a round with the given (player selected) difficulty information.
	 */
	//Should this just be in the round constructor?
	public void createRound() {
		Random rng = new Random();
		this.currentRound = new Round(roundDifficulty.trackDistance(), roundNum);
		for (int i = 1; i < roundDifficulty.numCarts(); i++) {
			int size = rng.nextInt(roundDifficulty.cartMinSize(),roundDifficulty.cartMaxSize());
			float speed = rng.nextFloat(roundDifficulty.cartMinSpeed(),roundDifficulty.cartMaxSpeed());
			ResourceType type = Rarity.pickRarity(roundNum, player.getMaxRounds()).getRandomType();
			Cart cart = new Cart(speed, size, type);
			currentRound.addCart(cart);
		}
	}

	public void playRound() {

	}
	

}
