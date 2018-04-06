package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.ScreenHandler;

/**
 * Created by alesd on 3/31/2018.
 */

public class MainMenu implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Label.LabelStyle font;
    private Texture bgTexture;
    private Table mainTable;
    private Table optionsTable;
    private Table statsTable;

    private Label backLabel;
    private Label descriptionLabel;

    private Label stateLabel;
    private Label itemsLabel;
    private Label equipmentLabel;
    private Label saveLabel;

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
        this.previousScreen = previousScreen;
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        Gdx.input.setInputProcessor(stage);
        bgTexture = new Texture(Gdx.files.internal("backgrounds/start_background.jpg"));

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE );
        mainTable = new Table();
        optionsTable = new Table();
        statsTable = new Table();

        descriptionLabel = new Label("Descripción", font);

        initStatsTable(mc);
        initOptionsTable();

        optionsTable.add(stateLabel).expandX().padBottom(70);
        optionsTable.row().padBottom(70);
        optionsTable.add(itemsLabel);
        optionsTable.row().padBottom(70);
        optionsTable.add(equipmentLabel);
        optionsTable.row().padBottom(70);
        optionsTable.add(saveLabel);
        optionsTable.setDebug(true);

        statsTable.add(lifeLabel).padBottom(20).padLeft(20);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(manaLabel);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(lvlLabel);
        statsTable.row().padBottom(20).padLeft(20);
        statsTable.add(xpLabel);

        mainTable.add(new Image(new Texture(Gdx.files.internal("mc.jpg")))).height(150).padLeft(100).padBottom(100);
        mainTable.add(statsTable).left().expand().padBottom(100);
        mainTable.add(optionsTable).width(Game.WIDTH / 4);
        mainTable.row();
        mainTable.add(descriptionLabel).height(Game.HEIGHT / 8).colspan(2);
        mainTable.add(backLabel);
        mainTable.setFillParent(true);

        stage.addActor(mainTable);
    }

    private void initOptionsTable(){
        backLabel = new Label("Atrás", font);
        backLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                screen.dispose();
                ScreenHandler.getScreenHandler().setScreen(previousScreen);
                previousScreen.getController().resetIP();
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
        stateLabel = new Label("Estado", font);
        stateLabel.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new StatsMenu(game, screen, mc));
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

        itemsLabel = new Label("Objetos", font);
        itemsLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new ItemsMenu(game, screen, mc));
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

        equipmentLabel = new Label("Equipo", font);
        equipmentLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ScreenHandler.getScreenHandler().setScreen(new EquipmentMenu(game, screen, mc));
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

        saveLabel = new Label("Guardar", font);
        saveLabel.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                System.out.println("jaja");
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
        lifeLabel = new Label("Vida: " + mc.getLife() + "/ " + mc.getMaxLife(), font);
        manaLabel = new Label("Maná: " + mc.getMana() + "/ " + mc.getMaxMana(), font);
        lvlLabel = new Label("Nivel: " + mc.getLevel(), font);
        xpLabel = new Label("Experiencia: " + mc.getCurrentExp() + "/ " + mc.getXpGoal(), font);
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
