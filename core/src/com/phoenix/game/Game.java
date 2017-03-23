package com.phoenix.game;

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
	
	@Override
	public void create () {
		batch = new SpriteBatch();
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
