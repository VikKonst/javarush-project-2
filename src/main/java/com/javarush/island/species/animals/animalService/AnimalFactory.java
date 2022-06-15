package com.javarush.island.species.animals.animalService;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.carnivorousAnimals.*;
import com.javarush.island.species.animals.herbivorousAnimals.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.javarush.island.services.AppService.getDblProperty;
import static com.javarush.island.services.AppService.getIntProperty;

public class AnimalFactory {

    public Animal createRandomAnimal() {
        return createAnimal(new Random().nextInt(15));
    }

    public Animal createAnimal(int animalNumber) {
        Animal animal = switch (animalNumber) {
            case 0 -> new Anaconda(getParams(Anaconda.class));
            case 1 -> new Bear(getParams(Bear.class));
            case 2 -> new Eagle(getParams(Eagle.class));
            case 3 -> new Fox(getParams(Fox.class));
            case 4 -> new Wolf(getParams(Wolf.class));
            case 5 -> new Boar(getParams(Boar.class));
            case 6 -> new Bull(getParams(Bull.class));
            case 7 -> new Caterpillar(getParams(Caterpillar.class));
            case 8 -> new Deer(getParams(Deer.class));
            case 9 -> new Duck(getParams(Duck.class));
            case 10 -> new Goat(getParams(Goat.class));
            case 11 -> new Hare(getParams(Hare.class));
            case 12 -> new Horse(getParams(Horse.class));
            case 13 -> new Mouse(getParams(Mouse.class));
            case 14 -> new Sheep(getParams(Sheep.class));
            default -> null;
        };
        return animal;
    }

    public Map<String, Object> getParams(Class<? extends Animal> clazz) {
        String className = clazz.getSimpleName();
        Map<String, Object> params = new HashMap<>();
        params.put("weight", getDblProperty(className + ".weight"));
        params.put("maxTravelSpeed", getIntProperty(className + ".maxTravelSpeed"));
        params.put("minKilosForSaturation", getDblProperty(className + ".minKilosForSaturation"));
        params.put("maxItemsPerCell", getIntProperty(className + ".maxItemsPerCell"));
        return params;
    }
}
