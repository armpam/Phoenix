package com.phoenix.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.phoenix.game.Screens.GameScreen;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static final int WIDTH = 800, HEIGHT = 480; //Resolución del juego

	public static final short DEFAULT_BIT = 2;
	public static final short MC_BIT = 4;
	public static final short ROCK_BIT = 8;
	public static final short TREE_BIT = 16;
	public static final short CHEST_BIT = 32;
	public static final short DESTROYED_BIT = 64;

	public static AssetManager assetManager; //El manager para la música - Static puede causar problemas (esperemos que no)
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        assetManager = new AssetManager();
        assetManager.load("audio/themes/overworld.ogg", Music.class);
        assetManager.load("audio/sounds/openChest.ogg", Music.class);
        assetManager.finishLoading();

        setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
