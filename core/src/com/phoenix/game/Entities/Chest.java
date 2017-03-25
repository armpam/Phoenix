package com.phoenix.game.Entities;

import com.badlogic.gdx.audio.Music;
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

    private boolean open;

    public Chest(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        tileset = map.getTileSets().getTileSet("package");

        fixture.setUserData(this);
        setCategoryFilter(Game.CHEST_BIT);

        open = false;
    }

    @Override
    public void onPlayerHit() {
        getCell(9).setTile(tileset.getTile(OPEN_CHEST)); //Sustituye la imagen del cofre por el cofre abierto

        if(!open) {
            Game.assetManager.get("audio/sounds/openChest.ogg", Music.class).play(); //Sonido del cofre al abrirse
            open = true;
        }
    }

    @Override
    public void onFireBallHit() {

    }

}
