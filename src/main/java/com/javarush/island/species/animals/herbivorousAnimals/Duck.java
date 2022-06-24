package com.javarush.island.species.animals.herbivorousAnimals;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.abstractItems.HerbivorousAnimal;

import java.util.Map;

public class Duck extends Animal implements HerbivorousAnimal {
    public Duck(double weight, int maxTravelSpeed, double minKilosForSaturation, int maxItemsPerCell) {
        super(weight, maxTravelSpeed, minKilosForSaturation, maxItemsPerCell);
    }

    public Duck(Map<String, Object> params) {
        super(params);
    }
}
