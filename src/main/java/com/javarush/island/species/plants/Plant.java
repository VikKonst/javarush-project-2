package com.javarush.island.species.plants;

import com.javarush.island.gameobjects.GameObject;

public class Plant implements GameObject {
    private double weight;
    private int maxItemsPerCell;
    private boolean isDead = Boolean.FALSE;

    public Plant(double weight, int maxItemsPerCell) {
        this.weight = weight;
        this.maxItemsPerCell = maxItemsPerCell;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getMaxItemsPerCell() {
        return maxItemsPerCell;
    }

    @Override
    public boolean getIsDead() {
        return isDead;
    }

    @Override
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
}
