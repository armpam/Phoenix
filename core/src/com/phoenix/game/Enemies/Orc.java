package com.phoenix.game.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 23/03/2017.
 */

public class Orc extends com.phoenix.game.Enemies.Enemy {

    public Orc(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        super(gscreen, x, y, object, map);
        movSpeed = 0.5f;
        SCSpeed = 0.25f;
        AGGRO = 2;
        CHASEDISTANCE = 40;
        hp = 3000;
        ap = 200;
        xp = 200;
        fixture.setUserData(this);
    }

    public TextureRegion getFrame(float delta){
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
                region = AnimationHandler.getAnimationHandler().getIdle_orc();
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }
}
