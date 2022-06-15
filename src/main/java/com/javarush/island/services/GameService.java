package com.javarush.island.services;

import com.javarush.island.gameobjects.GameField;
import com.javarush.island.gameobjects.GameObject;
import com.javarush.island.gameobjects.Position;
import com.javarush.island.species.animals.abstractItems.Animal;
import com.javarush.island.species.animals.animalService.AnimalService;
import com.javarush.island.species.animals.herbivorousAnimals.Caterpillar;

import java.util.*;

public class GameService {
    private static GameService INSTANCE;
    private AnimalService animalService;

    private GameService() {
        this.animalService = new AnimalService();
    }

    public static GameService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameService();
        }
        return INSTANCE;
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
        return GameField.getInstance().getGameFieldCellsMap().values()
                .stream()
                .map(ArrayList::listIterator)
                .anyMatch(gameObject -> gameObject instanceof Animal);
    }


//    public boolean isAnimalExistOnCell(List<GameObject> animalList) {
//        return animalList.stream().anyMatch(gameObject -> gameObject instanceof Animal);
//    }

    public void doAction() {
        HashMap<Position, ArrayList<GameObject>> tmp = copyOfGameField();
        Set<Map.Entry<Position, ArrayList<GameObject>>> entries = tmp.entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> entry : entries) {
            //System.out.println("---------------------------------------actions on position " + entry.getKey().getCoordinates());
            //System.out.println("Animals on position " + entry.getValue().size());
            Position currentPosition = entry.getKey();
            List<Animal> animals = new ArrayList<>();
            ArrayList<GameObject> value = entry.getValue();
            for (GameObject gameObject : value) {
                if (gameObject instanceof Animal) {
                    animals.add((Animal) gameObject);
                }
            }
            animals.forEach(animal -> doAnimalAction(animal, currentPosition, value));
        }
    }

    public void doAnimalAction(Animal animal, Position currentPosition, List<GameObject> gameObjects) {
        if (animal.getIsDead())
            return;
        int actionToBePerformed = new Random().nextInt(3);
//        System.out.println("action number " + actionToBePerformed);
        if (actionToBePerformed == 0) {
            if (!(animal instanceof Caterpillar)) {
                animalService.move(animal, currentPosition);
//                    System.out.println("***************** action move is done for animal " + animal.getClass().getSimpleName());
            }
        } else if (actionToBePerformed == 1) {
            animalService.eat(animal, gameObjects);
//                System.out.println("***************** action eat is done for animal " + animal.getClass().getSimpleName());
        } else {
            animalService.breed(animal, currentPosition, gameObjects);
//                System.out.println("***************** action breed is done for animal " + animal.getClass().getSimpleName());
        }
    }


    public HashMap<Position, ArrayList<GameObject>> copyOfGameField() {
        HashMap<Position, ArrayList<GameObject>> tmp = new HashMap<>();
        Set<Map.Entry<Position, ArrayList<GameObject>>> entries = GameField.getInstance().getGameFieldCellsMap().entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> entry : entries) {
            tmp.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return tmp;
    }

    public void refreshGameField() {
        markAnimalsDeadFromHunger();
        Set<Map.Entry<Position, ArrayList<GameObject>>> entries = GameField.getInstance().getGameFieldCellsMap().entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> entry : entries) {
            ArrayList<GameObject> value = entry.getValue();
            value.removeIf(GameObject::getIsDead);
        }
    }

    private void markAnimalsDeadFromHunger() {
        HashMap<Position, ArrayList<GameObject>> tmp = copyOfGameField();
        Set<Map.Entry<Position, ArrayList<GameObject>>> entries = tmp.entrySet();
        for (Map.Entry<Position, ArrayList<GameObject>> entry : entries) {
            ArrayList<GameObject> value = entry.getValue();
            for (GameObject gameObject : value) {
                if (gameObject instanceof Animal) {
                    if (((Animal) gameObject).getDaysWithoutFood() >= 3) { //animal can only live without food for 3 rounds of game
                        gameObject.setIsDead(true);
                    }
                }
            }
        }
    }
}
