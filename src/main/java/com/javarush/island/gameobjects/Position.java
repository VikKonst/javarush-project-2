package com.javarush.island.gameobjects;

import java.util.Objects;

public class Position {
    private String coordinates;
    private int coordinateX;
    private int coordinateY;

    public Position(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinates = "X" + coordinateX + "Y" + coordinateY;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return coordinateX == position.coordinateX && coordinateY == position.coordinateY && Objects.equals(coordinates, position.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, coordinateX, coordinateY);
    }
}
