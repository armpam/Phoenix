package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

/**
 * Created by alesd on 3/22/2017.
 */

public class Chest extends RectTileObject {

    private static TiledMapTileSet tileset;
    private final int OPEN_CHEST = 2;

    public Chest(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        tileset = map.getTileSets().getTileSet("package");
        System.out.println(tileset.getTile(OPEN_CHEST));
        fixture.setUserData(this);
        setCategoryFilter(Game.CHEST_BIT);
    }

    @Override
    public void onPlayerHit() {
        getCell(9).setTile(tileset.getTile(OPEN_CHEST));
    }
}
