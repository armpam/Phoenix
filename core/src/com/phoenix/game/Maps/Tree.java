package com.phoenix.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

/**
 * Created by alesd on 2/24/2017.
 */

public class Tree extends RectTileObject {

    private GameScreen screen;

    public Tree(World world, TiledMap map, MapObject object, GameScreen screen){
        super(world, map, object);
        fixture.setUserData(this);
        this.screen = screen;
        setCategoryFilter(Game.TREE_BIT);
    }

    @Override
    public void onPlayerHit() {
        if(this.object.getProperties().containsKey("destination")) {
            if (this.object.getProperties().get("destination").equals("map_1")) {
                screen.setGreenMapFlag();
            } else if (this.object.getProperties().get("destination").equals("dungeon_1")) {
                screen.setDungeonFlag();
            } else if (this.object.getProperties().get("destination").equals("sidescroll_1")) {
                screen.setSideScrollFlag();
            } else if (this.object.getProperties().get("destination").equals("water")) {
                screen.setTpFlag();
            } else if (this.object.getProperties().get("destination").equals("city_1")) {
                screen.setCityFlag();
            }
        }
    }

    @Override
    public void onFireBallHit() {

    }
}
