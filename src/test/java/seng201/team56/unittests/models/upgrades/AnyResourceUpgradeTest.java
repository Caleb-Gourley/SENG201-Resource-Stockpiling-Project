package seng201.team56.unittests.models.upgrades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.Cart;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.AnyResourceUpgrade;
import seng201.team56.models.upgrades.Upgrade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnyResourceUpgradeTest {
    private Upgrade<?> upgrade = new AnyResourceUpgrade(10);
    private Tower testTower;
    @BeforeEach
    void init() {
        testTower = new Tower(ResourceType.BCO_QUICHE, 30, 1000, 10);
    }
    @Test
    void applyUpgrade() {
        upgrade.applyUpgrade(testTower);
        Cart cart = new Cart(10, 30, ResourceType.COQ_A_VIN, 100);
        testTower.fillCarts(List.of(cart));
        assertEquals(6, cart.getResourceAmount());
    }
}