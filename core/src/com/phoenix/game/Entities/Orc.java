package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.AnimationHandler;

import java.util.Random;

/**
 * Created by alesd on 23/03/2017.
 */

public class Orc extends Enemy {

    //Posición inicial del enemigo
    private Random randomGenerator = new Random();

    public Orc(World world){
        super(world, AnimationHandler.getAnimationHandler().getSimpleOrc()); //
        this.x = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de x
        this.y = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de y
        defineEnemy(this.x,this.y);
        this.bdef.type = BodyDef.BodyType.StaticBody;
        fixture.setUserData(this);
        setCategoryFilter(Game.ENEMY_BIT);
        this.b2body.setActive(false); //Desactivamos al enemigo al crearlo para ahorrar recursos.
    }

    private TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case UP:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunUp_orc().getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunDown_orc().getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunLeft_orc().getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunRight_orc().getKeyFrame(stateTimer, true);
                break;
            default: //Caso IDLE
                if(previousState == MovState.DOWN){
                    region = AnimationHandler.getAnimationHandler().getIdleDown_orc(); //Se queda quieto mirando abajo
                }
                else if(previousState == MovState.LEFT){
                    region = AnimationHandler.getAnimationHandler().getIdleLeft_orc();
                }
                else if(previousState == MovState.RIGHT){
                    region =AnimationHandler.getAnimationHandler().getIdleRight_orc();
                }
                else{
                    region = AnimationHandler.getAnimationHandler().getIdle_orc();
                }
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar
    }
}
