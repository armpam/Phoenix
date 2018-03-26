package com.phoenix.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/24/2018.
 */

public class LightningBall extends MainProjectile {

    public LightningBall(GameScreen gscreen, float x, float y, String direction ){
        super(gscreen, x, y, direction);
        b2body.setUserData(this);
        damage = 300 * screen.getMcharacter().getAp();
    }

    protected TextureRegion getFrame(float delta, MovState state){

        TextureRegion region = (TextureRegion) AnimationHandler.getAnimationHandler().getLbAnim().getKeyFrame(stateTime, true);

        stateTime = stateTime + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }
}
