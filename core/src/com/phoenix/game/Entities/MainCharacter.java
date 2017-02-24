package com.phoenix.game.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by alesd on 2/23/2017.
 */

public class MainCharacter {

    private World world;
    public Body b2body;
    //Posición inicial del jugador
    private int x =50;
    private int y = 50;

    public MainCharacter(World world){
        this.world = world;
        defineMainCharacter();
    }

    public void defineMainCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody; //El jugador es dinámico, se mueve

        b2body = world.createBody(bdef);

        //El Body del jugador es un círculo de radio 5
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
