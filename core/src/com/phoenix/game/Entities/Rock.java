package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

/**
 * Created by alesd on 2/24/2017.
 */

public class Rock extends RectTileObject {

    public Rock(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Game.ROCK_BIT);
    }

    @Override
    public void onPlayerHit() {
        // La roca no hace nada cuando el jugador se choca contra ella
    }
}
