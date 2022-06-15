package com.javarush.island.species.animals.animalService;

import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.gameobjects.Position;
import com.javarush.island.services.GameService;
import com.javarush.island.services.AppService;
import com.javarush.island.species.animals.abstractItems.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalService {

    public void move(Animal animal, Position currentPosition) {
        for (int i = 0; i <= new Random().nextInt(animal.getMaxTravelSpeed()); i++) {
            Position newPosition = chooseDirection(animal, currentPosition);
            if (!(newPosition.equals(currentPosition))) {
                moveAnimalToNewPosition(currentPosition, newPosition, animal);
            }
            // else {
            //System.out.println("Animal decided to stay at current position " + currentPosition.getCoordinates());
            //}
        }
    }

    public Position chooseDirection(Animal animal, Position currentPosition) {
        //System.out.println("Choose direction for Animal " + animal.toString());
        boolean isPositionRelevant = false;
        Position newPosition = null;
        List<Position> randomlyChosenPositions = new ArrayList<>();
        /*
        only four possible directions are given to an animal, if no one is suitable
        animal stays at its current position
        for keeping record that all possible directions have been tried the variable List<Position> randomlyChosenPositions is created
        if the size of the list is 4 and no position appeared to be relevant, animal shall stay at its current position
        */
        while (!isPositionRelevant) {
            int randomDirection = new Random().nextInt(5);
            newPosition = switch (randomDirection) {
                case 0 -> new Position(currentPosition.getCoordinateX() + 1, currentPosition.getCoordinateY());
                case 1 -> new Position(currentPosition.getCoordinateX(), currentPosition.getCoordinateY() + 1);
                case 2 -> new Position(currentPosition.getCoordinateX() - 1, currentPosition.getCoordinateY());
                case 3 -> new Position(currentPosition.getCoordinateX(), currentPosition.getCoordinateY() - 1);
                default -> currentPosition;
            };

            if (randomlyChosenPositions.contains(newPosition) && randomlyChosenPositions.size() >= 4) {
                newPosition = currentPosition;
                break;
            }
            isPositionRelevant = isPositionExist(currentPosition, newPosition) && canAnimalBePlacedOnField(animal, newPosition);
            if (!isPositionRelevant && !randomlyChosenPositions.contains(newPosition)) {
                randomlyChosenPositions.add(newPosition);
            }
        }
        return newPosition;
    }

    public boolean isPositionExist(Position currentPosition, Position newPosition) {
        if (currentPosition.equals(newPosition)) {
            return true;
        }

        return GameField.getInstance().getGameFieldCellsMap().containsKey(newPosition);
    }

    private void moveAnimalToNewPosition(Position currentPosition, Position newPosition, Animal animal) {
        GameField.getInstance().getGameFieldCellsMap().get(currentPosition).remove(animal);
        GameField.getInstance().getGameFieldCellsMap().get(newPosition).add(animal);

        //System.out.println("Animal " + animal + " moving from old Position " + currentPosition.getCoordinates() +  " to new Position " + newPosition.getCoordinates());
    }

    private boolean canAnimalBePlacedOnField(Animal animal, Position newPosition) {
        return GameService.getInstance().canGameObjectBeOnField(animal, GameField.getInstance().getGameFieldCellsMap().get(newPosition));
    }

    public void eat(Animal animal, List<GameObject> gameObjects) {
        int possibility = 0;
        GameObject prey = null;
        int attemptsToHunt = 3; //only 3 attempts are given for hunting, if no prey is found then animal stays hungry
        while (attemptsToHunt > 0) {
            int randomForChoosingPrey = new Random().nextInt(gameObjects.size());
            //System.out.println("possibility to eat " + possibility);
            prey = gameObjects.get(randomForChoosingPrey);
            //System.out.println("Animal " + animal.getClass().getSimpleName() + " try to eat " + prey.getClass().getSimpleName());
            possibility = AppService.getIntProperty(animal.getClass().getSimpleName() + ".canEat." + prey.getClass().getSimpleName());
            if (possibility > 0)
                break;
            attemptsToHunt--;
        }
        if (canEat(possibility)) {
            //System.out.println("Animal " + animal.getClass().getSimpleName() + " is eating " + prey.getClass().getSimpleName());
            prey.setIsDead(true);
            animal.setKilosEaten(prey.getWeight());
            if (animal.getKilosEaten() < animal.getMinKilosForSaturation()) {
                animal.setDaysWithoutFood(animal.getDaysWithoutFood() + 1);
            } else {
                animal.setDaysWithoutFood(0);
            }
        } else {
            //System.out.println("Animal " + animal.getClass().getSimpleName() + " is not eating " + prey.getClass().getSimpleName());
            animal.setDaysWithoutFood(animal.getDaysWithoutFood() + 1);
        }
    }

    private boolean canEat(int possibility) {
        if (possibility == 0)
            return false;
        int eatOrNot = new Random().nextInt(100);
        return eatOrNot <= possibility;
    }

    public void breed(Animal animal, Position currentPosition, List<GameObject> gameObjects) {
        Animal newBornAnimal;
        Class<? extends Animal> animalClass = animal.getClass();
        int randomForChoosingPartner = new Random().nextInt(gameObjects.size());
        GameObject partner = gameObjects.get(randomForChoosingPartner);
        if (!partner.getIsDead() && partner.getClass().equals(animal.getClass())) {
            newBornAnimal = createNewBornAnimal(animalClass);
            if (newBornAnimal != null && GameService.getInstance().canGameObjectBeOnField(newBornAnimal, gameObjects)) {
                GameField.getInstance().getGameFieldCellsMap().get(currentPosition).add(newBornAnimal);
                //System.out.println("New born " + animal.getClass().getSimpleName() + " appeared on position " + currentPosition.getCoordinates());
            } else {
                //System.out.println("***************new born animal can not be on field");
            }
        } else {
            //System.out.println("****************partner is dead or another class");

        }
    }

    private <T extends Animal> T createNewBornAnimal(Class<T> clazz) {
        Constructor<?> constructor = clazz.getConstructors()[1];
        Object instance = null;
        try {
            instance = constructor.newInstance(new AnimalFactory().getParams(clazz));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (instance != null) {
            return clazz.cast(instance);
        }
        return null;
    }
}
