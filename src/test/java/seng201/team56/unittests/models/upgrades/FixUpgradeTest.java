package seng201.team56.unittests.models.upgrades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.FixUpgrade;
import seng201.team56.models.upgrades.Upgrade;

import static org.junit.jupiter.api.Assertions.*;

public class FixUpgradeTest {
    private Upgrade<?> upgrade;
    private Tower testTower;

    @BeforeEach
    void init() {
        upgrade = new FixUpgrade(10);
        testTower = new Tower(ResourceType.BCO_QUICHE,30,1000,10);
    }

    @Test
    void brokenTowerFix() {
        testTower.setBroken(true);
        upgrade.applyUpgrade(testTower);
        assertFalse(testTower.isBroken());
    }

    @Test
    void nonBrokenTowerFix() {
        testTower.setBroken(false);
        upgrade.applyUpgrade(testTower);
        assertFalse(testTower.isBroken());
    }
}
