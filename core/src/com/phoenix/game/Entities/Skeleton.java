package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 23/03/2017.
 */

public class Skeleton extends Enemy{

    public Skeleton(GameScreen gscreen, float x, float y, MapObject object, TiledMap map) {
        super(gscreen, x, y, object, map);
        movSpeed = 1f;
        SCSpeed = 0.8f;
        AGGRO = 2;
        CHASEDISTANCE = 40;
        fixture.setUserData(this);
        setCategoryFilter(Game.ENEMY_BIT);
    }

    //Da el frame a dibujar según el estado del enemigo
    private TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case UP:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunUp_sk().getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunDown_sk().getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunLeft_sk().getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunRight_sk().getKeyFrame(stateTimer, true);
                break;
            default: //Caso IDLE
                if(previousState == MovState.DOWN){
                    region = AnimationHandler.getAnimationHandler().getIdleDown_sk(); //Se queda quieto mirando abajo
                }
                else if(previousState == MovState.LEFT){
                    region = AnimationHandler.getAnimationHandler().getIdleLeft_sk();
                }
                else if(previousState == MovState.RIGHT){
                    region =AnimationHandler.getAnimationHandler().getIdleRight_sk();
                }
                else{
                    region = AnimationHandler.getAnimationHandler().getIdle_sk();
                }
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }

    public void update(float delta){
            if(body.isActive()) {
                move();
                setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            }
            setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar
    }
}