package seng201.team56.services;

import java.util.Random;

import seng201.team56.models.Player;
import seng201.team56.models.Round;

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
	

}
