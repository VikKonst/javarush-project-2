package com.javarush.island.services;

import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.gameobjects.Position;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Statistics {
    private final GameField gameField;
    private final GameService gameService;

    public Statistics() {
        this.gameField = GameField.getInstance();
        this.gameService = GameService.getInstance();
    }

    public void getCurrentAmountOfObjects() {
        System.out.println("Current amount of game objects on the field:");
        ArrayList<GameObject> allGameObjectOnField = new ArrayList<>();
        Set<Map.Entry<Position, ArrayList<GameObject>>> set = gameField.getGameFieldCellsMap().entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> cell : set) {
            ArrayList<GameObject> gameObjectsOnCell = cell.getValue();
            allGameObjectOnField.addAll(gameObjectsOnCell);
        }
        Map<String, Long> amountOfObjectsOnField = allGameObjectOnField.stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
        for (Map.Entry<String, Long> objectCount : amountOfObjectsOnField.entrySet()) {
            System.out.println(objectCount.getKey() + " = " + objectCount.getValue());
        }
    }
}
