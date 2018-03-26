package com.phoenix.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
 * Created by alesd on 3/23/2018.
 */

public class LightBall extends Sprite {

    private GameScreen screen;
    private World world;

    private boolean destroyed; //Está la bola destruida
    private boolean setToDestroy; //Marca la bola para que se destruya

    private Vector2 direction;

    private int damage;

    private float stateTime;

    private final int TEXT_WIDTH = 16;
    private final int TEXT_HEIGHT = 16;

    private Body b2body;
    private Fixture fixture;
    private BodyDef bdef;
    private FixtureDef fdef;

    public LightBall(GameScreen gscreen, float x, float y, int damage ){

        this.screen = gscreen;
        this.world = screen.getWorld();
        this.bdef = new BodyDef();
        this.fdef = new FixtureDef();
        this.damage = damage;

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM); //Posición en la que dibujar y tamaño del sprite
        setRegion(AnimationHandler.getAnimationHandler().getLightBall());

        define();
        direction = getDirection();
        b2body.setLinearVelocity(direction);
    }

    private void define(){

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked()){ //Si no hemos parado el mundo
            b2body = world.createBody(bdef);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Game.PPM);
        fdef.filter.categoryBits = Game.LIGHTBALL_BIT;
        fdef.filter.maskBits = Game.MC_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 0;
        fdef.density = 0;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this); //Clave para la colisión
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(AnimationHandler.getAnimationHandler().getLightBall());
        stateTime += delta;
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public Vector2 getDirection(){
        return screen.getMcharacter().b2body.getPosition().sub(b2body.getPosition()).nor().scl(2);
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public int getDamage(){return  damage;}
}
