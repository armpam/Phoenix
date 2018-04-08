package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.AnimationHandler;
import com.phoenix.game.Tools.ScreenHandler;
import com.phoenix.game.Tools.SoundHandler;

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
    private TextButton backButton;
    private TextButton manualButton;

    private SettingsScreen settingsScreen = this;

    private int audioValue;
    private int difficultyValue;

    public SettingsScreen(final Game game, final StartScreen sc) {
        this.game = game;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        skin = AnimationHandler.getAnimationHandler().getSkin();
        audioLabel = new Label("Intensidad del audio: ", skin);
        difficultyLabel = new Label("Nivel de dificultad: ", skin);

        Gdx.input.setInputProcessor(stage);

        backButton = new TextButton("Menu Principal", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                dispose();
                ScreenHandler.getScreenHandler().setScreen(sc);
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/back.wav", Sound.class).play(Game.volume);
                sc.resetIP();
            }
        });
        manualButton = new TextButton("Manual de Usuario", skin);
        manualButton.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new ManualScreen(game, settingsScreen));
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
            }
        });
        table = new Table();
        table.center();
        table.setFillParent(true);
        audioSlider = new Slider(0,100,10,false, skin);
        audioSlider.setValue(50);
        audioSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                audioValue =(int)audioSlider.getValue();
                switch(audioValue){
                    case 0:
                        Game.volume = 0.0f;
                        break;
                    case 10:
                        Game.volume = 0.1f;
                        break;
                    case 20:
                        Game.volume = 0.2f;
                        break;
                    case 30:
                        Game.volume = 0.3f;
                        break;
                    case 40:
                        Game.volume = 0.4f;
                        break;
                    case 50:
                        Game.volume = 0.5f;
                        break;
                    case 60:
                        Game.volume = 0.6f;
                        break;
                    case 70:
                        Game.volume = 0.7f;
                        break;
                    case 80:
                        Game.volume = 0.8f;
                        break;
                    case 90:
                        Game.volume = 0.9f;
                        break;
                    case 100:
                        Game.volume = 1.0f;
                        break;
                    default:
                        Game.volume = 0.5f;
                        break;
                }
                SoundHandler.getSoundHandler().setThemeVolume(Game.volume);
            }
        });
        difficultySlider = new Slider(1,3,1,false, skin);
        difficultySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyValue = (int)difficultySlider.getValue();
                Game.difficulty = difficultyValue;
            }
        });

        bg = new Texture("backgrounds/background_2.png");

        table.add(audioLabel).height(100);
        table.add(audioSlider);
        table.row();
        table.add(difficultyLabel).height(100);
        table.add(difficultySlider);
        table.row();
        table.add(manualButton).colspan(2).height(80).padTop(50).fillX();
        table.row();
        table.add(backButton).colspan(2).padTop(50).height(80).fillX();
        table.setDebug(true);

        stage.addActor(table);
    }

    public void resetIp(){
        Gdx.input.setInputProcessor(stage);
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
