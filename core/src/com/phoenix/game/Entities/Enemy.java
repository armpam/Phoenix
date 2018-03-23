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
    protected boolean retreatFlag = false;
    protected boolean changeDirections = false;
    protected float AGGRO; //Distancia para que empieze el modo lucha
    protected float CHASEDISTANCE;
    protected float movSpeed;
    protected String direction;

    protected final int TEXT_WIDTH = 64;
    protected final int TEXT_HEIGHT = 64;

    protected float initialX;
    protected float initialY;
    protected Vector2 initialVector;
    protected Vector2 directionVector;

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
        this.directionVector = new Vector2(25.2f, 8.96f);

        shape = new PolygonShape();

        bdef = new BodyDef();
        fdef = new FixtureDef();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM);

        define();
        getDirection(); //Devuelve sobre qué eje se debe mover el cuerpo

        setCategoryFilter(Game.ENEMY_BIT);
        fixture.setUserData(this);
        body.setActive(false);
    }

    private void define(){ //Este metodo lo definiremos en cada Enemigo, ya que cada Enemigo es diferente

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
        if(this.body.getPosition().dst2(initialVector) < CHASEDISTANCE && !retreatFlag) {
            if (direction.equals("horizontal") && (body.getPosition().dst(screen.getMcharacter().b2body.getPosition())) > AGGRO) {
                this.body.setLinearVelocity(movSpeed, 0);
                if(body.getPosition().dst2(initialVector) > 5 && !changeDirections){
                    reverseVelocity();
                    changeDirections = true;
                }
                if((int) body.getPosition().x == (int) initialX && (int)body.getPosition().y == (int) initialY) {
                    changeDirections = false;
                }
            } else if (direction.equals("vertical") && (body.getPosition().dst(screen.getMcharacter().b2body.getPosition())) > AGGRO) {
                this.body.setLinearVelocity(0, movSpeed);
                if(body.getPosition().dst2(initialVector) > 5 && !changeDirections){
                    reverseVelocity();
                    changeDirections = true;
                }
                if((int) body.getPosition().x == (int) initialX && (int)body.getPosition().y == (int) initialY) {
                    changeDirections = false;
                }
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
                directionVector = initialVector.sub(body.getPosition());
                directionVector.nor();
                body.setLinearVelocity(directionVector);
                initialVector.x = initialX;
                initialVector.y = initialY;
                if((int) body.getPosition().x == (int) initialX && (int)body.getPosition().y == (int) initialY) {
                    retreatFlag = false;
                }
            }
        }
    }

    private void chase(MainCharacter mc){
        body.setLinearVelocity((mc.b2body.getPosition().sub(body.getPosition())).nor());
    }

    public void reverseVelocity(){
        movSpeed = -movSpeed;
    }

    private void getDirection(){
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

    public Body getBody(){
        return body;
    }
}
