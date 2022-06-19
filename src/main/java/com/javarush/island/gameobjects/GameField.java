package com.javarush.island.gameobjects;

import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.services.GameService;
import com.javarush.island.species.plants.Plant;

import java.util.*;

public class GameField {
    private static final GameField INSTANCE = new GameField();
    public static final int MAX_AMOUNT_OF_ANIMALS_IN_CELL = 2345;
    public static final int MAX_AMOUNT_OF_PLANTS_IN_CELL = 200;
    private List<Cell> fieldCells = new ArrayList<>();

    private GameField() {

    }

    public static GameField getInstance() {
        return INSTANCE;
    }

    public void createGameField(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(i, j);
                this.fieldCells.add(cell);
            }
        }
    }

    public List<GameObject> getGameObjectsOnCell(Cell cell) {
        Cell resultCell = this.fieldCells.stream().filter(c -> c.equals(cell)).findFirst().orElse(null);
        if (resultCell != null) {
            return resultCell.getObjectsOnCell();
        }
        return null;
    }

    public List<GameObject> getGameObjectsOnCellViaPosition(Position position) {
        Cell resultCell = this.fieldCells.stream().filter(c -> c.getPosition().equals(position)).findFirst().orElse(null);
        if (resultCell != null) {
            return resultCell.getObjectsOnCell();
        }
        return null;
    }

    public List<Cell> getFieldCells() {
        return fieldCells;
    }

    public Cell getCellByPosition(Position position) {
        return this.fieldCells.stream().filter(c -> c.getPosition().equals(position)).findFirst().orElse(null);
    }

    public void setCells(List<Cell> cells) {
        this.fieldCells = cells;
    }

//
//
//    public HashMap<Position, ArrayList<GameObject>> getGameFieldCellsMap() {
//        return gameFieldCellsMap;
//    }
//
//    public void setGameFieldCellsMap(HashMap<Position, ArrayList<GameObject>> gameFieldCellsMap) {
//        this.gameFieldCellsMap = gameFieldCellsMap;
//    }


    //    public void checkForFilling() {
//                Set<Map.Entry<Position, ArrayList<GameObject>>> set = gameFieldCellsMap.entrySet();
//        for(Map.Entry<Position, ArrayList<GameObject>> cell : set) {
//            Map<String, Long> counterMap = cell.getValue().stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
//            System.out.println(cell.getKey().getCoordinates() + " :: " + counterMap);
//        }
//        System.out.println("--------------------------");
//    }
}
