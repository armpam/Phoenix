package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    protected float SCSpeed; //Velocidad del enemigo en persecución
    protected boolean fight = false; //Indica si el enemigo está en modo lucha
    protected boolean retreatFlag = false;
    protected boolean changeDirections = false;
    protected float AGGRO; //Distancia para que empieze el modo lucha
    protected float movSpeed;
    protected String direction;

    protected final int TEXT_WIDTH = 64;
    protected final int TEXT_HEIGHT = 64;

    protected float initialX;
    protected float initialY;
    protected Vector2 initialVector;

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

    protected MapObject object;

    public Enemy(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        this.screen = gscreen;
        this.world = screen.getWorld();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.initialX = x;
        this.initialY = y;
        this.object = object;
        this.initialVector = new Vector2(x, y);

        shape = new PolygonShape();

        bdef = new BodyDef();
        fdef = new FixtureDef();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM);

        define();
        getDirection(); //Devuelve sobre qué eje se debe mover el cuerpo

        setCategoryFilter(Game.ENEMY_BIT);
        fixture.setUserData(this);
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

    protected void move(){
        if(this.body.getPosition().dst2(initialVector) < 10 && !retreatFlag) {
            if (direction.equals("horizontal") && (body.getPosition().dst(screen.getMcharacter().b2body.getPosition())) > AGGRO) {
                this.body.setLinearVelocity(movSpeed, 0);
                if(body.getPosition().dst2(initialVector) > 5 && !changeDirections){
                    reverseVelocity();
                    changeDirections = true;
                }
                if((int) body.getPosition().x == (int) initialX && (int)body.getPosition().y == (int) initialY) {
                    changeDirections = false;
                }
            } else if (direction.equals("vertical")) {
                this.body.setLinearVelocity(0, movSpeed);
            }
            else{
                chase(screen.getMcharacter());
            }
        }
        else{
            if(!retreatFlag){
                retreatFlag = true;
            }
            else{
                if(body.getPosition().x < initialX && body.getLinearVelocity().x < 0.3){
                    body.applyLinearImpulse(SCSpeed, 0, initialX, initialY, true);
                }
                if(body.getPosition().x > initialX && body.getLinearVelocity().x > -0.3){
                    body.applyLinearImpulse(-SCSpeed, 0, initialX, initialY, true);
                }
                if(body.getPosition().y < initialY && body.getLinearVelocity().y < 0.3){
                    body.applyLinearImpulse(0, SCSpeed, initialX, initialY, true);
                }
                if(body.getPosition().y > initialY && body.getLinearVelocity().y > -0.3){
                    body.applyLinearImpulse(0, -SCSpeed, initialX, initialY, true);
                }
                if((int) body.getPosition().x == (int) initialX && (int)body.getPosition().y == (int) initialY){
                    retreatFlag = false;
                }
            }
        }
    }

    protected void chase(MainCharacter mc){

        if(body.getPosition().x < mc.b2body.getPosition().x && body.getLinearVelocity().x < 0.1){
            body.applyLinearImpulse(SCSpeed, 0, initialX, initialY, true);
        }
        if(body.getPosition().x > mc.b2body.getPosition().x && body.getLinearVelocity().x > -0.1){
            body.applyLinearImpulse(-SCSpeed, 0, initialX, initialY, true);
        }
        if(body.getPosition().y < mc.b2body.getPosition().y && body.getLinearVelocity().y < 0.1){
            body.applyLinearImpulse(0, SCSpeed, initialX, initialY, true);
        }
        if(body.getPosition().y > mc.b2body.getPosition().y && body.getLinearVelocity().y > -0.1){
            body.applyLinearImpulse(0, -SCSpeed, initialX, initialY, true);
        }
    }

    public void reverseVelocity(){
        movSpeed = -movSpeed;
    }

    protected void getDirection(){

        if(this.object.getProperties().get("direction").equals("horizontal")){
            direction = "horizontal";
        }
        else if(this.object.getProperties().get("direction").equals("vertical")){
            direction = "vertical";
        }
        else if(this.object.getProperties().get("direction").equals("none")){
            direction = "none";
        }
        else{
            direction = "none";
        }
    }
}
