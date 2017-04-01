package com.phoenix.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.phoenix.game.Entities.Chest;
import com.phoenix.game.Entities.Coin;
import com.phoenix.game.Entities.Rock;
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

    public B2WorldCreator(World world, TiledMap map, GameScreen screen){
        this.world = world;
        this.map = map;
        this.screen = screen;
        coinArray = new Array<Coin>();

        createTrees();
        createRocks();
        createChests();
        createCoins();
    }

    private void createTrees(){
        //Por cada objeto de tipo Object en Tiled cogemos aquellos objetos con ID = 11, hacemos un rectángulo y creamos el objeto
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Tree tree = new Tree(world, map, rect);
        }
    }

    private void createRocks(){
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Rock rock = new Rock(world, map, rect);
        }
    }

    private void createChests(){
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Chest chest = new Chest(world, map, rect);
        }
    }

    private void createCoins(){
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Coin coin = new Coin(this.screen, map, rect.x / Game.PPM, rect.y / Game.PPM);
            coinArray.add(coin);
        }
    }

    public Array<Coin> getCoinArray(){
        return coinArray;
    }
}
