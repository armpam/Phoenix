package com.phoenix.game.Maps;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 3/22/2017.
 */

public class Chest extends com.phoenix.game.Maps.RectTileObject {

    private static TiledMapTileSet tileset;
    private final int OPEN_CHEST_MAP_1 = 2630;
    private final int OPEN_CHEST_DUNG = 1327;
    private final int SC_CHEST = 770;

    private boolean open;

    public Chest(World world, TiledMap map, MapObject object){
        super(world, map, object);
        if(map.getProperties().get("name").equals("forest_1")){
            tileset = map.getTileSets().getTileSet("dungeon_pack");
        }
        else if(map.getProperties().get("name").equals("dungeon_1")){
            tileset = map.getTileSets().getTileSet("dungeon_pack");
        }
        else if(map.getProperties().get("name").equals("sidescroll_1")){
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

        if(map.getProperties().get("name").equals("forest_1")){
            getCell(2).setTile(tileset.getTile(OPEN_CHEST_MAP_1));
        }
        else if(map.getProperties().get("name").equals("dungeon_1")){
            getCell(3).setTile(tileset.getTile(OPEN_CHEST_DUNG));
        }
        else if(map.getProperties().get("name").equals("sidescroll_1")){
            getCell(1).setTile(tileset.getTile(SC_CHEST));
        }
        else if(map.getProperties().get("name").equals("city_1")){

        }
        if(!open) {
            SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/openChest.ogg", Music.class).play(); //Sonido del cofre al abrirse
            open = true;
        }
    }

    @Override
    public void onFireBallHit() {

    }

}
