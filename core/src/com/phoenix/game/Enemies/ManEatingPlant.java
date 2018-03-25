package com.phoenix.game.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/23/2018.
 */

public class ManEatingPlant extends com.phoenix.game.Enemies.Enemy {

    private final int WIDTH = 128;
    private final int HEIGHT = 128;
    private boolean lockLB;
    private final long LBCD = 300000000;
    private long startTime = 0;

    private Array<com.phoenix.game.Projectiles.LightBall> lbArray;

    public ManEatingPlant(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        super(gscreen, x, y, object, map);
        movSpeed = 0.5f;
        SCSpeed = 0.5f;
        AGGRO = 2;
        CHASEDISTANCE = 5;
        hp = 10000;
        ap = 150;
        xp = 2000;
        setBounds(x, y, WIDTH / Game.PPM, HEIGHT / Game.PPM);
        fixture.setUserData(this);
        body.setActive(true);
        lbArray = new Array<com.phoenix.game.Projectiles.LightBall>();
    }

    public void update(float delta){
        move();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar

        if(chasing && !lockLB){
            shoot();
            lockLB = true;
            startTime = TimeUtils.nanoTime();
        }

        if(lockLB){
            lockLightBall(startTime);
        }

        for(com.phoenix.game.Projectiles.LightBall lb : lbArray){ //Actualiza las bolas de fuego
            lb.update(delta);
            if(lb.isDestroyed()){
                lbArray.removeValue(lb, true); //Elimina la bola de fuego del array si se ha destruido
            }
        }
    }

    public TextureRegion getFrame(float delta){

        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case UP:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getPlant_up().getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getPlant_down().getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getPlant_left().getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getPlant_right().getKeyFrame(stateTimer, true);
                break;
            default:
                region = (TextureRegion) AnimationHandler.getAnimationHandler().getPlant_down().getKeyFrame(stateTimer, true);
        }

        stateTimer += delta;
        return region;
    }

    private void shoot(){
        com.phoenix.game.Projectiles.LightBall lb = new com.phoenix.game.Projectiles.LightBall(this.screen, body.getPosition().x, body.getPosition().y);
        lbArray.add(lb);
    }

    private void lockLightBall(long startTime) { //Pone la bola de fuego en CD
        if (TimeUtils.timeSinceNanos(startTime) > LBCD) {
            lockLB = false;
        }
    }

    public Array<com.phoenix.game.Projectiles.LightBall> getLbArray(){
        return lbArray;
    }
}
