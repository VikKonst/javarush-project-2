package com.javarush.island.gameobjects;

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
}
