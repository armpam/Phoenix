package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/24/2017.
 */

public class MainFireball extends Sprite {

    private GameScreen screen;
    private World world;

    private float stateTime;

    private boolean destroyed; //Está la bola destruida
    private boolean setToDestroy; //Marca la bola para que se destruya

    private enum MovState {UP, DOWN, LEFT, RIGHT};
    private MovState direction; // Dirección de la bola

    private final int TEXT_WIDTH = 64;
    private final int TEXT_HEIGHT = 64;

    private final int FB_SPEED = 2; //Velocidad de la bola

    Body b2body;
    Fixture fixture;

    public MainFireball(GameScreen gscreen, float x, float y, String direction ){

        this.screen = gscreen;
        this.direction = intDirection(direction);
        this.world = screen.getWorld();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM); //Posición en la que dibujar y tamaño del sprite

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
                Game.MC_BIT | Game.ENEMY_BIT;

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
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt, direction));

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

    private TextureRegion getFrame(float delta, MovState state){

        TextureRegion region = new TextureRegion();

        switch(state){

            case UP:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getFanim_up().getKeyFrame(stateTime, true);
                break;
            case DOWN:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getFanim_down().getKeyFrame(stateTime, true);
                break;
            case LEFT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getFanim_left().getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getFanim_right().getKeyFrame(stateTime, true);
                break;
        }

        stateTime = stateTime + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;

    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
