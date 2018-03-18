package com.phoenix.game.Tools;

import com.badlogic.gdx.Screen;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Screens.StartScreen;

/**
 * Created by alesd on 3/13/2018.
 */

public class ScreenHandler {

    public static com.phoenix.game.Game game;
    public static Screen currentScreen;

    public ScreenHandler(){

    }

    public static void initialize(com.phoenix.game.Game game){
        ScreenHandler.game = game;
    }

    public static void setGameScreen(){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "map_1.tmx", false);
        game.setScreen(currentScreen);
    }

    public static void setGameScreenBack(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "map_1.tmx", mc, false);
        game.setScreen(currentScreen);
    }

    public static void setDungeonScreen(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "cave_1.tmx", mc, false);
        game.setScreen(currentScreen);
    }

    public static void setLoginScreen(){
        currentScreen = new StartScreen(game);
        game.setScreen(currentScreen);
    }

    public static void setSideScrollScreen(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "sidescroll_1.tmx", mc, true);
        game.setScreen(currentScreen);
    }
}
