package seng201.team56.unittests.models.upgrades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.Rarity;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.RarityUpgrade;
import seng201.team56.models.upgrades.Upgrade;

import static org.junit.jupiter.api.Assertions.*;

public class RarityUpgradeTest {
    private Upgrade<?> upgrade;
    private Tower testTower;

    @BeforeEach
    void init() {
        upgrade = new RarityUpgrade(10);
        testTower = new Tower(Rarity.COMMON);
    }

    @Test
    void applyUpgrade() {
        testTower = upgrade.applyUpgrade(testTower);
        assertEquals(Rarity.UNCOMMON,testTower.getRarity());
    }

    @Test
    void legendaryUpgradeTest() {
        testTower = new Tower(Rarity.LEGENDARY);
        testTower = upgrade.applyUpgrade(testTower);
        assertEquals(Rarity.LEGENDARY,testTower.getRarity());
    }

}
