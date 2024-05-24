package seng201.team56.unittests.models.upgrades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.CapacityUpgrade;
import seng201.team56.models.upgrades.Upgrade;

import static org.junit.jupiter.api.Assertions.*;

public class CapacityUpgradeTest {
    private Upgrade<?> upgrade;
    private Tower testTower;
    @BeforeEach
    void init() {
        upgrade = new CapacityUpgrade(20, 10);
        testTower = new Tower(ResourceType.BCO_QUICHE, 30, 30 , 10);
    }

    @Test
    void applyUpgrade() {
        upgrade.applyUpgrade(testTower);
        assertEquals(40,testTower.getResourceAmount());
    }
}
