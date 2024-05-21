package seng201.team56.services;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Optional;
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
	private boolean roundRunning = false;
	private boolean roundWon = false;
	private boolean roundLost = false;
	private final Random rng;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 * Constructor.
	 * Initialises roundNum to 0.
	 * @param player the current {@link Player} (game state) object
	 */
	public RoundService(Player player, ShopService shopService) {
		this.rng = new Random();
		this.player = player;
		this.roundNum = 1;
		this.shopService = shopService;
    }

	public void setRoundDifficulty(RoundDifficulty difficulty) {
		this.roundDifficulty = difficulty;
	}

	public void addRunningSubscriber(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener("roundRunning", listener);
	}

	/**
	 * Creates a round with the given (player selected) difficulty information.
	 */
	//Should this just be in the round constructor?
	public void createRound() {
		this.pool = Executors.newScheduledThreadPool(roundDifficulty.numCarts() + player.getInventory().getFieldTowers().size());
		this.currentRound = new Round(roundDifficulty.trackDistance(), roundNum);
		for (int i = 0; i < roundDifficulty.numCarts(); i++) {
			int size = rng.nextInt(roundDifficulty.cartMinSize(),roundDifficulty.cartMaxSize());
			double speed = rng.nextDouble(roundDifficulty.cartMinSpeed(),roundDifficulty.cartMaxSpeed());
			ResourceType type = Rarity.pickRarity(roundNum, player.getMaxRounds()).getRandomType();
			Cart cart = new Cart(speed, size, type, currentRound.getTrackDistance());
			for (Tower tower: player.getInventory().getFieldTowers()) {
				cart.addTowerDistanceListener(tower);
			}
			currentRound.addCart(cart);
		}
	}

	/**
	 * Play a round. Adds {@link CartMoveTask} tasks to the pool.
	 */
	public void playRound() {
		// Check that the round has been created first
		if (currentRound != null && !roundRunning) {
			// Begin moving all carts
			for (Cart cart : currentRound.getCarts()) {
				CartMoveTask task = new CartMoveTask(cart, pool, this);
				pool.schedule(task, 0, TimeUnit.SECONDS);
			}
			for (Tower tower: player.getInventory().getFieldTowers()) {
				pool.scheduleAtFixedRate(tower::reload, 0, tower.getReloadSpeed(), TimeUnit.MILLISECONDS);
			}
			boolean oldValue = roundRunning;
			roundRunning = true;
			pcs.firePropertyChange("roundRunning", oldValue, true);
		}
	}

	/**
	 * Getter for the current round
	 * @return currentRound
	 */
	public Round getCurrentRound() {
		return currentRound;
	}

	public int getRoundNum() {
		return roundNum;
	}

	/**
	 * Getter for roundRunning
	 * @return true if a round is currently active, false otherwise
	 */
	public boolean isRoundRunning() {
		return roundRunning;
	}

	public boolean isRoundWon() {
		return roundWon;
	}

	public boolean isRoundLost() {
		return roundLost;
	}

	public List<RandomEvent> getRandomEvents(Tower tower) {
		int randInt = rng.nextInt(5,5 + roundNum * 2);
		long randLong = rng.nextLong(50, roundNum * 200);
		RandomEvent towerCapacityIncrease = new RandomEvent(roundNum, () -> tower.increaseResourceFullAmount(randInt),player.getDifficulty());
		RandomEvent towerCapacityDecrease = new RandomEvent(roundNum, () -> tower.decreaseResourceFullAmount(randInt),player.getDifficulty());
		RandomEvent towerReloadSpeedIncrease = new RandomEvent(roundNum, () -> tower.decreaseReloadInterval(randLong),player.getDifficulty());
		RandomEvent towerReloadSpeedDecrease = new RandomEvent(roundNum, () -> tower.increaseReloadInvterval(randLong),player.getDifficulty());
		RandomEvent towerBreaks = new RandomEvent(roundNum,tower::setBroken,player.getDifficulty(),tower.getUseCount());
		RandomEvent nothingHappens = new RandomEvent(() -> {}, 10);
        return List.of(towerCapacityIncrease, towerCapacityDecrease, towerReloadSpeedIncrease, towerReloadSpeedDecrease, towerBreaks, nothingHappens);
	}

	/**
	 * Executes a random event from a weighted selection of random events.
 	 */
	public void randomEvent(List<RandomEvent> events) {
		int total = 0;
		for (RandomEvent event : events) {
			total += event.getWeight();
			event.setWeight(total);
		}
		int value = rng.nextInt(total + 1);
		events.stream().filter(randomEvent -> randomEvent.getWeight() >= value).findFirst().ifPresent(RandomEvent::act);
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
		roundWon = currentRound.getCarts().stream().allMatch(cart -> cart.isDone() && cart.isFull());
		roundLost = currentRound.getCarts().stream().anyMatch(cart -> cart.isDone() && !cart.isFull());
		if (roundWon || roundLost) {
			boolean oldValue = roundRunning;
			roundRunning = false;
			this.pcs.firePropertyChange("roundRunning", oldValue, false);
			pool.shutdown();
			shopService.updateItems(roundNum);
			player.getInventory().incFieldTowers();
			int i = rng.nextInt(player.getInventory().getFieldTowers().size());
			Tower tower = player.getInventory().getFieldTowers().get(i);
			List<RandomEvent> events = getRandomEvents(tower);
			randomEvent(events);
			if (roundWon) {
				roundNum++;
				player.addMoney(roundDifficulty.monetaryReward());
				for (Tower t: player.getInventory().getFieldTowers()) {
					t.addXp(roundDifficulty.xpReward());
				}
			}
		}
	}


}
