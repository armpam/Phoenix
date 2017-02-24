package com.phoenix.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Entities.Rock;
import com.phoenix.game.Entities.Tree;

/**
 * Created by alesd on 2/24/2017.
 */

//Crea nuestro mundo Box2D, que de momento usamos para los Bodies, que no los dibujos de Sprites
public class B2WorldCreator {
    private World world;
    private TiledMap map;

    public B2WorldCreator(World world, TiledMap map){
        this.world = world;
        this.map = map;

        createTrees();
        createRocks();
    }

    private void createTrees(){
        //Por cada objeto de tipo Object en Tiled cogemos aquellos objetos con ID = 10, hacemos un rectángulo y creamos el objeto
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Tree tree = new Tree(world, map, rect);
        }
    }

    private void createRocks(){
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Rock rock = new Rock(world, map, rect);
        }
    }
}
