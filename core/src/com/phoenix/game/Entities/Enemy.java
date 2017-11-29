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
    protected TextureRegion idle; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))
    protected TextureRegion idleLeft; // Posturas sin hacer nada mirando hacia distintos lados
    protected TextureRegion idleDown;
    protected TextureRegion idleRight;
    protected enum MovState {UP, DOWN, LEFT, RIGHT, IDLE}; //Hacia dónde se mueve
    protected MovState currentState; //Estado actual del personaje
    protected MovState previousState;

    protected Animation runLeft;
    protected Animation runRight;
    protected Animation runUp;
    protected Animation runDown;

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
        fixture = b2body.createFixture(fdef);

        idle = new TextureRegion(mainTexture, 0 , 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT ); //Cogemos el sprite del punto 0,0 con W y H 64
        idleLeft = new TextureRegion(mainTexture, 0 , 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleRight = new TextureRegion(mainTexture, 0 , 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleDown = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        setBounds(0, 0, MAIN_TEXT_WIDTH / Game.PPM, MAIN_TEXT_HEIGHT / Game.PPM);
        setRegion(idle);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar
    }

    // Inicializa las animaciones del personaje principal
    protected void initAnimations(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 512, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runUp = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 576, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runLeft = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 640, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runDown = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 704, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runRight = new Animation(0.1f, frames);
        frames.clear();
    }

    //Da el frame a dibujar según el estado del enemigo
    protected TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case UP:
                region = (TextureRegion)runUp.getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion)runDown.getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion)runLeft.getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion)runRight.getKeyFrame(stateTimer, true);
                break;
            default: //Caso IDLE
                if(previousState == MovState.DOWN){
                    region = idleDown; //Se queda quieto mirando abajo
                }
                else if(previousState == MovState.LEFT){
                    region = idleLeft;
                }
                else if(previousState == MovState.RIGHT){
                    region = idleRight;
                }
                else{
                    region = idle;
                }
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
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
