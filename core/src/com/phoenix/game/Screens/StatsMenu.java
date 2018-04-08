package com.phoenix.game.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;

/**
 * Created by alesd on 4/5/2018.
 */

public class StatsMenu extends SubMenu {

    private Label lifeLabel;
    private Label manaLabel;
    private Label lvlLabel;
    private Label xpLabel;
    private Label apLabel;
    private Label dpLabel;

    public StatsMenu(Game game, MainMenu prevMenu, MainCharacter mc) {
        super(game, prevMenu, mc);
        titleLabel.setText("ESTADO");
        infoTable.add(new Label("", skin));
        infoTable.row();

        initLabels();

        infoTable.row();
        infoTable.add(lifeLabel);
        infoTable.add(manaLabel);
        infoTable.row();
        infoTable.add(lvlLabel);
        infoTable.add(xpLabel);
        infoTable.row();
        infoTable.add(apLabel);
        infoTable.add(dpLabel);
        infoTable.row();
    }

    private void initLabels(){
        lifeLabel = new Label("Vida: " + mc.getLife() + "/ " + mc.getMaxLife(), skin);
        manaLabel = new Label("Maná: " + mc.getMana() + "/ " + mc.getMaxMana(), skin);
        lvlLabel = new Label("Nivel: " + mc.getLevel(), skin);
        xpLabel = new Label("Experiencia: " + mc.getCurrentExp() + "/ " + mc.getXpGoal(), skin);
        apLabel = new Label("Puntos de Ataque: " + mc.getAp(), skin);
        dpLabel = new Label("Puntos de Defensa: " + mc.getDp(), skin);

        lifeLabel.addListener(new ClickListener(){

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Puntos de vida. Al llegar estos a 0 se termina el juego.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        manaLabel.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Puntos de maná. Recurso principal para realizar ataques.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        lvlLabel.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Nivel del jugador. Un mayor nivel aumenta todos los estados.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        xpLabel.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Experiencia del jugador. Al llegar al objetivo se sube de nivel.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        apLabel.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Puntos de ataque. Determinan el daño del jugador. Aumentados por el arma equipada.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });

        dpLabel.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                descriptionLabel.setText("Puntos de defensa. Determinan el daño recibido por el jugador. Aumentados por la armadura equipada.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                descriptionLabel.setText("Descripción");
            }
        });
    }
}
