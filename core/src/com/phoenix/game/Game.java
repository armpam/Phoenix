package com.phoenix.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static final int WIDTH = 800, HEIGHT = 480; //Resolución del juego

	public static final float PPM = 100; //Pixels por metro en box2d

	public static final short DEFAULT_BIT = 2;
	public static final short MC_BIT = 4;
	public static final short ROCK_BIT = 8;
	public static final short TREE_BIT = 16;
	public static final short CHEST_BIT = 32;
	public static final short DESTROYED_BIT = 64;
	public static final short MAIN_FBALL_BIT = 128;
	public static final short COIN_BIT = 256;
	public static final short ENEMY_BIT = 512;

	public static AssetManager assetManager; //El manager para la música - Static puede causar problemas (esperemos que no)
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        assetManager = new AssetManager();
        assetManager.load("audio/themes/overworld.ogg", Music.class);
		assetManager.load("audio/themes/intro_song.ogg", Music.class);
        assetManager.load("audio/sounds/openChest.ogg", Music.class);
		assetManager.load("audio/sounds/coin.ogg", Music.class);
        assetManager.finishLoading();

        setScreen(new StartScreen(this));
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
