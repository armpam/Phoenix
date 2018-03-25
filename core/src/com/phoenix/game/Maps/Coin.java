package com.phoenix.game.Maps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

import java.util.Random;

/**
 * Created by alesd on 3/31/2017.
 */

public class Coin extends Sprite {

    private GameScreen screen;
    private World world;

    private final int TEXT_WIDTH = 32;
    private final int TEXT_HEIGHT = 32;

    private float stateTime;

    private boolean destroyed;
    private boolean setToDestroy;

    //Puntuación mínima y máxima que da cada moneda
    private final int MIN_VALUE = 50;
    private final int MAX_VALUE = 200;
    private int value;

    Body b2body;
    Fixture fixture;

    public Coin(GameScreen gscreen, TiledMap map, float x, float y){

        this.screen = gscreen;
        this.world = screen.getWorld();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM); //Posición en la que dibujar y tamaño del sprite

        Random rand = new Random();
        value = rand.nextInt((MAX_VALUE - MIN_VALUE) + 1) + MIN_VALUE ;

        defineCoin();
    }

    private void defineCoin(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        if(!world.isLocked()){ //Si no hemos parado el mundo
            b2body = world.createBody(bdef);
        }

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Game.PPM);
        fdef.filter.categoryBits = Game.COIN_BIT;
        fdef.filter.maskBits = Game.CHEST_BIT | Game.TREE_BIT |Game.ROCK_BIT |
                Game.MC_BIT;

        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        fixture.setSensor(true);

    }

    public void update(float dt){

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));

        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    private TextureRegion getFrame(float dt){

        TextureRegion region;
        region = (TextureRegion) AnimationHandler.getAnimationHandler().getCoinAnimation().getKeyFrame(stateTime, true);

        stateTime = stateTime + dt;

        return region;
    }

    public int getValue(){
        return value;
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void onPlayerHit(){

    }
}
