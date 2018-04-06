package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.ScreenHandler;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 4/1/2017.
 */

public class StartScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Table table;

    private Image newGame;
    private Image loadGame;
    private Image settings;
    private Texture bg;

    public StartScreen(Game game){

        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );

        Gdx.input.setInputProcessor(stage);

        initButtons();

        bg = new Texture("backgrounds/background_2.png");

        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(newGame).expandX();
        table.row().padTop(50);
        table.add(loadGame);
        table.row().padTop(50);
        table.add(settings);

        stage.addActor(table);
    }

    private void initButtons(){
        newGame = new Image(new Texture("UI/nueva_partida.png"));
        newGame.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                dispose();
                ScreenHandler.getScreenHandler().setGameScreen();
            }
        });
        loadGame = new Image((new Texture("UI/cargar_partida.png")));
        settings = new Image(new Texture("UI/ajustes.png"));

        newGame.setSize(500,150);
        loadGame.setSize(500,150);
        settings.setSize(500,150);
        settings.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                dispose();
                ScreenHandler.getScreenHandler().setScreen(new SettingsScreen(game));
            }
        });
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.draw(bg, 0, 0, Game.WIDTH, Game.HEIGHT);
        game.batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
