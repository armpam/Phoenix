package com.phoenix.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.AnimationHandler;

import java.util.Random;   //***************************

/**
 * Created by alesd on 05/03/2017.
 */

public abstract class Enemy extends Sprite {

    protected World world;
    public Body b2body;

    protected int x;
    protected int y;

    protected Fixture fixture;
    BodyDef bdef = new BodyDef();

    protected Texture mainTexture;
    protected final int MAIN_TEXT_WIDTH = 64, MAIN_TEXT_HEIGHT =64 ; //Altura y anchura de los sprites del spritesheet del MC

    protected enum MovState {UP, DOWN, LEFT, RIGHT, IDLE}; //Hacia dónde se mueve
    protected MovState currentState; //Estado actual del personaje
    protected MovState previousState;

    protected float stateTimer;

    public Enemy(World world,  Texture mainTexture){
        this.world = world;
        this.mainTexture = mainTexture;
    }

    protected void defineEnemy(int x, int y){ //Este metodo lo definiremos en cada Enemigo, ya que cada Enemigo es diferente

        bdef.position.set(x/ Game.PPM, y/ Game.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody; //El enemigo es dinámico, se mueve

        b2body = world.createBody(bdef);

        //El Body del Skeleton es un círculo de radio 10
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ Game.PPM);
        fdef.filter.categoryBits = Game.ENEMY_BIT;
        fdef.filter.maskBits = Game.MC_BIT;

        fdef.shape = shape;
        fdef.restitution = 0; //Hace que no rebote
        fdef.friction = 0;
        fdef.density = 0;
        fixture = b2body.createFixture(fdef);

        setBounds(0, 0, MAIN_TEXT_WIDTH / Game.PPM, MAIN_TEXT_HEIGHT / Game.PPM);
    }

    //Devuelve el estado de movimiento del jugador (corriendo hacia la dcha/izquierda, quieto...)
    protected MovState getState(){
        if(b2body.getLinearVelocity().x < 0){ //Si la X disminuye es que está yendo hacia la izquierda
            previousState = MovState.LEFT;
            return previousState;
        }
        else if(b2body.getLinearVelocity().x > 0){
            previousState = MovState.RIGHT;
            return previousState;
        }
        else if(b2body.getLinearVelocity().y < 0){
            previousState = MovState.DOWN;
            return previousState;
        }
        else if(b2body.getLinearVelocity().y > 0){
            previousState = MovState.UP;
            return previousState;
        }
        else return MovState.IDLE;
    }

    protected void setCategoryFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
}
