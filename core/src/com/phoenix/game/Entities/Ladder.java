package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

/**
 * Created by alesd on 3/18/2018.
 */

public class Ladder extends RectTileObject {

    private GameScreen screen;

    public Ladder(World world, TiledMap map, MapObject object, GameScreen screen){
        super(world, map, object);
        this.screen = screen;
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(Game.LADDER_BIT);
    }

    @Override
    public void onPlayerHit() {
        screen.setLadder(true);
    }

    public void leftLadder(){
        screen.setLadder(false);
    }

    @Override
    public void onFireBallHit() {

    }
}
