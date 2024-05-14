package seng201.team56.unittests.services;

import org.junit.jupiter.api.Test;

import seng201.team56.models.Difficulty;
import seng201.team56.models.Player;
import seng201.team56.models.Rarity;
import seng201.team56.models.Tower;
import seng201.team56.services.SetupService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SetupServiceTest {
    @Test
    void playerConstructionTest() {
        String name = "Sean";
        Difficulty difficulty = Difficulty.MEDIUM;
        List<Tower> towers = List.of(new Tower(Rarity.COMMON));
        int maxRoundNum = 7;
        SetupService setupService = new SetupService(name,difficulty,towers,maxRoundNum);
        Player player = setupService.getPlayer();
        assertEquals(name,player.getName());
        assertEquals(towers,player.getInventory().getTowers());
    }

    @Test
    void randomTowersTest() {
        ArrayList<Tower> towers = SetupService.getTowersToChoose();
        assertFalse(towers.isEmpty());
    }
}
