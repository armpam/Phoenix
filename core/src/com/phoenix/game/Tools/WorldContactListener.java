package com.phoenix.game.Tools;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.phoenix.game.Entities.NPC;
import com.phoenix.game.Maps.Chest;
import com.phoenix.game.Maps.Coin;
import com.phoenix.game.Enemies.Enemy;
import com.phoenix.game.Maps.Ladder;
import com.phoenix.game.Maps.Tree;
import com.phoenix.game.Projectiles.IceBall;
import com.phoenix.game.Projectiles.LightBall;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Projectiles.LightningBall;
import com.phoenix.game.Projectiles.MainFireball;
import com.phoenix.game.Projectiles.MainProjectile;
import com.phoenix.game.Maps.MovingBlock;
import com.phoenix.game.Maps.Rock;
import com.phoenix.game.Maps.Sensor;
import com.phoenix.game.Scenes.Main_UI;

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
        handleProjectileCollision(fixA, fixB); // Una bola de fuego colisiona con algo
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
                ((MainCharacter) player.getUserData()).onChestHit((Chest)object.getUserData());
                ((Chest) object.getUserData()).onPlayerHit(); //Sabemos que es un árbol asi que lo casteamos a árbol
            }

            if (object.getUserData() instanceof Coin){
                ((Coin) object.getUserData()).setToDestroy();
                ((MainCharacter) player.getUserData()).onCoinHit(((Coin) object.getUserData()).getValue());
            }

            if (object.getUserData() instanceof Rock) {
                ((Rock) object.getUserData()).onPlayerHit();
            }

            if (object.getUserData() instanceof Tree) {
                ((Tree) object.getUserData()).onPlayerHit();
            }

            if(object.getUserData() instanceof Ladder){
                ((Ladder) object.getUserData()).onPlayerHit();
            }

            if(object.getUserData() instanceof LightBall){
                ((MainCharacter)player.getUserData()).onLightBallHit((LightBall) object.getUserData());
                ((LightBall) object.getUserData()).setToDestroy();
            }

            if(object.getUserData() instanceof Enemy){
                ((MainCharacter)player.getUserData()).onEnemyHit((Enemy)object.getUserData());
            }

            if(object.getUserData() instanceof NPC){
                ((NPC)object.getUserData()).onPlayerHit();
            }
        }
    }

    private void handleProjectileCollision(Fixture fixA, Fixture fixB) {

        if (fixA.getUserData() instanceof MainProjectile || fixB.getUserData() instanceof MainProjectile) {
            Fixture projectile;
            Fixture object;

            if (fixA.getUserData() instanceof MainProjectile) {
                projectile = fixA;
                object = fixB;
            } else {
                projectile = fixB;
                object = fixA;
            }

            if (object.getUserData() instanceof Chest) { //Si el objeto es de tipo Chest
                ((Chest) object.getUserData()).onFireBallHit(); //Sabemos que es un cofre asi que lo casteamos a cofre
            }

            if(object.getUserData() instanceof Enemy){
                ((Enemy) object.getUserData()).onProjectileHit((MainProjectile) projectile.getUserData());
            }

            ((MainProjectile) projectile.getUserData()).setToDestroy(); //Si choca con algo la marcamos para que se destruya
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
            if(object.getUserData() instanceof Rock || object.getUserData() instanceof Tree || object.getUserData() instanceof Chest){
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

