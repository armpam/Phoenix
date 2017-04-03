package com.phoenix.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;

/**
 * Created by alesd on 4/1/2017.
 */

public class StartScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Label.LabelStyle font;
    private Table table;

    public StartScreen(Game game){

        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Game)game).batch );

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE );
        table = new Table();
        table.setFillParent(true);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
