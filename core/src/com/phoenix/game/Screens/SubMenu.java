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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
 * Created by alesd on 4/4/2018.
 */

public abstract class SubMenu implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;
    private MainMenu prevMenu;
    protected MainCharacter mc;

    private Texture bg;
    protected Label.LabelStyle font;
    protected Skin skin;
    protected Label descriptionLabel;
    private TextButton backLabel;
    protected TextButton titleLabel;

    protected Table mainTable;
    protected Table downTable;
    protected Table infoTable;

    public SubMenu(Game game, MainMenu prevMenu, MainCharacter mc){
        this.game = game;
        this.prevMenu = prevMenu;
        this.mc = mc;
        skin = AnimationHandler.getAnimationHandler().getSkin();
        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE );
        titleLabel = new TextButton("Prueba", skin);
        titleLabel.setColor(Color.DARK_GRAY);

        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch );
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal("backgrounds/start_background.jpg"));

        init();
    }

    private void init(){
        descriptionLabel = new Label("Descripción", skin);
        backLabel = new TextButton("Atrás", skin);
        backLabel.setColor(Color.DARK_GRAY);
        backLabel.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                goBack();
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/back.wav", Sound.class).play(Game.volume);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Vuelve al menú anterior");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        mainTable = new Table(skin);
        downTable = new Table(skin);
        infoTable = new Table(skin);

        infoTable.padTop(50);
        infoTable.top().left();
        infoTable.add(titleLabel).expandX().colspan(2).fillX().height(50);
        infoTable.row();
        infoTable.add();
        infoTable.row();

        downTable.add(descriptionLabel).expand() ;
        downTable.add(backLabel).width(105).height(50);

        mainTable.add(infoTable).grow();
        mainTable.row();
        mainTable.add(downTable).bottom().fillX().height(50);
        mainTable.setFillParent(true);

        stage.addActor(mainTable);
    }

    private void goBack(){
        stage.dispose();
        ScreenHandler.getScreenHandler().setScreen(prevMenu);
        prevMenu.resetIP();
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
        viewport.update(width, height);
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
