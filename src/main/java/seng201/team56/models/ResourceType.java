package seng201.team56.models;

import java.util.List;
import java.util.Random;

/**
 * ResourceType options (to be used for carts and towers).
 * @author Sean Reitsma
 */
public enum ResourceType {
    /**
     * Bacon, Cheddar, and Onion Quiché
     */
    BCO_QUICHE ("Bacon, Cheddar, and Onion Quiché"),
    /**
     * Bouillabaisse
     */
    BOUILLABAISSE ("Bouillabaisse"),
    /**
     * Gruyère Cheese Soufflé
     */
    GRUYER_CHEESE_SOUFFLE ("Gruyère Cheese Soufflé"),
    /**
     * Crème Brûlée<br>
     * Mmm...
     */
    CREME_BRULEE ("Crème Brûlée"),
    /**
     * Hot Niçoise Salad
     */
    NICOISE_SALAD ("Hot Niçoise Salad"),
    /**
     * Coq au Vin
     */
    COQ_A_VIN ("Coq au Vin"),
    /**
     * Ratatouille
     */
    RATATOUILLE ("Ratatouille");

    private final String name;
    private static final List<ResourceType> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    ResourceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a random {@link ResourceType} for this Rarity
     * @return a random ResourceType
     */
    public static ResourceType randomResourceType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
