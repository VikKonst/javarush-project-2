package com.javarush.island.game;

import com.javarush.island.tools.InitializationTools;

public class Main {
    public static void main(String[] args) {
        Game game = InitializationTools.createClass(Game.class);
        game.startGame();
    }
}
