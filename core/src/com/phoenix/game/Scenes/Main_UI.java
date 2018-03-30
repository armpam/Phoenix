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
import com.phoenix.game.Entities.MainCharacter;
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

    private Label lifeLabel;
    private Label manaLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label hpPotLabel;
    private Label mpPotLabel;


    public Main_UI(SpriteBatch batch, MainCharacter mCharacter){
        cam = new OrthographicCamera();
        hudPort = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
        stage = new Stage(hudPort, batch);
        hudFont = new BitmapFont();
        hudLabelStyle = new Label.LabelStyle(hudFont, Color.WHITE);

        hudTable = new Table();
        hudTable.top();
        hudTable.left();
        hudTable.setFillParent(true);
        lifeLabel = new Label("Vida: " + Integer.toString(mCharacter.getLife()), hudLabelStyle);
        manaLabel = new Label("Maná: " + Integer.toString(mCharacter.getMana()), hudLabelStyle);
        scoreLabel = new Label("Dinero: " + Integer.toString(mCharacter.getMoney()), hudLabelStyle);
        levelLabel = new Label("Nivel: " + Integer.toString(mCharacter.getLevel()), hudLabelStyle);
        hpPotLabel = new Label("Pociones de vida: " + Integer.toString(mCharacter.getHpPot()), hudLabelStyle);
        mpPotLabel = new Label("Pociones de maná: " + Integer.toString(mCharacter.getMpPot()), hudLabelStyle);

        hudTable.add(lifeLabel).padLeft(20);
        hudTable.row();
        hudTable.add(manaLabel).padLeft(20);
        hudTable.row();
        hudTable.add(scoreLabel).padLeft(20);
        hudTable.row();
        hudTable.add(levelLabel).padLeft(20);
        hudTable.row();
        hudTable.add(hpPotLabel).padLeft(20);
        hudTable.row();
        hudTable.add(mpPotLabel).padLeft(20);

        stage.addActor(hudTable);

    }

    public void updateUI(MainCharacter mc){
        lifeLabel.setText("Vida: " + mc.getLife());
        manaLabel.setText("Maná: " + mc.getMana());
        levelLabel.setText("Nivel: " + mc.getLevel());
        scoreLabel.setText("Dinero: " + mc.getMoney());
    }

    public void updateLife(MainCharacter mc){
        lifeLabel.setText("Vida: " + mc.getLife());
    }

    public void updateMana(MainCharacter mc){
        manaLabel.setText("Maná: " + mc.getMana());
    }

    public void updateScore(MainCharacter mc){scoreLabel.setText("Dinero: " + mc.getMoney());}

    public void updateHpPots(MainCharacter mc){hpPotLabel.setText("Pociones de vida: " + mc.getHpPot());}

    public void updateMpPots(MainCharacter mc){mpPotLabel.setText("Pociones de maná: " + mc.getMpPot());}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
