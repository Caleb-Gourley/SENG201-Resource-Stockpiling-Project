package seng201.team56.services;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import seng201.team56.models.*;
import seng201.team56.services.threads.CartMoveTask;
import java.util.concurrent.TimeUnit;

/**
 * A service to handle creation and running of rounds.
 * 
 * @author Sean Reitsma
 */
public class RoundService {
	private Round currentRound;
	private int roundNum;
	private final Player player;
	private ScheduledExecutorService pool;
	private final ShopService shopService;
    private RoundDifficulty roundDifficulty;
	
	/**
	 * Constructor.
	 * Initialises roundNum to 0.
	 * @param player the current {@link Player} (game state) object
	 */
	public RoundService(Player player, ShopService shopService) {
		this.player = player;
		this.roundNum = 0;
		this.currentRound = null;
		this.shopService = shopService;
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
			double speed = rng.nextDouble(roundDifficulty.cartMinSpeed(),roundDifficulty.cartMaxSpeed());
			ResourceType type = Rarity.pickRarity(roundNum, player.getMaxRounds()).getRandomType();
			Cart cart = new Cart(speed, size, type, roundDifficulty.trackDistance());
			for (Tower tower: player.getInventory().getFieldTowers()) {
				cart.addPropertyChangeListener(tower);
			}
			currentRound.addCart(cart);
		}
	}

	/**
	 * Play a round. Adds {@link CartMoveTask} tasks to the pool.
	 */
	public void playRound() {
		// Check that the round has been created first
		if (currentRound != null) {
			// Begin moving all carts
			for (Cart cart : currentRound.getCarts()) {
				CartMoveTask task = new CartMoveTask(cart, pool, this);
				pool.schedule(task, 0, TimeUnit.SECONDS);
			}
		}
	}

	public Round getCurrentRound() {
		return currentRound;
	}

	/**
	 * Checks conditions for the end of the round and shuts the pool down if any of them are met.
	 * Conditions are either:
	 * <ul>
	 *     <li>All the carts are both done and full (if this is the case the round is complete).</li>
	 *     <li>Any cart is done but not full (if this is the case the round is lost).</li>
	 * </ul>
	 */
	public void endRound() {
		if (currentRound.getCarts().stream().allMatch(cart -> cart.isDone() && cart.isFull())) {
			//Round completed!
			pool.shutdown();
			roundNum++;
			shopService.updateItems(roundNum);
			//TODO: choose difficulty for next round
		} else if (currentRound.getCarts().stream().anyMatch(cart -> cart.isDone() && !cart.isFull())) {
			//Round lost!
			pool.shutdown();
		}
	}
}
