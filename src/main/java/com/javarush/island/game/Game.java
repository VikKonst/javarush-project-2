package com.javarush.island.game;

import com.javarush.island.gameobjects.GameField;
import com.javarush.island.services.AppService;
import com.javarush.island.services.GameService;
import com.javarush.island.services.StatisticsService;

public class Game {
    private final AppService appService;
    private final GameService gameService;
    private final StatisticsService statisticsService;

    public Game(AppService appService, GameService gameService, StatisticsService statisticsService) {
        this.appService = appService;
        this.gameService = gameService;
        this.statisticsService = statisticsService;
    }

    public void startGame() {
        initializeGameField();
        //this cycle will be replaced, the game shall continue until all animals are dead or until user wants to stop
        for (int i = 0; i < 3; i++) {
            animalsDoTheirActions();
            getCurrentAmountOfDiedObjects();
            removeDeadGameObjects();
            plantsDoTheirActions();
            getCurrentAmountOfObjectsOnField();
        }
    }

    public void initializeGameField() {
        GameField gameField = GameField.getInstance();
        int height = appService.getIntProperty("gameField.height");
        int width = appService.getIntProperty("gameField.width");
        gameField.createGameField(height, width);
        gameService.fillFieldsWithAnimals();
        gameService.fillFieldsWithPlants();
        System.out.println("animals created");

    }

    public void animalsDoTheirActions() {
        gameService.doAction();
    }

    public void plantsDoTheirActions() {
        gameService.fillFieldsWithPlants();
    }

    public void removeDeadGameObjects() {
        gameService.refreshGameField();
    }

    public void getCurrentAmountOfObjectsOnField() {
        statisticsService.getCurrentAmountOfObjects();
    }

    public void getCurrentAmountOfDiedObjects() {
        statisticsService.getCurrentAmountOfDeadObjects();
    }

}
