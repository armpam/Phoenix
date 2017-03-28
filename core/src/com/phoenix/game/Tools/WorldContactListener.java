package com.phoenix.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Entities.Chest;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Entities.MainFireball;

import org.lwjgl.Sys;

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

    }

    //Qué pasa cuando termina el contacto
    @Override
    public void endContact(Contact contact) {

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
}
