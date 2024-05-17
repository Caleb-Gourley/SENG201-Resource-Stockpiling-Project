package seng201.team56.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import seng201.team56.models.Cart;
import seng201.team56.models.Rarity;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.PropertyChangeEvent;

public class TowerTest {
    private Tower tower;

    @BeforeEach
    public void setUpTower() {
       tower = new Tower(ResourceType.BOUILLABAISSE, 6, 5, 10);
       tower.reload();
       tower.setDistance(30.0);
    }

    @Test
    void propertyChangeTest() {
        Cart cart = new Cart(30, 10, ResourceType.BOUILLABAISSE, 100);
        PropertyChangeEvent event = new PropertyChangeEvent(cart,"distance", cart.getDistance(), 30.0);
        tower.propertyChange(event);
        assertTrue(event.getPropertyName().equals("distance")
                && (event.getNewValue() instanceof Double)
                && ((double) event.getNewValue() >= tower.getDistance()));
        assertEquals(6, cart.getResourceAmount());
    }

    @Test
    void garbageSourcePropertyChangeTest() {
        Object o = "A string?";
        PropertyChangeEvent event = new PropertyChangeEvent(o, "class", o.getClass(), Tower.class);
        assertDoesNotThrow(() -> tower.propertyChange(event));
        assertFalse(event.getPropertyName().equals("distance")
                && (event.getNewValue() instanceof Double)
                && ((double) event.getNewValue() >= tower.getDistance()));
    }

    @Test
    void garbagePropertyChangeTest() {
        Cart cart = new Cart(30, 10, ResourceType.BOUILLABAISSE, 100);
        PropertyChangeEvent event = new PropertyChangeEvent(cart, "resourceAmount", 0, 6);
        assertDoesNotThrow(() -> tower.propertyChange(event));
        assertFalse(event.getPropertyName().equals("distance")
                && (event.getNewValue() instanceof Double)
                && ((double) event.getNewValue() >= tower.getDistance()));
    }

    @ParameterizedTest
    @EnumSource
    void towerRarityTest(Rarity rarity) {
        tower = new Tower(rarity);
        assertAll("Tower",
                () -> assertNotEquals(0, tower.getReloadSpeed()),
                () -> assertNotEquals(0, tower.getResourceFullAmount()),
                () -> assertNotNull(tower.getResourceType()),
                () -> assertNotEquals(0, tower.getBuyPrice()),
                () -> assertNotEquals(0, tower.getSellPrice()),
                () -> assertEquals(0, tower.getLevel())
        );
        assertEquals(rarity, tower.getRarity());
    }
}
