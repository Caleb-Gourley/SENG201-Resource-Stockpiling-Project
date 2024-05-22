package seng201.team56.unittests.services;

import org.junit.jupiter.api.*;
import seng201.team56.models.*;
import seng201.team56.services.RoundService;
import seng201.team56.services.SetupService;
import seng201.team56.services.ShopService;
import seng201.team56.services.util.RandomEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RoundServiceTest {
    private RoundService roundService;
    private RoundDifficulty difficulty;
    private Tower testTower;
    @BeforeEach
    void initTest() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            towers.add(new Tower(Rarity.COMMON));
        }
        SetupService setupService = new SetupService("Test player", Difficulty.MEDIUM, towers, 6);
        Player testPlayer = setupService.getPlayer();
        testTower = new Tower(Rarity.COMMON);
        testPlayer.getInventory().addFieldTower(testTower);
        roundService = new RoundService(testPlayer, new ShopService(testPlayer));
        double trackDistance = 50;
        int numCarts = 5;
        int cartMinSize = 15;
        int cartMaxSize = 40;
        double cartMinSpeed = 5;
        double cartMaxSpeed = 10.5;
        difficulty = new RoundDifficulty(trackDistance,numCarts,cartMinSize,cartMaxSize,cartMinSpeed,
                cartMaxSpeed, 100, 100);
        roundService.setRoundDifficulty(difficulty);
    }

    @Test
    void createRoundTest() {
        roundService.createRound();
        assertAll("Round",
                () -> assertNotNull(roundService.getCurrentRound()),
                () -> assertEquals(difficulty.numCarts(), roundService.getCurrentRound().getCarts().size())
        );
    }

    @Test
    void randomEventTest() {
        Tower tower = new Tower(Rarity.COMMON);
        assertEquals(0, tower.getUseCount());
        RandomEvent testEvent = new RandomEvent(15, tower::incUseCount, Difficulty.HARD);
        RandomEvent doNothing = new RandomEvent(() -> {}, 0);
        List<RandomEvent> events = List.of(testEvent, doNothing);
        assertAll(
                () -> assertEquals(20, testEvent.getWeight()),
                () -> assertEquals(0, doNothing.getWeight())
        );
        roundService.randomEvent(events);
        assertEquals(1, tower.getUseCount());

    }

    @Test
    void playRoundTest() {
        roundService.createRound();
        roundService.playRound();
        assertTrue(roundService.isRoundRunning());
    }

    @Disabled
    @Test
    void roundEndTest() {
        roundService.createRound();
        roundService.playRound();
        assertTimeout(Duration.ofSeconds(30), () -> {
            while(!roundService.isRoundRunning()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        assertFalse(roundService.isRoundRunning());
    }

    @Test
    void genDifficultiesTest() {
        List<RoundDifficulty> difficulties = roundService.generateRoundDifficulties();
        assertEquals(3, difficulties.size());
    }
}
