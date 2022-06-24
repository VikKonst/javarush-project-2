package com.javarush.island.gameobjects;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final Position position;
    private final List<GameObject> objectsOnCell = new ArrayList<>();

    public Cell(int coordinateX, int coordinateY) {
        this.position = new Position(coordinateX, coordinateY);
    }

    public Position getPosition() {
        return position;
    }

    public List<GameObject> getObjectsOnCell() {
        return objectsOnCell;
    }
}
