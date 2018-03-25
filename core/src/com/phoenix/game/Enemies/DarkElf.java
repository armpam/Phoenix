package com.phoenix.game.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/23/2018.
 */

public class DarkElf extends Enemy {

    private Array<com.phoenix.game.Projectiles.LightBall> lightBalls;
    private boolean lockLB;
    private final long LBCD = 300000000;
    private float stateTime;
    private long startTime = 0;
    private TextureRegion region;
    private boolean right;

    public DarkElf(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        super(gscreen, x, y, object, map);
        movSpeed = 0f;
        SCSpeed = 0f;
        AGGRO = 10;
        CHASEDISTANCE = 0;
        hp = 500;
        ap = 100;
        xp = 150;
        lightBalls = new Array<com.phoenix.game.Projectiles.LightBall>();
        region = new TextureRegion();
        lockLB = false;
        fixture.setUserData(this);
        body.setType(BodyDef.BodyType.StaticBody);
        setCategoryFilter(Game.ENEMY_BIT);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void update(float delta){
        if(body.isActive()) {
            if (body.getPosition().dst2(screen.getMcharacter().b2body.getPosition()) < AGGRO && !lockLB) {
                getDirection2();
                shoot();
                lockLB = true;
                startTime = TimeUtils.nanoTime();
                setRegion(getFrame(delta));
            } else {
                setRegion(getFrame(delta));
            }
        }
        else{
            setRegion(AnimationHandler.getAnimationHandler().getIdleElf());
        }
        if(lockLB){
            lockLightBall(startTime);
        }
        for(com.phoenix.game.Projectiles.LightBall lb : lightBalls){ //Actualiza las bolas de fuego
            lb.update(delta);
            if(lb.isDestroyed()){
                lightBalls.removeValue(lb, true); //Elimina la bola de fuego del array si se ha destruido
            }
        }
    }

    private void shoot(){
        com.phoenix.game.Projectiles.LightBall lb = new com.phoenix.game.Projectiles.LightBall(this.screen, body.getPosition().x, body.getPosition().y);
        lightBalls.add(lb);
    }

    private void lockLightBall(long startTime) { //Pone la bola de fuego en CD
        if (TimeUtils.timeSinceNanos(startTime) > LBCD) {
            lockLB = false;
        }
    }

    private void getDirection2(){
        if(body.getPosition().x < screen.getMcharacter().b2body.getPosition().x){
            right = true;
        }
        else{
            right = false;
        }
    }

    public TextureRegion getFrame(float delta){

        if (right){
            region = (TextureRegion) AnimationHandler.getAnimationHandler().getElfShootingRight().getKeyFrame(stateTime, true);
        }
        else{
            region = (TextureRegion) AnimationHandler.getAnimationHandler().getElfShootingLeft().getKeyFrame(stateTime, true);
        }
        stateTime += delta;
       return region;
    }

    @Override
    public void move(){

    }

    public Array<com.phoenix.game.Projectiles.LightBall> getLightBalls(){
        return lightBalls;
    }
}
