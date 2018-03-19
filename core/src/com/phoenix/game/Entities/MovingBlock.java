package com.phoenix.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;

/**
 * Created by alesd on 3/18/2018.
 */

public class MovingBlock extends Sprite {

    private GameScreen screen;
    private World world;
    private TiledMap map;

    private final int TEXT_WIDTH = 64;
    private final int TEXT_HEIGHT = 32;
    private TextureRegion textureRegion;

    private float stateTime;
    private int movSpeed = 1;

    private Rectangle bounds;
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private Body body;

    private Fixture fixture;

    private MapObject object;

    public MovingBlock(GameScreen gscreen, float x, float y, MapObject object, TiledMap map){

        this.screen = gscreen;
        this.world = screen.getWorld();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        shape = new PolygonShape();

        textureRegion = AnimationHandler.getAnimationHandler().getMovingBlock();

        setBounds(x, y, TEXT_WIDTH / Game.PPM, TEXT_HEIGHT / Game.PPM);

        defineMovingBlock();
        setCategoryFilter(Game.ROCK_BIT);
        setRegion(textureRegion);
    }

    public void defineMovingBlock(){
        BodyDef bdef = new BodyDef();

        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Game.PPM, (bounds.getY() + bounds.getHeight() / 2) / Game.PPM );

        FixtureDef fdef = new FixtureDef();

        body = world.createBody(bdef); //Crea el Body en el mundo
        body.setGravityScale(0);

        shape.setAsBox(bounds.getWidth() / 2 / Game.PPM, bounds.getHeight() / 2 / Game.PPM); //Define la forma como una caja
        fdef.shape = shape;
        fdef.filter.maskBits = Game.ROCK_BIT | Game.MB_BIT;
        fixture = body.createFixture(fdef);
        fdef.restitution = 0; //Hace que no rebote
        fdef.friction = 1;
        fdef.density = 0;
        fixture.setUserData(this);
    }

    public void setCategoryFilter(short bit){ //Ponemos al objeto el bit que queramos
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(int layerNumber){ //Devuelve las coordenadas de un B2body de tipo RectTileObject
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerNumber);
        TiledMapTileLayer.Cell cell = layer.getCell((int)(body.getPosition().x * Game.PPM / 32),(int)(body.getPosition().y * Game.PPM / 32));
        return cell;
    }

    public void update(float delta){
        move();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    private TextureRegion getFrame(float delta){
        stateTime = stateTime + delta;
        return textureRegion;
    }

    private void move(){
       this.body.setLinearVelocity(new Vector2(movSpeed, 0 ));
    }

    public void reverseVelocity(){
        this.movSpeed = -movSpeed;
    }
}
