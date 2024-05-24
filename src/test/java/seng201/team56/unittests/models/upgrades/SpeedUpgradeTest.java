package seng201.team56.unittests.models.upgrades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.SpeedUpgrade;
import seng201.team56.models.upgrades.Upgrade;

import static org.junit.jupiter.api.Assertions.*;

public class SpeedUpgradeTest {
    private Upgrade<?> upgrade;
    private Tower testTower;
    @BeforeEach
    void init() {
        upgrade = new SpeedUpgrade(10, 100);
        testTower = new Tower(ResourceType.BCO_QUICHE,20, 200,10);
    }

    @Test
    void applyUpgradeTest() {
        testTower = upgrade.applyUpgrade(testTower);
        assertEquals(100, testTower.getReloadSpeed());
    }
}
