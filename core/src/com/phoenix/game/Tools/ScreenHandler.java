package com.phoenix.game.Tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Screens.StartScreen;

import sun.applet.Main;

/**
 * Created by alesd on 3/13/2018.
 */

public class ScreenHandler implements Disposable {

    private static ScreenHandler mScreenHandler;
    public com.phoenix.game.Game game;
    public Screen currentScreen;
    public String previousMap;

    private ScreenHandler(){

    }

    public void initialize(com.phoenix.game.Game game){
        this.previousMap = "none";
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
        currentScreen = new GameScreen(game, "forest_1.tmx", false);
        game.setScreen(currentScreen);
        SoundHandler.getSoundHandler().playForestTheme();
        System.gc();
    }

    public void setGameScreenBack(MainCharacter mc){
        previousMap = (String)((GameScreen) currentScreen).getMap().getProperties().get("name");
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "forest_1.tmx", mc, false);
        game.setScreen(currentScreen);
        ((GameScreen) currentScreen).setRepositionFlag();
        SoundHandler.getSoundHandler().playForestTheme();
        System.gc();
    }

    public void setDungeonScreen(MainCharacter mc){
        previousMap = (String)((GameScreen) currentScreen).getMap().getProperties().get("name");
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "cave_1.tmx", mc, false);
        game.setScreen(currentScreen);
        ((GameScreen) currentScreen).setRepositionFlag();
        SoundHandler.getSoundHandler().playDungeonTheme();
        System.gc();
    }

    public void setLoginScreen(){
        currentScreen = new StartScreen(game);
        game.setScreen(currentScreen);
        SoundHandler.getSoundHandler().playIntroTheme();
        System.gc();
    }

    public void setSideScrollScreen(MainCharacter mc){
        previousMap = (String)((GameScreen) currentScreen).getMap().getProperties().get("name");
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "sidescroll_1.tmx", mc, true);
        game.setScreen(currentScreen);
        ((GameScreen) currentScreen).setRepositionFlag();
        SoundHandler.getSoundHandler().playPlatformerTheme();
        System.gc();
    }

    public void setCityScreen(MainCharacter mc){
        previousMap = (String)((GameScreen) currentScreen).getMap().getProperties().get("name");
        currentScreen.dispose();
        currentScreen = new GameScreen(game, "city_1.tmx", mc, false);
        game.setScreen(currentScreen);
        ((GameScreen) currentScreen).setRepositionFlag();
        SoundHandler.getSoundHandler().playTownTheme();
        System.gc();
    }

    public String getPreviousMap(){
        return previousMap;
    }

    @Override
    public void dispose() {
        currentScreen.dispose();
    }
}
