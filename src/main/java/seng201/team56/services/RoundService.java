package seng201.team56.services;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import seng201.team56.models.*;
import seng201.team56.services.threads.CartMoveTask;
import seng201.team56.services.util.RandomEvent;

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
	private final Random rng;
	
	/**
	 * Constructor.
	 * Initialises roundNum to 0.
	 * @param player the current {@link Player} (game state) object
	 */
	public RoundService(Player player, ShopService shopService) {
		this.rng = new Random();
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
		this.pool = Executors.newScheduledThreadPool(roundDifficulty.numCarts());
		this.currentRound = new Round(roundDifficulty.trackDistance(), roundNum);
		for (int i = 0; i < roundDifficulty.numCarts(); i++) {
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

	private void randomEvent() {
		int i = rng.nextInt(player.getInventory().getFieldTowers().size());
		Tower tower = player.getInventory().getFieldTowers().get(i);
		int randInt = rng.nextInt(1,roundNum * 2);
		double randDouble = rng.nextDouble(0.05, roundNum * 0.2);
		RandomEvent towerCapacityIncrease = new RandomEvent(roundNum, () -> tower.increaseResourceFullAmount(randInt),player.getDifficulty());
		RandomEvent towerCapacityDecrease = new RandomEvent(roundNum, () -> tower.decreaseResourceFullAmount(randInt),player.getDifficulty());
		RandomEvent towerReloadSpeedIncrease = new RandomEvent(roundNum, () -> tower.decreaseReloadInterval(randDouble),player.getDifficulty());
		RandomEvent towerReloadSpeedDecrease = new RandomEvent(roundNum, () -> tower.increaseReloadInvterval(randDouble),player.getDifficulty());
		RandomEvent towerBreaks = new RandomEvent(roundNum,tower::setBroken,player.getDifficulty(),tower.getUseCount());
		RandomEvent nothingHappens = new RandomEvent(() -> {}, 0.7);
		List<RandomEvent> events = List.of(towerCapacityIncrease, towerCapacityDecrease, towerReloadSpeedIncrease, towerReloadSpeedDecrease, towerBreaks, nothingHappens);
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
		boolean roundWon = currentRound.getCarts().stream().allMatch(cart -> cart.isDone() && cart.isFull());
		boolean roundLost = currentRound.getCarts().stream().anyMatch(cart -> cart.isDone() && !cart.isFull());
		if (roundWon || roundLost) {
			pool.shutdown();
			shopService.updateItems(roundNum);
			player.getInventory().incFieldTowers();
			if (roundWon) {
				roundNum++;
				player.addMoney(roundDifficulty.monetaryReward());
				player.addXp(roundDifficulty.xpReward());
				randomEvent();
			} else {
            }
		}
	}
}
