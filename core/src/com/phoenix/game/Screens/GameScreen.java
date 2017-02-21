package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.phoenix.game.Scenes.Main_UI;

/**
 * Created by Alesander on 2/21/2017.
 */

public class GameScreen implements Screen {

    private Game game;
    private Texture texture;
    private OrthographicCamera cam;
    private Viewport gamePort;
    private Main_UI UI;

    public GameScreen(Game game){
        this.game = game;
        this.cam = new OrthographicCamera();
        this.gamePort = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
        this.UI = new Main_UI(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(UI.stage.getCamera().combined);
        UI.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
