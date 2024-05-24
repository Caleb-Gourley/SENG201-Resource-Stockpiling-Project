package seng201.team56;

import seng201.team56.models.Player;

import java.util.function.Consumer;

/**
 *Manages scenes and gameplay
 * @author Caleb Gourley
 */
public class GameEnvironment {
    private final Consumer<GameEnvironment> setupScreenLauncher;
    private final Consumer<GameEnvironment> mainScreenLauncher;
    private final Runnable clearScreen;
    private final Runnable quitGame;
    private Player player;

    /**
     * Constructor
     * Initialises setupScreenLauncher, mainScreenLauncher, clearScreen
     * Launches the setup screen
     * @param setupScreenLauncher Consumer
     * @param mainScreenLauncher Consumer
     * @param clearScreen Consumer
     * @param quitGame Runnable
     */
    public GameEnvironment(Consumer<GameEnvironment> setupScreenLauncher, Consumer<GameEnvironment> mainScreenLauncher, Runnable clearScreen, Runnable quitGame) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.mainScreenLauncher = mainScreenLauncher;
        this.clearScreen = clearScreen;
        this.quitGame = quitGame;
        launchSetupScreen();
    }

    /**
     * Launches the setup screen
     */
    public void launchSetupScreen() { setupScreenLauncher.accept(this); }

    /**
     * Closes the setup screen and launches the main screen
     */
    public void closeSetupScreen() {
        clearScreen.run();
        launchMainScreen();
    }

    /**
     * Launches the main screen
     */
    public void launchMainScreen() { mainScreenLauncher.accept(this); }

    /**
     * Close the main screen
     */
    public void closeMainScreen() {
        clearScreen.run();
    }

    /**
     * Sets the current player object
     * @param player the current player object for the game
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Returns the current player object
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Quits the game.
     */
    public void quitGame() {
        clearScreen.run();
        quitGame.run();
    }
}
