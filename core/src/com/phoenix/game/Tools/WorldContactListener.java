package com.phoenix.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.phoenix.game.Entities.Chest;
import com.phoenix.game.Entities.Coin;
import com.phoenix.game.Entities.Enemy;
import com.phoenix.game.Entities.Ladder;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Entities.MainFireball;
import com.phoenix.game.Entities.MovingBlock;
import com.phoenix.game.Entities.Rock;
import com.phoenix.game.Entities.Sensor;
import com.phoenix.game.Entities.Skeleton;
import com.phoenix.game.Game;
import com.phoenix.game.Scenes.Main_UI;

import org.lwjgl.Sys;

import java.util.Random;

import sun.applet.Main;

/**
 * Created by alesd on 3/22/2017.
 */

//Esta clase permite saber que fixture (b2body) colisiona con qué fixture
public class WorldContactListener implements ContactListener {

    //Qué pasa cuando entran en contacto
    @Override
    public void beginContact(Contact contact) {
        // Las dos fixtures que colisionan
        Fixture fixB = contact.getFixtureB();
        Fixture fixA = contact.getFixtureA();

        handlePlayerCollision(fixA, fixB); //El jugador colisiona con algo
        handleFireBallCollision(fixA, fixB); // Una bola de fuego colisiona con algo
        handleMovingBlockCollision(fixA, fixB);
        handleEnemyCollision(fixA, fixB);

    }

    //Qué pasa cuando termina el contacto
    @Override
    public void endContact(Contact contact) {
        Fixture fixB = contact.getFixtureB();
        Fixture fixA = contact.getFixtureA();
        endPlayerCollision(fixA, fixB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void handlePlayerCollision(Fixture fixA, Fixture fixB) {

        if (fixA.getUserData() instanceof MainCharacter || fixB.getUserData() instanceof MainCharacter) {
            Fixture player; //Esta fixture será el jugador
            Fixture object; // Esta el objeto con el que choca

            if (fixA.getUserData() instanceof  MainCharacter) { //Asignamos las fixture de arriba
                player = fixA;
                object = fixB;
            } else {
                player = fixB;
                object = fixA;
            }

            if (object.getUserData() instanceof Chest) { //Si el objeto es de tipo Chest
                ((Chest) object.getUserData()).onPlayerHit(); //Sabemos que es un árbol asi que lo casteamos a árbol
            }

            if (object.getUserData() instanceof Coin){
                ((Coin) object.getUserData()).setToDestroy();
                ((MainCharacter) player.getUserData()).addMoney(((Coin) object.getUserData()).getValue());
                Main_UI.updateScore((MainCharacter) player.getUserData());
                SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/coin.ogg", Music.class).play();
            }

            if (object.getUserData() instanceof Rock) {
                ((Rock) object.getUserData()).onPlayerHit();
            }

            if(object.getUserData() instanceof Ladder){
                ((Ladder) object.getUserData()).onPlayerHit();
            }
        }
    }

    private void handleFireBallCollision(Fixture fixA, Fixture fixB) {

        if (fixA.getUserData() instanceof MainFireball || fixB.getUserData() instanceof MainFireball) {
            Fixture fireBall;
            Fixture object;

            if (fixA.getUserData() instanceof MainFireball) {
                fireBall = fixA;
                object = fixB;
            } else {
                fireBall = fixB;
                object = fixA;
            }

            if (object.getUserData() instanceof Chest) { //Si el objeto es de tipo Chest
                ((Chest) object.getUserData()).onFireBallHit(); //Sabemos que es un cofre asi que lo casteamos a cofre
            }

            ((MainFireball) fireBall.getUserData()).setToDestroy(); //Si choca con algo la marcamos para que se destruya
        }
    }

    private void handleMovingBlockCollision(Fixture fixA, Fixture fixB) {

        if (fixA.getUserData() instanceof MovingBlock || fixB.getUserData() instanceof MovingBlock) {
            Fixture movingBlock;
            Fixture object;

            if (fixA.getUserData() instanceof MovingBlock) {
                movingBlock = fixA;
                object = fixB;
            } else {
                movingBlock = fixB;
                object = fixA;
            }
            if(movingBlock.getUserData() instanceof MovingBlock && object.getUserData() instanceof  MovingBlock){
                ((MovingBlock) movingBlock.getUserData()).reverseVelocity();
                ((MovingBlock) object.getUserData()).reverseVelocity();
            }else if (object.getUserData() instanceof Sensor){
                ((MovingBlock) movingBlock.getUserData()).reverseVelocity();
            }
        }
    }

    private void handleEnemyCollision(Fixture fixA, Fixture fixB) {

        if (fixA.getUserData() instanceof Enemy || fixB.getUserData() instanceof Enemy) {
            Fixture enemy;
            Fixture object;

            if (fixA.getUserData() instanceof Enemy) {
                enemy = fixA;
                object = fixB;
            } else {
                enemy = fixB;
                object = fixA;
            }
            if(object.getUserData() instanceof Rock){
                ((Enemy) enemy.getUserData()).reverseVelocity();
            }
        }
    }

    private void endPlayerCollision(Fixture fixA, Fixture fixB){

        if (fixA.getUserData() instanceof MainCharacter || fixB.getUserData() instanceof MainCharacter) {
            Fixture player; //Esta fixture será el jugador
            Fixture object; // Esta el objeto con el que choca

            if (fixA.getUserData() instanceof MainCharacter) { //Asignamos las fixture de arriba
                player = fixA;
                object = fixB;
            } else {
                player = fixB;
                object = fixA;
            }
            if(object.getUserData() instanceof Ladder){
                ((Ladder) object.getUserData()).leftLadder();
            }
        }
    }
}

