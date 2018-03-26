package com.phoenix.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/24/2018.
 */

public class IceBall extends MainProjectile {

    public IceBall(GameScreen gscreen, float x, float y, String direction ){
        super(gscreen, x, y, direction);
        b2body.setUserData(this);
        damage = 0;
    }

    protected TextureRegion getFrame(float delta, MainProjectile.MovState state){

        TextureRegion region = (TextureRegion) AnimationHandler.getAnimationHandler().getIbAnim().getKeyFrame(stateTime, true);

        stateTime = stateTime + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }
}
