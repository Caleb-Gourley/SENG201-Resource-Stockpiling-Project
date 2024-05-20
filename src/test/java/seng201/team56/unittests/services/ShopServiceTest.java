package seng201.team56.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team56.models.*;
import seng201.team56.services.ShopService;

import java.util.List;

public class ShopServiceTest {
    private ShopService shopService;
    private Player testPlayer;

    @BeforeEach
    void initTest() {
        testPlayer = new Player("Test player", Difficulty.EASY, new Inventory(List.of(new Tower(Rarity.COMMON), new Tower(Rarity.UNCOMMON))), 15);
        shopService = new ShopService(testPlayer);
    }

    @Test
    void updateItemsTest() {
        List<Purchasable> oldItems = shopService.getItems();
        shopService.updateItems(1);
        assertNotEquals(oldItems, shopService.getItems());
    }

    @Test
    void buyItemTest() {
        shopService.getItems().add(new Tower(ResourceType.BCO_QUICHE, 100, 2000, 50));
        shopService.buyItem(0);
        assertEquals(0, shopService.getItems().size());
        assertEquals(3, testPlayer.getInventory().getTowers().size());
        assertEquals(50, testPlayer.getMoney());
    }

    @Test
    void cantBuyItemTest() {
        shopService.getItems().add(new Tower(ResourceType.BCO_QUICHE, 100, 2000, 101));
        assertFalse(shopService.buyItem(0));
        assertEquals(100, testPlayer.getMoney());
        assertEquals(1, shopService.getItems().size());
        assertEquals(2, testPlayer.getInventory().getTowers().size());
    }
}
