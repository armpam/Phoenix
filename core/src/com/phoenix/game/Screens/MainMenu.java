package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.AnimationHandler;
import com.phoenix.game.Tools.ScreenHandler;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 3/31/2018.
 */

public class MainMenu implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Label.LabelStyle font;
    private Skin skin;
    private Texture bgTexture;
    private Table mainTable;
    private Table optionsTable;
    private Table statsTable;

    private TextButton backLabel;
    private Label descriptionLabel;

    private TextButton stateLabel;
    private TextButton itemsLabel;
    private TextButton equipmentLabel;
    private TextButton saveLabel;

    private Label lifeLabel;
    private Label manaLabel;
    private Label lvlLabel;
    private Label xpLabel;

    private MainCharacter mc;

    private GameScreen previousScreen;
    private MainMenu screen = this;

    public MainMenu(Game game, MainCharacter mc, GameScreen previousScreen){
        this.game = game;
        this.mc = mc;
        this.skin = AnimationHandler.getAnimationHandler().getSkin();
        this.previousScreen = previousScreen;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        Gdx.input.setInputProcessor(stage);
        bgTexture = new Texture(Gdx.files.internal("backgrounds/start_background.jpg"));

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE );
        mainTable = new Table();
        optionsTable = new Table(skin);
        optionsTable.setColor(Color.DARK_GRAY);
        statsTable = new Table();

        descriptionLabel = new Label("Descripción", skin);

        initStatsTable(mc);
        initOptionsTable();

        optionsTable.add(stateLabel).expandX().padBottom(40).width(130).height(60);
        optionsTable.row().padBottom(40).width(130).height(60);
        optionsTable.add(itemsLabel);
        optionsTable.row().padBottom(40).width(130).height(60);
        optionsTable.add(equipmentLabel);
        optionsTable.row().padBottom(40).width(130).height(60);
        optionsTable.add(saveLabel);

        statsTable.add(lifeLabel).padBottom(20).padLeft(20);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(manaLabel);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(lvlLabel);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(xpLabel);

        mainTable.add(new Image(new Texture(Gdx.files.internal("mc.jpg")))).height(150).padLeft(100).padBottom(100);
        mainTable.add(statsTable).left().expand().padBottom(60);
        mainTable.add(optionsTable).width(Game.WIDTH / 4);
        mainTable.row();
        mainTable.add(descriptionLabel).height(Game.HEIGHT / 8).colspan(2);
        mainTable.add(backLabel).height(60).width(130);
        mainTable.setFillParent(true);

        stage.addActor(mainTable);
    }

    private void initOptionsTable(){
        backLabel = new TextButton("Atrás", skin);
        backLabel.setColor(Color.DARK_GRAY);
        backLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                screen.dispose();
                ScreenHandler.getScreenHandler().setScreen(previousScreen);
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/back.wav", Sound.class).play(Game.volume);
                previousScreen.getController().resetIP();
                previousScreen.getController().resetBag();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Vuelve a la pantalla de juego");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });
        stateLabel = new TextButton("Estado", skin);
        stateLabel.setColor(Color.DARK_GRAY);
        stateLabel.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new StatsMenu(game, screen, mc));
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Muestra el estado del jugador y sus atributos");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        itemsLabel = new TextButton("Objetos", skin);
        itemsLabel.setColor(Color.DARK_GRAY);
        itemsLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new ItemsMenu(game, screen, mc));
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Muestra los objetos en la bolsa");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        equipmentLabel = new TextButton("Equipo", skin);
        equipmentLabel.setColor(Color.DARK_GRAY);
        equipmentLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new EquipmentMenu(game, screen, mc));
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Muestra el equipo en la bolsa");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        saveLabel = new TextButton("Guardar", skin);
        saveLabel.setColor(Color.DARK_GRAY);
        saveLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                System.out.println("jaja");
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/error.wav", Sound.class).play(Game.volume);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Guarda la partida");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });
    }

    private void initStatsTable(MainCharacter mc){
        lifeLabel = new Label("Vida: " + mc.getLife() + "/ " + mc.getMaxLife(), skin);
        manaLabel = new Label("Maná: " + mc.getMana() + "/ " + mc.getMaxMana(), skin);
        lvlLabel = new Label("Nivel: " + mc.getLevel(), skin);
        xpLabel = new Label("Experiencia: " + mc.getCurrentExp() + "/ " + mc.getXpGoal(), skin);
    }

    public void resetIP(){
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
        game.batch.draw(bgTexture, 0, 0, Game.WIDTH, Game.HEIGHT);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {viewport.update(width, height);}

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
