package seng201.team56.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng201.team56.models.*;
import seng201.team56.services.RoundService;
import seng201.team56.services.SetupService;
import seng201.team56.services.ShopService;
import seng201.team56.services.util.RandomEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RoundServiceTest {
    private RoundService roundService;
    private Tower testTower;
    @BeforeEach
    public void initAllTests() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            towers.add(new Tower(Rarity.COMMON));
        }
        SetupService setupService = new SetupService("Test player", Difficulty.MEDIUM, towers, 6);
        Player testPlayer = setupService.getPlayer();
        testTower = new Tower(Rarity.COMMON);
        testPlayer.getInventory().addFieldTower(testTower);
        roundService = new RoundService(testPlayer, new ShopService(testPlayer));
    }

    @Test
    void createRoundTest() {
        double trackDistance = 50;
        int numCarts = 5;
        int cartMinSize = 15;
        int cartMaxSize = 40;
        double cartMinSpeed = 5;
        double cartMaxSpeed = 10.5;
        RoundDifficulty difficulty = new RoundDifficulty(trackDistance,numCarts,cartMinSize,cartMaxSize,cartMinSpeed,
                cartMaxSpeed, 100, 100);
        roundService.setRoundDifficulty(difficulty);
        roundService.createRound();
        assertAll("Round",
                () -> assertNotNull(roundService.getCurrentRound()),
                () -> assertEquals(numCarts, roundService.getCurrentRound().getCarts().size())
        );
    }

    @Disabled
    @Test
    void randomEventTest() {
        //FIXME Not yet implemented
        List<RandomEvent> events = List.of(new RandomEvent(() -> {}, 1));
        roundService.randomEvent(events);
    }
}
