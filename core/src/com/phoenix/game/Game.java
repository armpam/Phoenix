package com.phoenix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.phoenix.game.Screens.GameScreen;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render(); //Delega el renderizado a la pantalla que esté activa
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
