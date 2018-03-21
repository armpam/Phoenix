package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 05/03/2017.
 */

public abstract class Enemy extends Sprite {

    protected GameScreen screen;
    protected World world;

    protected final int TEXT_WIDTH = 64;
    protected final int TEXT_HEIGHT = 64;

    protected float movSpeed;
    protected float initialX;
    protected float initialY;

    protected Rectangle bounds;
    protected BodyDef bdef;
    protected PolygonShape shape;
    protected FixtureDef fdef;
    protected Body body;

    protected Fixture fixture;

    protected enum MovState {UP, DOWN, LEFT, RIGHT, IDLE}; //Hacia dónde se mueve
    protected MovState currentState; //Estado actual del personaje
    protected MovState previousState;

    protected float stateTimer;

    public Enemy(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        this.screen = gscreen;
        this.world = screen.getWorld();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.initialX = x;
        this.initialY = y;

        shape = new PolygonShape();

        bdef = new BodyDef();
        fdef = new FixtureDef();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM);

        define();

        setCategoryFilter(Game.ENEMY_BIT);
    }

    protected void define(){ //Este metodo lo definiremos en cada Enemigo, ya que cada Enemigo es diferente

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Game.PPM, (bounds.getY() + bounds.getHeight() / 2) / Game.PPM );

        body = world.createBody(bdef); //Crea el Body en el mundo

        shape.setAsBox(bounds.getWidth() / 2 / Game.PPM, bounds.getHeight() / 2 / Game.PPM); //Define la forma como una caja
        fdef.shape = shape;
        fdef.filter.maskBits = Game.ROCK_BIT | Game.MC_BIT;
        fixture = body.createFixture(fdef);
    }

    //Devuelve el estado de movimiento del jugador (corriendo hacia la dcha/izquierda, quieto...)
    protected MovState getState(){
        if(body.getLinearVelocity().x < 0){ //Si la X disminuye es que está yendo hacia la izquierda
            previousState = MovState.LEFT;
            return previousState;
        }
        else if(body.getLinearVelocity().x > 0){
            previousState = MovState.RIGHT;
            return previousState;
        }
        else if(body.getLinearVelocity().y < 0){
            previousState = MovState.DOWN;
            return previousState;
        }
        else if(body.getLinearVelocity().y > 0){
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

    public float getX(){
        return initialX;
    }


}
