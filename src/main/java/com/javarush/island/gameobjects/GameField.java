package com.javarush.island.gameobjects;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.services.GameService;
import com.javarush.island.species.animals.animalService.AnimalFactory;
import com.javarush.island.species.plants.Plant;

import java.util.*;

public class GameField {
    private static final GameField INSTANCE = new GameField();
    private static final int HEIGHT = 100;
    private static final int WIDTH = 20;
    private static final int MAX_AMOUNT_OF_ANIMALS_IN_CELL = 2345;
    private static final int MAX_AMOUNT_OF_PLANTS_IN_CELL = 200;
    private GameService gameService;
    private HashMap<Position, ArrayList<GameObject>> gameFieldCellsMap;

    private GameField() {

    }

    public static GameField getInstance() {
        return INSTANCE;
    }

    public void createGameField() {
        gameService = GameService.getInstance();
        gameFieldCellsMap = new HashMap<>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                gameFieldCellsMap.put(new Position(i, j), new ArrayList<>());
            }
        }
        setAnimalsOnGameField();
        setPlantsOnGameField();
    }

    public void setAnimalsOnGameField() {
        AnimalFactory animalFactory = new AnimalFactory();
        Set<Map.Entry<Position, ArrayList<GameObject>>> set = gameFieldCellsMap.entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> cell : set) {
            for (int i = 0; i < new Random().nextInt(MAX_AMOUNT_OF_ANIMALS_IN_CELL); i++) {
                Animal animal = animalFactory.createRandomAnimal();
                if (gameService.canGameObjectBeOnField(animal, cell.getValue())) {
                    cell.getValue().add(animal);
                }
            }
        }
    }

    public void setPlantsOnGameField() {
        Set<Map.Entry<Position, ArrayList<GameObject>>> set = gameFieldCellsMap.entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> cell : set) {
            for (int i = 0; i < new Random().nextInt(MAX_AMOUNT_OF_PLANTS_IN_CELL); i++) {
                Plant plant = new Plant(1, 200);
                if (gameService.canGameObjectBeOnField(plant, cell.getValue())) {
                    cell.getValue().add(plant);
                }
            }
        }
    }


    public HashMap<Position, ArrayList<GameObject>> getGameFieldCellsMap() {
        return gameFieldCellsMap;
    }

    public void setGameFieldCellsMap(HashMap<Position, ArrayList<GameObject>> gameFieldCellsMap) {
        this.gameFieldCellsMap = gameFieldCellsMap;
    }


    //    public void checkForFilling() {
//                Set<Map.Entry<Position, ArrayList<GameObject>>> set = gameFieldCellsMap.entrySet();
//        for(Map.Entry<Position, ArrayList<GameObject>> cell : set) {
//            Map<String, Long> counterMap = cell.getValue().stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
//            System.out.println(cell.getKey().getCoordinates() + " :: " + counterMap);
//        }
//        System.out.println("--------------------------");
//    }
}
