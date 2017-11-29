package com.phoenix.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.GameScreen;

import java.util.Random;

/**
 * Created by david on 23/03/2017.
 */

public class Orc extends Enemy {

    //Posición inicial del enemigo
    private Random randomGenerator = new Random();

    private boolean fight = false; //Indica si el enemigo está en modo lucha
    private final float AGGRO = 150; //Distancia para que empieze el modo lucha

    public Orc(World world){
        super(world, GameScreen.simpleOrc); //
        this.x = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de x
        this.y = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de y
        defineEnemy(this.x,this.y);
        this.bdef.type = BodyDef.BodyType.StaticBody;
        fixture.setUserData(this);
        setCategoryFilter(Game.ENEMY_BIT);
        this.b2body.setActive(false); //Desactivamos al enemigo al crearlo para ahorrar recursos.
    }
}
