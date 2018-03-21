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
        assetManager.load("audio/themes/overworld.ogg", Music.class);
        assetManager.load("audio/themes/intro_song.ogg", Music.class);
        assetManager.load("audio/sounds/openChest.ogg", Music.class);
        assetManager.load("audio/sounds/coin.ogg", Music.class);
        assetManager.finishLoading();
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
