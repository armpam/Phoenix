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
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

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

    private Texture texture;
    private Animation fanim_up;
    private Animation fanim_down;
    private Animation fanim_left;
    private Animation fanim_right;
    private final int TEXT_WIDTH = 64;
    private final int TEXT_HEIGHT = 64;


    private final int FB_SPEED = 2; //Velocidad de la bola

    Body b2body;
    Fixture fixture;

    public MainFireball(GameScreen gscreen, float x, float y, String direction ){

        this.screen = gscreen;
        this.direction = intDirection(direction);
        this.world = screen.getWorld();

        texture = new Texture(Gdx.files.internal("package_64.png"));

        initAnimation();;

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

    private void initAnimation(){

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 8; i++){
            frames.add(new TextureRegion(texture, i* 64, 0, TEXT_WIDTH, TEXT_HEIGHT));
        }
        fanim_left = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 8; i++){
            frames.add(new TextureRegion(texture, i* 64, 128, TEXT_WIDTH, TEXT_HEIGHT));
        }
        fanim_up = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 8; i++){
            frames.add(new TextureRegion(texture, i* 64, 256, TEXT_WIDTH, TEXT_HEIGHT));
        }
        fanim_right = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 8; i++){
            frames.add(new TextureRegion(texture, i* 64, 384, TEXT_WIDTH, TEXT_HEIGHT));
        }
        fanim_down = new Animation(0.1f, frames);
        frames.clear();

    }

    private TextureRegion getFrame(float delta, MovState state){

        TextureRegion region = new TextureRegion();

        switch(state){

            case UP:
                region = (TextureRegion)fanim_up.getKeyFrame(stateTime, true);
                break;
            case DOWN:
                region = (TextureRegion)fanim_down.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                region = (TextureRegion)fanim_left.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                region = (TextureRegion)fanim_right.getKeyFrame(stateTime, true);
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
