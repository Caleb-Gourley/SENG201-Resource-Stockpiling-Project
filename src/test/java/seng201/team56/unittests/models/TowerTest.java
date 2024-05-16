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
    }

    @Test
    void propertyChangeTest() {
        Cart cart = new Cart(30, 10, ResourceType.BOUILLABAISSE, 100);
        PropertyChangeEvent event = new PropertyChangeEvent(cart,"distance", 0, 30);
        tower.propertyChange(event);
        assertEquals(6, cart.getResourceAmount());
    }

    @Test
    void towerRarityTest() {
        tower = new Tower(Rarity.COMMON);

    }
}
