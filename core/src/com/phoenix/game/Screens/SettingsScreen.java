package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;

/**
 * Created by alesd on 4/6/2018.
 */

public class SettingsScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;
    private Texture bg;

    private Table table;
    private Slider audioSlider;
    private Slider difficultySlider;
    private Skin skin;
    private Label audioLabel;
    private Label difficultyLabel;

    private int audioValue;
    private int difficultyValue;

    public SettingsScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        audioLabel = new Label("Intensidad del audio: ", skin);
        difficultyLabel = new Label("Nivel de dificultad: ", skin);

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.center();
        table.setFillParent(true);
        audioSlider = new Slider(0,100,10,false, skin);
        audioSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                audioValue =(int)audioSlider.getValue();
            }
        });
        difficultySlider = new Slider(1,3,1,false, skin);
        difficultySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyValue = (int)difficultySlider.getValue();
            }
        });

        bg = new Texture("backgrounds/background_2.png");

        table.add(audioLabel);
        table.add(audioSlider);
        table.row();
        table.add(difficultyLabel);
        table.add(difficultySlider);
        table.setDebug(true);

        stage.addActor(table);
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

    }
}
