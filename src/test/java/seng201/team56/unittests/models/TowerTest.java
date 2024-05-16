package seng201.team56.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void towerRarityTest() {
        tower = new Tower(Rarity.COMMON);

    }
}
