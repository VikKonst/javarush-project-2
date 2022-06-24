package com.javarush.island.species.animals.carnivorousAnimals;

import com.javarush.island.species.animals.abstractItems.CarnivorousAnimal;
import com.javarush.island.species.animals.abstractItems.Animal;

import java.util.Map;

public class Wolf extends Animal implements CarnivorousAnimal {
    public Wolf(double weight, int maxTravelSpeed, double minKilosForSaturation, int maxItemsPerCell) {
        super(weight, maxTravelSpeed, minKilosForSaturation, maxItemsPerCell);
    }

    public Wolf(Map<String, Object> params) {
        super(params);
    }
}
