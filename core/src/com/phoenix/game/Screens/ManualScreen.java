package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.AnimationHandler;
import com.phoenix.game.Tools.ScreenHandler;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 4/7/2018.
 */

public class ManualScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;
    private Texture bg;

    private Skin skin;

    private Table table;
    private TextButton backButton;

    public ManualScreen(Game game, final SettingsScreen sc){
        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        skin = AnimationHandler.getAnimationHandler().getSkin();
        table = new Table(skin);
        backButton = new TextButton("Menu de Ajustes", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                dispose();
                ScreenHandler.getScreenHandler().setScreen(sc);
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/back.wav", Sound.class).play(Game.volume);
                sc.resetIp();
            }
        });

        Gdx.input.setInputProcessor(stage);

        table.center();
        table.left();
        table.padLeft(50);
        table.padBottom(100);
        table.setFillParent(true);
        table.add(new Label("Controles", skin)).align(Align.left);
        table.row();
        table.add(new Label("Flechas de movimiento - Moverse", skin)).align(Align.left);
        table.row();
        table.add(new Label("1 - Bola de fuego: Alto dano y alto consumo de mana", skin)).align(Align.left);
        table.row();
        table.add(new Label("2 - Bola de hielo: Ralentiza al enemigo y consumo medio de mana", skin)).align(Align.left);
        table.row();
        table.add(new Label("3 - Bola de rayo: Dano medio y consumo bajo de mana", skin)).align(Align.left);
        table.row();
        table.add(new Label("4 - Usar pocion de vida", skin)).align(Align.left);
        table.row();
        table.add(new Label("5 - Usar pocion de mana", skin)).align(Align.left);
        table.row();
        table.add(new Label("I - Abrir menu", skin)).align(Align.left);
        table.row();
        table.add(new Label("Espacio (en modo sidescroll) - Saltar", skin)).align(Align.left);
        table.row();
        table.add(backButton).height(80).width(200).padTop(50).padLeft(200);

        bg = new Texture("backgrounds/background_2.png");

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
        stage.dispose();
    }
}
