package seng201.team56.services;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Retrieved from <a href = https://stackoverflow.com/a/6409791>https://stackoverflow.com/a/6409791</a></a> 17/5/24
 * @param <E> The type of object to be stored in this RandomEventCollection
 * @author <a href=https://stackoverflow.com/users/57695/peter-lawrey>Peter Lawrey</a>
 */
public class RandomEventCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomEventCollection() {
        this(new Random());
    }

    public RandomEventCollection(Random random) {
        this.random = random;
    }

    public RandomEventCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
