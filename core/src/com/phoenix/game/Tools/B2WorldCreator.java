package com.phoenix.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.phoenix.game.Enemies.Bat;
import com.phoenix.game.Enemies.Enemy;
import com.phoenix.game.Maps.Chest;
import com.phoenix.game.Maps.Coin;
import com.phoenix.game.Enemies.DarkElf;
import com.phoenix.game.Maps.Ladder;
import com.phoenix.game.Enemies.ManEatingPlant;
import com.phoenix.game.Maps.MovingBlock;
import com.phoenix.game.Enemies.Orc;
import com.phoenix.game.Maps.Rock;
import com.phoenix.game.Maps.Sensor;
import com.phoenix.game.Enemies.Skeleton;
import com.phoenix.game.Maps.Tree;
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
    private Array<Enemy> enemyArray;

    public B2WorldCreator(World world, TiledMap map, GameScreen screen) {
        this.world = world;
        this.map = map;
        this.screen = screen;
        coinArray = new Array<Coin>();
        mbArray = new Array<MovingBlock>();
        enemyArray = new Array<Enemy>();

        if (map.getProperties().containsKey("name")) {
            if (map.getProperties().get("name").equals("forest_1")) {
                createRocks(5);
                createChests(4);
                createCoins(3);
                createSkeletons(6);
                createOrcs(7);
                createElfs(8);
            } else if (map.getProperties().get("name").equals("dungeon_1")) {
                createTrees(5);
                createChests(6);
                createBats(7);
                createMep(8);
            } else if (map.getProperties().get("name").equals("sidescroll_1")) {
                createWalls(2);
                createChests(3);
                createCoins(4);
                createLadders(6);
                createMovingBlocks(5);
                createSensors(7);
            } else if (map.getProperties().get("name").equals("city_1")) {
                createWalls(1);
            }
        }
    }

    private void createTrees(int layer){
        //Por cada objeto de tipo Object en Tiled cogemos aquellos objetos con ID = 11, hacemos un rectángulo y creamos el objeto
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            new Tree(world, map, object, screen);
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

    private void createSkeletons(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Skeleton sk = new Skeleton(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            enemyArray.add(sk);
        }
    }

    private void createOrcs(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Orc orc = new Orc(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            enemyArray.add(orc);
        }
    }

    private void createElfs(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            DarkElf de = new DarkElf(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            enemyArray.add(de);
        }
    }

    private void createBats(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Bat bat = new Bat(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            enemyArray.add(bat);
        }
    }

    private void createMep(int layer){
        for (MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            ManEatingPlant mep = new ManEatingPlant(this.screen, rect.x / Game.PPM, rect.y / Game.PPM, object, map);
            enemyArray.add(mep);
        }
    }

    public Array<Coin> getCoinArray(){
        return coinArray;
    }
    public Array<MovingBlock> getMbArray(){ return mbArray; }
    public Array<Enemy> getEnemyArray(){return enemyArray;}
}
