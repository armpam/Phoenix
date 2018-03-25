package com.phoenix.game.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

/**
 * Created by alesd on 3/19/2018.
 */

public class Sensor extends RectTileObject {

    public Sensor(World world, TiledMap map, MapObject object){
        super(world, map, object);
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setAwake(true);
        body.setGravityScale(0);

        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(Game.SENSOR_BIT);
    }
    @Override
    public void onPlayerHit() {

    }

    @Override
    public void onFireBallHit() {

    }
}
