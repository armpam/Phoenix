package com.phoenix.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

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

        if(fixA.getUserData() == "mcharacter" || fixB.getUserData() == "mcharacter"){
            Fixture player;
            Fixture object;

            if(fixA.getUserData() == "mcharacter"){
                player = fixA;
                object = fixB;
            }
            else{
                player = fixB;
                object = fixA;
            }

            if(object.getUserData()instanceof com.phoenix.game.Entities.Tree){ //Hay conflictos con la clase Tree de Java, así que detallamos bien de dónde viene
                ((com.phoenix.game.Entities.Tree)object.getUserData()).onPlayerHit(); //Sabemos que es un árbol asi que lo casteamos a árbol
            }
        }

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
}
