package com.phoenix.game.Maps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by alesd on 3/20/2018.
 */

public interface MovingRectTileObject {

    void define();

    void setCategoryFilter(short bit);

    void update(float delta);

    TextureRegion getFrame(float delta);

    void move();

    void reverseVelocity();

}
