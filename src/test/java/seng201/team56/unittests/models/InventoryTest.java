package seng201.team56.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team56.models.Inventory;
import seng201.team56.models.Rarity;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void init() {
        inventory = new Inventory(List.of());
    }

    @Test
    void addTowerTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addTower(tower);
        assertTrue(inventory.getTowers().contains(tower));
    }

    @Test
    void addTooManyTowersTest() {
        for (int i = 0; i < 5; i++) {
            inventory.addTower(new Tower(Rarity.COMMON));
        }
        assertThrows(ArrayStoreException.class, () -> inventory.addTower(new Tower(Rarity.COMMON)));
    }

    @Test
    void addTowerTwiceTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addTower(tower);
        assertThrows(ArrayStoreException.class, () -> inventory.addTower(tower));
    }

    @Test
    void moveTowerTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addTower(tower);
        inventory.moveTower(tower);
        assertTrue(inventory.getFieldTowers().contains(tower));
        assertFalse(inventory.getTowers().contains(tower));
    }

    @Test
    void addFieldTowerTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addFieldTower(tower);
        assertTrue(inventory.getFieldTowers().contains(tower));
    }

    @Test
    void addTooManyFieldTowersTest() {
        for (int i = 0; i < 5; i++) {
            inventory.addFieldTower(new Tower(Rarity.COMMON));
        }
        assertThrows(ArrayStoreException.class, () -> inventory.addFieldTower(new Tower(Rarity.COMMON)));
    }

    @Test
    void addFieldTowerTwiceTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addFieldTower(tower);
        assertThrows(ArrayStoreException.class, () -> inventory.addFieldTower(tower));
    }

    @Test
    void moveFieldTowerTest() {
        Tower tower = new Tower(Rarity.COMMON);
        inventory.addFieldTower(tower);
        inventory.moveTower(tower);
        assertTrue(inventory.getTowers().contains(tower));
        assertFalse(inventory.getFieldTowers().contains(tower));
    }
}
