package com.phoenix.game.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/23/2018.
 */

public class Bat extends com.phoenix.game.Enemies.Enemy {

    private TextureRegion region;
    private float stateTimer;

    public Bat(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        super(gscreen, x, y, object, map);
        movSpeed = 3f;
        hp = 4000;
        ap =  400;
        xp = 100;
        body.setActive(true);
        fixture.setUserData(this);
        fixture.setSensor(true);
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();

        switch(currentState) {
            case UP:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getBatUp().getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getBatDown().getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getBatLeft().getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getBatRight().getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer += delta;
        return region;
    }

    @Override
    public void move(){
        if(direction.equals("horizontal")){
            this.body.setLinearVelocity(movSpeed, 0);
        }
        else if(direction.equals("vertical")){
            this.body.setLinearVelocity(0, movSpeed);
        }
    }
}
