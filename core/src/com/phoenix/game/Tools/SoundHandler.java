package com.phoenix.game.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by alesd on 3/20/2018.
 */

public class SoundHandler implements Disposable {

    private static SoundHandler mSoundHandler;
    private AssetManager assetManager;

    private Music currentTheme;

    private SoundHandler(){

        init();
    }

    public static SoundHandler getSoundHandler(){
        if(mSoundHandler == null){
            mSoundHandler = new SoundHandler();
        }
        return mSoundHandler;
    }

    private void init(){

        assetManager = new AssetManager();
        assetManager.load("audio/themes/intro_theme.ogg", Music.class);
        assetManager.load("audio/themes/forest_theme.ogg", Music.class);
        assetManager.load("audio/themes/dungeon_theme.ogg", Music.class);
        assetManager.load("audio/themes/town_theme.ogg", Music.class);
        assetManager.load("audio/themes/platformer_theme.ogg", Music.class);
        assetManager.load("audio/sounds/openChest.ogg", Music.class);
        assetManager.load("audio/sounds/coin.ogg", Music.class);
        assetManager.load("audio/sounds/hurt.ogg", Music.class);
        assetManager.load("audio/sounds/hit.ogg", Music.class);
        assetManager.load("audio/sounds/fireball.wav", Music.class);
        assetManager.load("audio/sounds/iceball.wav", Music.class);
        assetManager.load("audio/sounds/lightningball.wav", Music.class);
        assetManager.load("audio/sounds/jump.wav", Music.class);
        assetManager.finishLoading();


    }

    public void playIntroTheme(){
        if(currentTheme != null) {
            currentTheme.stop();
            currentTheme = assetManager.get("audio/themes/intro_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
        else{
            currentTheme = assetManager.get("audio/themes/intro_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
    }

    public void playForestTheme(){
        if(currentTheme != null) {
            currentTheme.stop();
            currentTheme = assetManager.get("audio/themes/forest_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
    }

    public void playDungeonTheme(){
        if(currentTheme != null) {
            currentTheme.stop();
            currentTheme = assetManager.get("audio/themes/dungeon_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
    }

    public void playTownTheme(){
        if(currentTheme != null) {
            currentTheme.stop();
            currentTheme = assetManager.get("audio/themes/town_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
    }

    public void playPlatformerTheme(){
        if(currentTheme != null) {
            currentTheme.stop();
            currentTheme = assetManager.get("audio/themes/platformer_theme.ogg", Music.class);
            currentTheme.setLooping(true);
            currentTheme.play();
        }
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
