package com.javarush.island.gameobjects;

public interface GameObject {
    int getMaxItemsPerCell();

    boolean getIsDead();

    void setIsDead(boolean isDead);

    double getWeight();
}
