package com.javarush.island.game;

import com.javarush.island.services.AppService;

public class Main {
    private static boolean dataLoaded = false;

    public static void main(String[] args) {
        initApplicationData();
        if (dataLoaded) {
            Game game = new Game();
            game.startGame();
        }
    }

    private static void initApplicationData() {
        System.out.println("Initializing application data...");
        AppService.loadProperties();
        dataLoaded = true;
    }
}
