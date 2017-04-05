package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

import java.util.Random;   //***************************

/**
 * Created by david on 05/03/2017.
 */

public abstract class Enemy {

    protected World world;
    public Body b2body;

    protected int x;
    protected int y;

    protected Fixture fixture;

    public Enemy(World world){
        this.world = world;
    }

    protected void defineEnemy(int x, int y){ //Este metodo lo definiremos en cada Enemigo, ya que cada Enemigo es diferente
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/ Game.PPM, y/ Game.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody; //El enemigo es dinámico, se mueve

        b2body = world.createBody(bdef);

        //El Body del Skeleton es un círculo de radio 10
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ Game.PPM);
        fdef.filter.maskBits = Game.MC_BIT;

        fdef.shape = shape;
        fdef.restitution = 0; //Hace que no rebote
        fdef.friction = 0;
        fixture = b2body.createFixture(fdef);
    }

    public void setCategoryFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
}
