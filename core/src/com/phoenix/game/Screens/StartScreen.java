package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.ScreenHandler;

/**
 * Created by alesd on 4/1/2017.
 */

public class StartScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Label.LabelStyle font;
    private Table table;

    private Music introTheme;

    public StartScreen(Game game){

        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE );
        table = new Table();
        table.center();
        table.setFillParent(true);

        Label startLabel = new Label("Haz click para empezar", font);

        table.add(startLabel).expandX();

        stage.addActor(table);

        introTheme = Game.assetManager.get("audio/themes/intro_song.ogg", Music.class);
        introTheme.setLooping(true);
        introTheme.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            introTheme.stop();
            ScreenHandler.setGameScreen();
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

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
        stage.dispose();
    }
}
