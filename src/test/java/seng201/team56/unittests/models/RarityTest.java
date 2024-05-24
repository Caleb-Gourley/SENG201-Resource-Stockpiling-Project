package seng201.team56.unittests.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import seng201.team56.models.Rarity;

import static org.junit.jupiter.api.Assertions.*;

public class RarityTest {
    @Test
    void pickRarityTest() {
        Rarity rarity = Rarity.pickRarity(1,5);
        assertTrue(rarity == Rarity.COMMON || rarity == Rarity.UNCOMMON);
    }

    @Test
    void nextRarityTest() {
        assertAll(
                () -> assertEquals(Rarity.UNCOMMON, Rarity.getNextRarity(Rarity.COMMON)),
                () -> assertEquals(Rarity.RARE, Rarity.getNextRarity(Rarity.UNCOMMON)),
                () -> assertEquals(Rarity.EPIC, Rarity.getNextRarity(Rarity.RARE)),
                () -> assertEquals(Rarity.LEGENDARY, Rarity.getNextRarity(Rarity.EPIC)),
                () -> assertEquals(Rarity.LEGENDARY, Rarity.getNextRarity(Rarity.LEGENDARY))
        );
    }
}
