package seng201.team56.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.Difficulty;
import seng201.team56.models.Player;
import seng201.team56.models.Rarity;
import seng201.team56.models.Tower;
import seng201.team56.services.RoundService;
import seng201.team56.services.SetupService;
import seng201.team56.services.ShopService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RoundServiceTest {
    private RoundService roundService;
    @BeforeEach
    public void initAllTests() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            towers.add(new Tower(Rarity.COMMON));
        }
        SetupService setupService = new SetupService("Test player", Difficulty.MEDIUM, towers, 6);
        Player testPlayer = setupService.getPlayer();
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
        roundService.createRound(trackDistance,numCarts,cartMinSize,cartMaxSize,cartMinSpeed,cartMaxSpeed);
        assertAll("Round",
                () -> assertNotNull(roundService.getCurrentRound()),
                () -> assertEquals(numCarts, roundService.getCurrentRound().getCarts().size())
        );
    }
}
