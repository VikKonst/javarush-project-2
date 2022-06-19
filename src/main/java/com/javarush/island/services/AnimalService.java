package com.javarush.island.services;

import com.javarush.island.annotations.Injectable;
import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.gameobjects.Position;
import com.javarush.island.species.animals.abstractItems.Animal;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
;

@Injectable
public class AnimalService {
    private final AppService appService;

    public AnimalService(AppService appService) {
        this.appService = appService;
    }

    public Animal createRandomAnimal() {
        List<Class<? extends Animal>> allClassesExtendingAnimal = appService.getAllClassesExtendingAnimal();
        return createAnimal(allClassesExtendingAnimal.get(new Random().nextInt(allClassesExtendingAnimal.size())));
    }

    public Map<String, Object> getParams(Class<? extends Animal> clazz) {
        String className = clazz.getSimpleName();
        Map<String, Object> params = new HashMap<>();
        params.put("weight", appService.getDblProperty(className + ".weight"));
        params.put("maxTravelSpeed", appService.getIntProperty(className + ".maxTravelSpeed"));
        params.put("minKilosForSaturation", appService.getDblProperty(className + ".minKilosForSaturation"));
        params.put("maxItemsPerCell", appService.getIntProperty(className + ".maxItemsPerCell"));
        return params;
    }

    public <T extends Animal> T createAnimal(Class<T> clazz) {
        Constructor<?> constructor = null;
        Object result = null;
        for (Constructor<?> construct: clazz.getConstructors()) {
            if (construct.getParameterTypes().length == 1 && construct.getParameterTypes()[0].equals(Map.class)) {
                constructor = construct;
                break;
            }
        }
        if (constructor != null) {
            try {
                result = constructor.newInstance(getParams(clazz));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return clazz.cast(result);
    }

    public void move(Animal animal, Position currentPosition) {
        for (int i = 0; i <= new Random().nextInt(animal.getMaxTravelSpeed()); i++) {
            Position newPosition = chooseDirection(animal, currentPosition);
            if (!(newPosition.equals(currentPosition))) {
                moveAnimalToNewPosition(currentPosition, newPosition, animal);
            }
        }
    }

    public Position chooseDirection(Animal animal, Position currentPosition) {
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
        } else return GameField.getInstance().getCellByPosition(newPosition) != null;
    }

    private void moveAnimalToNewPosition(Position currentPosition, Position newPosition, Animal animal) {
        GameField.getInstance().getGameObjectsOnCellViaPosition(currentPosition).remove(animal);
        GameField.getInstance().getGameObjectsOnCellViaPosition(newPosition).add(animal);
    }

    private boolean canAnimalBePlacedOnField(Animal animal, Position newPosition) {
        return canGameObjectBeOnField(animal, GameField.getInstance().getGameObjectsOnCellViaPosition(newPosition));
    }

    public boolean canGameObjectBeOnField(GameObject gameObject, List<GameObject> gameObjects) {
        int count = 0;
        for (GameObject gameObjTmp : gameObjects) {
            if (!gameObjTmp.getIsDead() && gameObject.getClass().equals(gameObjTmp.getClass())) {
                count++;
            }
        }
        return count < gameObject.getMaxItemsPerCell();
    }

    public void eat(Animal animal, List<GameObject> gameObjects) {
        int possibility = 0;
        GameObject prey = null;
        int attemptsToHunt = 3; //only 3 attempts are given for hunting, if no prey is found then animal stays hungry
        while (attemptsToHunt > 0) {
            int randomForChoosingPrey = new Random().nextInt(gameObjects.size());
            prey = gameObjects.get(randomForChoosingPrey);
            possibility = appService.getIntProperty(animal.getClass().getSimpleName() + ".canEat." + prey.getClass().getSimpleName());
            if (possibility > 0)
                break;
            attemptsToHunt--;
        }
        if (canEat(possibility)) {
            prey.setIsDead(true);
            animal.setKilosEaten(prey.getWeight());
            if (animal.getKilosEaten() < animal.getMinKilosForSaturation()) {
                animal.setDaysWithoutFood(animal.getDaysWithoutFood() + 1);
            } else {
                animal.setDaysWithoutFood(0);
            }
        } else {
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
            newBornAnimal = createAnimal(animalClass);
            if (newBornAnimal != null && canGameObjectBeOnField(newBornAnimal, gameObjects)) {
                GameField.getInstance().getGameObjectsOnCellViaPosition(currentPosition).add(newBornAnimal);
            }
        }
    }
}
