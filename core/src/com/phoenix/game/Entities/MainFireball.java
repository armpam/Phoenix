package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

/**
 * Created by alesd on 3/24/2017.
 */

public class MainFireball extends Sprite {

    private GameScreen screen;
    private World world;

    private Array<TextureRegion> frames;
    private Animation fireAnimation;

    private float stateTime;

    private boolean destroyed; //Está la bola destruida
    private boolean setToDestroy; //Marca la bola para que se destruya

    private enum MovState {UP, DOWN, LEFT, RIGHT};
    private MovState direction; // Dirección de la bola

    private final int FB_SPEED = 2; //Velocidad de la bola

    Body b2body;
    Fixture fixture;

    public MainFireball(GameScreen gscreen, float x, float y, String direction ){

        this.screen = gscreen;
        this.direction = intDirection(direction);
        this.world = screen.getWorld();
        //TODO ANIMACION DE LA BOLA AQUI
        setBounds(x, y, 6 / Game.PPM, 6 / Game.PPM); //Posición en la que dibujar y tamaño del sprite

        defineFireball();
    }

    private void defineFireball(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked()){ //Si no hemos parado el mundo
            b2body = world.createBody(bdef);
        }

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Game.PPM);
        fdef.filter.categoryBits = Game.MAIN_FBALL_BIT;
        fdef.filter.maskBits = Game.CHEST_BIT | Game.TREE_BIT |Game.ROCK_BIT |
                Game.MC_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 0;
        fdef.density = 0;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this); //Clave para la colisión

        if(direction == MovState.DOWN){
            b2body.setLinearVelocity(0,-FB_SPEED);
        }
        else if(direction == MovState.UP){
            b2body.setLinearVelocity(0,FB_SPEED);
        }
        else if(direction == MovState.LEFT){
            b2body.setLinearVelocity(-FB_SPEED,0);
        }
        else{
            b2body.setLinearVelocity(FB_SPEED,0);
        }


    }

    public void update(float dt){
        stateTime += dt;

        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

    }

    //Interpreta la dirección en la que mira el jugador
    private MovState intDirection(String direction){

        if(direction.equals("UP"))
            return MovState.UP;
        else if(direction.equals("LEFT"))
            return MovState.LEFT;
        else if(direction.equals("RIGHT"))
            return MovState.RIGHT;
        else
            return MovState.DOWN;
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
