package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

/**
 * Created by alesd on 2/24/2017.
 */

public class Tree extends RectTileObject {

    public Tree(World world, TiledMap map, MapObject object){
        super(world, map, object);
        fixture.setUserData(this);
        setCategoryFilter(Game.TREE_BIT);
    }

    @Override
    public void onPlayerHit() {

    }

    @Override
    public void onFireBallHit() {

    }
}
