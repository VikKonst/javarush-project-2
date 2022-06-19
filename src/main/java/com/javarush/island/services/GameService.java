package com.javarush.island.services;

import com.javarush.island.annotations.Injectable;;
import com.javarush.island.gameobjects.Cell;
import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.gameobjects.Position;
import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.herbivorousAnimals.Caterpillar;
import com.javarush.island.species.plants.Plant;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Injectable
public class GameService {
    private final AnimalService animalService;
    private final PlantService plantService;

    public GameService(AnimalService animalService, PlantService plantService) {
        this.animalService = animalService;
        this.plantService = plantService;
    }

    public void fillFieldsWithAnimals() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Cell> oddCells = new ArrayList<>();
        List<Cell> evenCells = new ArrayList<>();
        for (int i = 0; i < GameField.getInstance().getFieldCells().size(); i++) {
            if (i % 2 == 0) {
                evenCells.add(GameField.getInstance().getFieldCells().get(i));
            } else {
                oddCells.add(GameField.getInstance().getFieldCells().get(i));
            }
        }
        List<List<Cell>> cells = List.of(oddCells, evenCells);
        cells.forEach(list -> {
            FillCellTask fillCellTask = new FillCellTask(list);
            executorService.submit(fillCellTask);
            try {
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void fillFieldsWithPlants() {
        GameField.getInstance().getFieldCells().forEach(this :: setPlantsOnGameField);
    }

    public void setAnimalsOnGameField(Cell cell) {
        for (int i = 0; i < new Random().nextInt(GameField.MAX_AMOUNT_OF_ANIMALS_IN_CELL); i++) {
                Animal animal = animalService.createRandomAnimal();
                if (canGameObjectBeOnField(animal, cell.getObjectsOnCell())) {
                    cell.getObjectsOnCell().add(animal);
                }
            }
        }

        public void setPlantsOnGameField(Cell cell) {
            for (int i = 0; i < new Random().nextInt(GameField.MAX_AMOUNT_OF_PLANTS_IN_CELL); i++) {
                Plant plant = plantService.createNewPlant();
                if (canGameObjectBeOnField(plant, cell.getObjectsOnCell())) {
                    cell.getObjectsOnCell().add(plant);
                }
            }
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

    public boolean isAnimalsExist() {
        boolean isAnimalExist = false;
        for (Cell cell : GameField.getInstance().getFieldCells()) {
            for (GameObject gameObject : cell.getObjectsOnCell()) {
                if (gameObject instanceof Animal) {
                    isAnimalExist = true;
                    break;
                }
            }
        }
        return isAnimalExist;
    }


    public boolean isAnimalExistOnCell(List<GameObject> animalList) {
        return animalList.stream().anyMatch(gameObject -> gameObject instanceof Animal);
    }

    public void doAction() {
        for (Cell cell : new ArrayList<>(GameField.getInstance().getFieldCells())) {
            Position currentPosition = cell.getPosition();
            List<Animal> animals = new ArrayList<>();
            List<GameObject> gameObjects = cell.getObjectsOnCell();
            for (GameObject gameObject : gameObjects) {
                if (gameObject instanceof Animal) {
                    animals.add((Animal) gameObject);
                }
            }
            animals.forEach(animal -> doAnimalAction(animal, currentPosition, gameObjects));
        }
    }

    public void doAnimalAction(Animal animal, Position currentPosition, List<GameObject> gameObjects) {
        if (animal.getIsDead())
            return;
        int actionToBePerformed = new Random().nextInt(3);
        if (actionToBePerformed == 0) {
            if (!(animal instanceof Caterpillar)) {
                animalService.move(animal, currentPosition);
            }
        } else if (actionToBePerformed == 1) {
            animalService.eat(animal, gameObjects);
        } else {
            animalService.breed(animal, currentPosition, gameObjects);
        }
    }

    public void refreshGameField() {
        markAnimalsDeadFromHunger();
        for (Cell cell : GameField.getInstance().getFieldCells()) {
            List<GameObject> gameObjects = cell.getObjectsOnCell();
            gameObjects.removeIf(GameObject::getIsDead);
        }
    }

    private void markAnimalsDeadFromHunger() {
        for (Cell cell : GameField.getInstance().getFieldCells()) {
            List<GameObject> gameObjects = cell.getObjectsOnCell();
            gameObjects.forEach(gameObject -> {
                if (gameObject instanceof Animal && ((Animal) gameObject).getDaysWithoutFood() >= 3) { //animal can only live without food for 3 rounds of game
                    gameObject.setIsDead(true);
                }
            });
        }
    }

    private class FillCellTask implements Runnable {
        private final List<Cell> cells;
        public FillCellTask(List<Cell> cells) {
            this.cells = cells;
        }

        @Override
        public void run() {
            for (Cell cell : cells) {
                System.out.println("filling cell " + cell.getPosition().getCoordinates() + " with animals");
                setAnimalsOnGameField(cell);
            }
        }
    }
}
