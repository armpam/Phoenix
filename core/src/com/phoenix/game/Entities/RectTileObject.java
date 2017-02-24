package com.phoenix.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by alesd on 2/24/2017.
 */

//Cualquier objecto que tengamos en Tiled como un rectángulo con el que queremos chocar
public abstract class RectTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds; // El rectángulo que marca los límites de los Bodies

    //Hay que definir esto siempre que creemos un cuerpo (Body) de Box2D
    protected BodyDef bdef;
    protected PolygonShape shape;
    protected FixtureDef fdef;
    protected Body body;

    public RectTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        bdef = new BodyDef(); //Definición del cuerpo
        shape = new PolygonShape(); //Qué tipo de polígono rodea al Body
        fdef = new FixtureDef(); //Definición de las fixtures

        create();
    }

    //Crea el cuerpo Box2D con colisión de polígono rectangular
    public void create(){

        bdef.type = BodyDef.BodyType.StaticBody; //Cuerpo estático, no se mueve
        bdef.position.set(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2 );

        body = world.createBody(bdef); //Crea el Body en el mundo

        shape.setAsBox(bounds.getWidth() / 2, bounds.getHeight() / 2); //Define la forma como una caja
        fdef.shape = shape;
        body.createFixture(fdef);

    }
}
