package com.phoenix.game.Tools;

import com.badlogic.gdx.Screen;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Screens.StartScreen;

import sun.applet.Main;

/**
 * Created by alesd on 3/13/2018.
 */

public class ScreenHandler {

    private static ScreenHandler mScreenHandler;
    public com.phoenix.game.Game game;
    public Screen currentScreen;

    private ScreenHandler(){

    }

    public void initialize(com.phoenix.game.Game game){
        this.game = game;
    }

    public static ScreenHandler getScreenHandler(){
        if(mScreenHandler == null){
            mScreenHandler = new ScreenHandler();
        }
        return mScreenHandler;
    }

    public void setGameScreen(){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "map_1.tmx", false);
        game.setScreen(currentScreen);
        System.gc();
    }

    public void setGameScreenBack(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "map_1.tmx", mc, false);
        game.setScreen(currentScreen);
        System.gc();
    }

    public void setDungeonScreen(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "cave_1.tmx", mc, false);
        game.setScreen(currentScreen);
        System.gc();
    }

    public void setLoginScreen(){
        currentScreen = new StartScreen(game);
        game.setScreen(currentScreen);
        System.gc();
    }

    public void setSideScrollScreen(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "sidescroll_1.tmx", mc, true);
        game.setScreen(currentScreen);
        System.gc();
    }

    public void setCityScreen(MainCharacter mc){
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "city_1.tmx", mc, false);
        game.setScreen(currentScreen);
        System.gc();
    }
}
