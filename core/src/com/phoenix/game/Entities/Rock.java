package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.ScreenHandler;

/**
 * Created by alesd on 2/24/2017.
 */

public class Rock extends RectTileObject {

    private GameScreen screen;

    public Rock(World world, TiledMap map, MapObject object, GameScreen screen){
        super(world, map, object);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(Game.ROCK_BIT);
    }

    @Override
    public void onPlayerHit() {
        if(this.object.getProperties().containsKey("map_1")){
            screen.setGreenMapFlag();
        }
        else if(this.object.getProperties().containsKey("dungeon_1")){
            screen.setDungeonFlag();
        }
        else if(this.object.getProperties().containsKey("sidescroll_1")){
            screen.setSideScrollFlag();
        }
        else if(this.object.getProperties().containsKey("water")) {
            screen.setTpFlag();
        }
    }

    @Override
    public void onFireBallHit() {

    }
}
