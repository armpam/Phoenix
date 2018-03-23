package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.phoenix.game.Screens.GameScreen;

/**
 * Created by alesd on 3/23/2018.
 */

public class ManEatingPlant extends Enemy {

    public ManEatingPlant(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){
        super(gscreen, x, y, object, map);
    }
}
