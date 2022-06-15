package com.javarush.island.game;

import com.javarush.island.gameobjects.GameField;
import com.javarush.island.services.GameService;
import com.javarush.island.services.Statistics;

public class Game {
    private final GameField gameField;
    private final GameService gameService;

    public Game() {
        this.gameField = GameField.getInstance();
        this.gameService = GameService.getInstance();
    }

    public void startGame() {
        initializeGameField();
//        this.gameField.checkForFilling();
        for (int i = 0; i < 3; i++) {
            animalsDoTheirActions();
            removeDeadGameObjects();
            plantsDoTheirActions();
            getStatistics();
        }

//        while (gameService.isAnimalsExist()) {
//        }
    }

    public void initializeGameField() {
        gameField.createGameField();
    }

    public void animalsDoTheirActions() {
//        System.out.println("++++++++++++++++++Animals do their actions++++++++++++++");
        gameService.doAction();
//        gameField.checkForFilling();
    }

    public void plantsDoTheirActions() {
//        System.out.println("++++++++++++++++++Plants do their actions++++++++++++++");
        gameField.setPlantsOnGameField();
//        gameField.checkForFilling();
    }

    public void removeDeadGameObjects() {
        gameService.refreshGameField();
//        gameField.checkForFilling();
    }

    public void getStatistics() {
        Statistics statistics = new Statistics();
        statistics.getCurrentAmountOfObjects();
    }

}
