package com.phoenix.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * Created by alesd on 2/23/2017.
 */

public class MainCharacter extends Sprite {

    private World world;
    public Body b2body;
    protected Fixture fixture;
    //Posición inicial del jugador
    private int x =50;
    private int y = 50;

    private Texture mainTexture;
    private final int MAIN_TEXT_WIDTH = 64, MAIN_TEXT_HEIGHT =64 ; //Altura y anchura de los sprites del spritesheet del MC
    private TextureRegion idle; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))
    private TextureRegion idleLeft; // Posturas sin hacer nada mirando hacia distintos lados
    private TextureRegion idleDown;
    private TextureRegion idleRight;
    private enum MovState {UP, DOWN, LEFT, RIGHT, IDLE}; //Hacia dónde se mueve
    private MovState currentState; //Estado actual del personaje
    private MovState previousState;// Estado en el que se ha quedado al pararse

    //Animaciones del movimiento en 4 direcciones
    private Animation runLeft;
    private Animation runRight;
    private Animation runUp;
    private Animation runDown;

    private float stateTimer;

    public MainCharacter(World world){
        this.world = world;
        defineMainCharacter();
        currentState = MovState.IDLE;
        stateTimer = 0;

        mainTexture = new Texture(Gdx.files.internal("main.png")); //La imagen con todos los sprites
        initAnimations();

        idle = new TextureRegion(mainTexture, 0 , 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT ); //Cogemos el sprite del punto 0,0 con W y H 64
        idleLeft = new TextureRegion(mainTexture, 0 , 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleRight = new TextureRegion(mainTexture, 0 , 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleDown = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        setBounds(0, 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT);
        setRegion(idle); //Le dices que región dibujar (Hace falta para que el método draw sepa qué dibujar)
    }

    //Actualiza la posición de dónde dibujamos al jugador (sigue a la cámara)
    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar
    }

    // Inicializa las animaciones del personaje principal
    private void initAnimations(){
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

    //Da el frame a dibujar según el estado del jugador
    private TextureRegion getFrame(float delta){
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
    private MovState getState(){
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

    private void defineMainCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody; //El jugador es dinámico, se mueve

        b2body = world.createBody(bdef);

        //El Body del jugador es un círculo de radio 5
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData("mcharacter"); //Se crea la fixture y la asignamos el nombre mcharacter para la COLISIÓN
    }

}
