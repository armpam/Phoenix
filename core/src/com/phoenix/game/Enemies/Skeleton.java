package com.phoenix.game.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 23/03/2017.
 */

public class Skeleton extends com.phoenix.game.Enemies.Enemy {

    public Skeleton(GameScreen gscreen, float x, float y, MapObject object, TiledMap map) {
        super(gscreen, x, y, object, map);
        movSpeed = 1f;
        SCSpeed = 1.1f;
        AGGRO = 2;
        CHASEDISTANCE = 40;
        hp = 1000;
        ap = 50;
        xp = 100;
        fixture.setUserData(this);
    }

    //Da el frame a dibujar según el estado del enemigo
    public TextureRegion getFrame(float delta){
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
                region = AnimationHandler.getAnimationHandler().getIdle_sk();
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }
}