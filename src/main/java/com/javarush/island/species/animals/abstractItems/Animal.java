package com.javarush.island.species.animals.abstractItems;

import com.javarush.island.gameobjects.GameObject;

import java.util.Map;
import java.util.UUID;

public abstract class Animal extends BasicEntity implements GameObject {
    private double weight;
    private int maxTravelSpeed;
    private double minKilosForSaturation;
    private int maxItemsPerCell;
    private boolean isDead = Boolean.FALSE;
    private double kilosEaten = 0;
    private int daysWithoutFood = 0;


    public Animal(double weight, int maxTravelSpeed, double minKilosForSaturation, int maxItemsPerCell) {
        this.weight = weight;
        this.maxTravelSpeed = maxTravelSpeed;
        this.minKilosForSaturation = minKilosForSaturation;
        this.maxItemsPerCell = maxItemsPerCell;
        this.setId(UUID.randomUUID());
    }

    public Animal(Map<String, Object> params) {
        this.setId(UUID.randomUUID());
        this.weight = (double) params.get("weight");
        this.maxTravelSpeed = (int) params.get("maxTravelSpeed");
        this.minKilosForSaturation = (double) params.get("minKilosForSaturation");
        this.maxItemsPerCell = (int) params.get("maxItemsPerCell");
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public int getMaxTravelSpeed() {
        return maxTravelSpeed;
    }

    public double getMinKilosForSaturation() {
        return minKilosForSaturation;
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

    public double getKilosEaten() {
        return kilosEaten;
    }

    public void setKilosEaten(double kilosEaten) {
        this.kilosEaten = kilosEaten;
    }

    public int getDaysWithoutFood() {
        return daysWithoutFood;
    }

    public void setDaysWithoutFood(int daysWithoutFood) {
        this.daysWithoutFood = daysWithoutFood;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
