package com.javarush.island.services;

import com.javarush.island.annotations.Injectable;
import com.javarush.island.gameobjects.Cell;
import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.species.animals.abstractItems.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Injectable
public class StatisticsService {
    public void getCurrentAmountOfObjects() {
        System.out.println("Current amount of game objects on the field:");
        List<GameObject> allGameObjectOnField = new ArrayList<>();
        for (Cell cell : GameField.getInstance().getFieldCells()) {
            allGameObjectOnField.addAll(cell.getObjectsOnCell());
        }
        Map<String, Long> amountOfObjectsOnField = allGameObjectOnField.stream().collect(Collectors.groupingBy(Object::toString, Collectors.counting()));
        for (Map.Entry<String, Long> objectCount : amountOfObjectsOnField.entrySet()) {
            System.out.println(objectCount.getKey() + " = " + objectCount.getValue());
        }
    }

    public void getCurrentAmountOfDeadObjects() {
        List<GameObject> eatenObjects = new ArrayList<>();
        List<GameObject> diedFromHunger = new ArrayList<>();
        for (Cell cell : GameField.getInstance().getFieldCells()) {
            List<GameObject> gameObjectsOnCell = cell.getObjectsOnCell();
            for (GameObject gameObject : gameObjectsOnCell) {
                if (gameObject.getIsDead()) {
                    eatenObjects.add(gameObject);
                } else if (gameObject instanceof Animal) {
                    if (((Animal) gameObject).getDaysWithoutFood() >= 3) { //animal can only live without food for 3 rounds of game
                        gameObject.setIsDead(true);
                        diedFromHunger.add(gameObject);
                    }
                }
            }
        }
        if (eatenObjects.size() > 0) {
            System.out.println("Amount of game objects that have been eaten this round:");
            Map<String, Long> amountOfEatenObjects = eatenObjects.stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
            for (Map.Entry<String, Long> objectCount : amountOfEatenObjects.entrySet()) {
                System.out.println(objectCount.getKey() + " = " + objectCount.getValue());
            }
        }
        if (diedFromHunger.size() > 0) {
            System.out.println("Amount of animals that have died from hunger this round:");
            Map<String, Long> amountOfDiedFromHungerAnimals = diedFromHunger.stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
            for (Map.Entry<String, Long> objectCount : amountOfDiedFromHungerAnimals.entrySet()) {
                System.out.println(objectCount.getKey() + " = " + objectCount.getValue());
            }
        }
    }
}
