package seng201.team56;

import java.util.function.Consumer;

public class GameEnvironment {
    private final Consumer<GameEnvironment> setupScreenLauncher;
    private final Consumer<GameEnvironment> mainScreenLauncher;
    private final Runnable clearScreen;

    public GameEnvironment(Consumer<GameEnvironment> setupScreenLauncher, Consumer<GameEnvironment> mainScreenLauncher, Runnable clearScreen) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.mainScreenLauncher = mainScreenLauncher;
        this.clearScreen = clearScreen;
        launchSetupScreen();
    }

    public void launchSetupScreen() { setupScreenLauncher.accept(this); }

    public void closeSetupScreen() {
        clearScreen.run();
        launchMainScreen();
    }

    public void launchMainScreen() { mainScreenLauncher.accept(this); }
}
