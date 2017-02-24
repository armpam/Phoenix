package com.phoenix.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.phoenix.game.Screens.GameScreen;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static final int WIDTH = 800, HEIGHT = 480; //Resolución del juego
	
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
