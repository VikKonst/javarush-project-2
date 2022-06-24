package com.javarush.island.species.animals.herbivorousAnimals;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.abstractItems.HerbivorousAnimal;

import java.util.Map;

public class Horse extends Animal implements HerbivorousAnimal {
    public Horse(double weight, int maxTravelSpeed, double minKilosForSaturation, int maxItemsPerCell) {
        super(weight, maxTravelSpeed, minKilosForSaturation, maxItemsPerCell);
    }

    public Horse(Map<String, Object> params) {
        super(params);
    }
}
