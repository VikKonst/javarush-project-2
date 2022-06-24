package com.javarush.island.species.animals.carnivorousAnimals;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.abstractItems.CarnivorousAnimal;

import java.util.Map;

public class Eagle extends Animal implements CarnivorousAnimal {
    public Eagle(double weight, int maxTravelSpeed, double minKilosForSaturation, int maxItemsPerCell) {
        super(weight, maxTravelSpeed, minKilosForSaturation, maxItemsPerCell);
    }

    public Eagle(Map<String, Object> params) {
        super(params);
    }
}
