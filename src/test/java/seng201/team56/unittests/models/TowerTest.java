package seng201.team56.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import seng201.team56.models.Cart;
import seng201.team56.models.Rarity;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class TowerTest {
    private Tower tower;

    @BeforeEach
    public void setUpTower() {
       tower = new Tower(ResourceType.BOUILLABAISSE, 6, 5, 10);
    }

    @Test
    void fillCartTest() {
        Cart cart = new Cart(30, 10, ResourceType.BOUILLABAISSE, 100);
        tower.fillCarts(List.of(cart));
        assertEquals(6, cart.getResourceAmount());
    }

    @Test void wrongTypeCartTest() {
        Cart cart = new Cart(30, 10, ResourceType.COQ_A_VIN, 100);
        tower.fillCarts(List.of(cart));
        assertEquals(0, cart.getResourceAmount());
    }

    @ParameterizedTest
    @EnumSource
    void towerRarityTest(Rarity rarity) {
        tower = new Tower(rarity);
        assertAll("Tower",
                () -> assertNotEquals(0, tower.getReloadSpeed()),
                () -> assertNotEquals(0, tower.getResourceAmount()),
                () -> assertNotNull(tower.getResourceType()),
                () -> assertNotEquals(0, tower.getBuyPrice()),
                () -> assertNotEquals(0, tower.getSellPrice()),
                () -> assertEquals(0, tower.getLevel())
        );
        assertEquals(rarity, tower.getRarity());
    }

    @Test
    void decrementReloadTest() {
        tower.decreaseReloadInterval(5);
        assertEquals(0,tower.getReloadSpeed());
    }

    @Test
    void decrementReloadTestOver() {
        tower.decreaseReloadInterval(6);
        assertEquals(5,tower.getReloadSpeed());
    }

    @Test
    void decrementCapacityTest() {
        tower.decreaseResourceAmount(6);
        assertEquals(0,tower.getResourceAmount());
    }

    @Test
    void decrementCapacityTestOver() {
        tower.decreaseResourceAmount(7);
        assertEquals(6,tower.getResourceAmount());
    }
}
