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
    private Player player;

    /**
     *
     * @param setupScreenLauncher
     * @param mainScreenLauncher
     * @param clearScreen
     */
    public GameEnvironment(Consumer<GameEnvironment> setupScreenLauncher, Consumer<GameEnvironment> mainScreenLauncher, Runnable clearScreen) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.mainScreenLauncher = mainScreenLauncher;
        this.clearScreen = clearScreen;
        launchSetupScreen();
    }

    /**
     *
     */
    public void launchSetupScreen() { setupScreenLauncher.accept(this); }

    /**
     *
     */
    public void closeSetupScreen() {
        clearScreen.run();
        launchMainScreen();
    }

    /**
     *
     */
    public void launchMainScreen() { mainScreenLauncher.accept(this); }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
