package com.phoenix.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;


/**
 * Created by alesd on 2/21/2017.
 */

public class Main_UI implements Disposable {
    public Stage stage;
    private Viewport hudPort;
    private OrthographicCamera cam;
    private Table hudTable;
    private BitmapFont hudFont;
    private Label.LabelStyle hudLabelStyle;

    private Integer life;
    private Integer mana;
    private static Integer score;

    Label lifeLabel;
    Label manaLabel;
    static Label scoreLabel;


    public Main_UI(SpriteBatch batch){
        cam = new OrthographicCamera();
        hudPort = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
        stage = new Stage(hudPort, batch);
        hudFont = new BitmapFont();
        hudLabelStyle = new Label.LabelStyle(hudFont, Color.WHITE);

        life = 1000;
        mana = 200;
        score = 0;

        hudTable = new Table();
        hudTable.top();
        hudTable.left();
        hudTable.setFillParent(true);
        lifeLabel = new Label("Vida: " + Integer.toString(life), hudLabelStyle);
        manaLabel = new Label("Maná: " + Integer.toString(mana), hudLabelStyle);
        scoreLabel = new Label("Puntuación: " + Integer.toString(score), hudLabelStyle);

        hudTable.add(lifeLabel).padLeft(20);
        hudTable.row();
        hudTable.add(manaLabel).padLeft(20);
        hudTable.row();
        hudTable.add(scoreLabel).padLeft(20);

        stage.addActor(hudTable);

    }

    public static void addScore(int sum){
        score = score + sum;
        scoreLabel.setText("Puntuación: " + Integer.toString(score));
    }

    public int getScore(){
        return score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
