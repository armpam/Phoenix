package com.phoenix.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/24/2017.
 */

public class MainFireball extends com.phoenix.game.Projectiles.MainProjectile {

    public MainFireball(GameScreen gscreen, float x, float y, String direction ){
        super(gscreen, x, y, direction);
        b2body.setUserData(this);
        damage = 500 * screen.getMcharacter().getAp();
        setRegion(AnimationHandler.getAnimationHandler().getFanim_region());
    }

    protected TextureRegion getFrame(float delta, MovState state){

        TextureRegion region = (TextureRegion) AnimationHandler.getAnimationHandler().getFanim().getKeyFrame(stateTime, true);

        stateTime = stateTime + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }
}
