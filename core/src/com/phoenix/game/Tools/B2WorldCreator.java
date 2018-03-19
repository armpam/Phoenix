package com.phoenix.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.phoenix.game.Entities.Chest;
import com.phoenix.game.Entities.Coin;
import com.phoenix.game.Entities.Ladder;
import com.phoenix.game.Entities.MovingBlock;
import com.phoenix.game.Entities.Rock;
import com.phoenix.game.Entities.Sensor;
import com.phoenix.game.Entities.Tree;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

/**
 * Created by alesd on 2/24/2017.
 */

//Crea nuestro mundo Box2D, que de momento usamos para los Bodies, que no los dibujos de Sprites
public class B2WorldCreator {
    private World world;
    private TiledMap map;
    private GameScreen screen;

    private Array<Coin> coinArray;
    private Array<MovingBlock> mbArray;

    public B2WorldCreator(World world, TiledMap map, GameScreen screen){
        this.world = world;
        this.map = map;
        this.screen = screen;
        coinArray = new Array<Coin>();
        mbArray = new Array<MovingBlock>();

        if(map.getProperties().containsKey("map_1")) {
            createRocks(16);
            createChests(15);
            createCoins(14);
        }
        else if(map.getProperties().containsKey("dungeon_1")){
            createWalls(5);
            createChests(6);
        }
        else if(map.getProperties().containsKey("sidescroll_1")){
            createWalls(2);
            createChests(3);
            createCoins(4);
            createLadders(6);
            createMovingBlocks(5);
            createSensors(7);
        }
    }

    private void createTrees(int layer){
        //Por cada objeto de tipo Object en Tiled cogemos aquellos objetos con ID = 11, hacemos un rectángulo y creamos el objeto
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Tree(world, map, object);
        }
    }

    private void createRocks(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Rock(world, map, object, screen);
        }
    }

    private void createChests(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Chest(world, map, object);
        }
    }

    private void createCoins(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Coin coin = new Coin(this.screen, map, rect.x / Game.PPM, rect.y / Game.PPM);
            coinArray.add(coin);
        }
    }

    private void createWalls(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Rock(world, map, object, screen);
        }
    }

    private void createLadders(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Ladder(world, map, object, screen);
        }
    }

    private void createMovingBlocks(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            MovingBlock mb = new MovingBlock(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            mbArray.add(mb);
        }
    }

    private void createSensors(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Sensor(world, map, object);
        }
    }

    public Array<Coin> getCoinArray(){
        return coinArray;
    }
    public Array<MovingBlock> getMbArray(){ return mbArray; }
}
