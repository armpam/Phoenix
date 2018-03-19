package com.phoenix.game.Entities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

/**
 * Created by alesd on 3/22/2017.
 */

public class Chest extends RectTileObject {

    private static TiledMapTileSet tileset;
    private final int OPEN_CHEST_MAP_1 = 1026;
    private final int OPEN_CHEST_DUNG = 1327;
    private final int SC_CHEST = 770;

    private boolean open;

    public Chest(World world, TiledMap map, MapObject object){
        super(world, map, object);
        if(map.getProperties().containsKey("map_1")){
            tileset = map.getTileSets().getTileSet("package");
        }
        else if(map.getProperties().containsKey("dungeon_1")){
            tileset = map.getTileSets().getTileSet("dungeon_pack");
        }
        else if(map.getProperties().containsKey("sidescroll_1")){
            tileset = map.getTileSets().getTileSet("package");
        }
        else{

        }

        fixture.setUserData(this);
        setCategoryFilter(Game.CHEST_BIT);

        open = false;
    }

    @Override
    public void onPlayerHit() {
        if(map.getProperties().containsKey("map_1")){
            getCell(12).setTile(tileset.getTile(OPEN_CHEST_MAP_1)); //Sustituye la imagen del cofre por el cofre abierto
        }
        else if(map.getProperties().containsKey("dungeon_1")){
            getCell(3).setTile(tileset.getTile(OPEN_CHEST_DUNG));
        }
        else if(map.getProperties().containsKey("sidescroll_1")){
            getCell(1).setTile(tileset.getTile(SC_CHEST));
        }
        else{

        }

        if(!open) {
            Game.assetManager.get("audio/sounds/openChest.ogg", Music.class).play(); //Sonido del cofre al abrirse
            open = true;
        }
    }

    @Override
    public void onFireBallHit() {

    }

}
