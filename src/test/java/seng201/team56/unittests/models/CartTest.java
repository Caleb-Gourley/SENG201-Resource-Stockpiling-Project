package seng201.team56.unittests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import seng201.team56.models.Cart;

import org.junit.jupiter.api.Test;
import seng201.team56.models.ResourceType;
import seng201.team56.models.Tower;

/**
 * Unit tests for the cart model
 * @author sean
 */
class CartTest {
    private Cart cart;

    /**
     * Setup before each test. Creates a test cart object with some hard coded sample values
     */
    @BeforeEach
    public void setUpTest() {
        cart = new Cart(30, 10, ResourceType.BOUILLABAISSE, 100);
    }

    /**
     * Test filling the cart up to its exact size
     */
    @Test
    void fillExactTest() {
        cart.fillAmount(10);
        assertEquals(10, cart.getResourceAmount());
    }

    /**
     * Test a fill amount over the carts size, resulting in a leftover amount
     */
    @Test
    void fillOverTest() {
        cart.fillAmount(11);
        assertEquals(10, cart.getResourceAmount());
    }

    /**
     * Test a fill amount under the carts size
     */
    @Test
    void fillUnderTest() {
        cart.fillAmount(9);
        assertEquals(9, cart.getResourceAmount());

    }

    @Test
    void moveTest() {
        cart.move();
        assertEquals(30, cart.getDistance());
    }

    @Test
    void finishTest() {
        for (int i = 0; i < 4; i++) {
            cart.move();
        }
        assertEquals(120, cart.getDistance());
        assertTrue(cart.isDone());
    }

    @Test
    void fillFullTest() {
        cart.fillAmount(10);
        assertEquals(10, cart.getResourceAmount());
        assertTrue(cart.isFull());
    }
}
