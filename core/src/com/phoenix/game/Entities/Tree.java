package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by alesd on 2/24/2017.
 */

public class Tree extends RectTileObject {

    public Tree(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
    }
}