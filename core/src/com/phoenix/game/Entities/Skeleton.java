package com.phoenix.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phoenix.game.Game;

import java.util.Random;

/**
 * Created by david on 23/03/2017.
 */

public class Skeleton extends Enemy{

    //Posición inicial del enemigo
    private Random randomGenerator = new Random();

    private float SSpeed = 0.005f; //Velocidad standard de movimiento del Enemigo
    private float SCSpeed = 0.05f; //Velocidad del enemigo en persecución
    private boolean fight = false; //Indica si el enemigo está en modo lucha
    private final float AGGRO = 150; //Distancia para que empieze el modo lucha

    public Skeleton(World world) {
        super(world);
        this.x = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de x
        this.y = randomGenerator.nextInt(2000);  //Genera la posición aleatoria de y
        defineEnemy(this.x,this.y);
        fixture.setUserData(this);
        setCategoryFilter(Game.ENEMY_BIT);
        this.b2body.setActive(false); //Desactivamos al enemigo al crearlo para ahorrar recursos.
    }

    public void enemyMovement(MainCharacter mcharacter) {  //Movimiento del enemigo
        //Declaramos la variable distance, la cual obtiene  la distancia del enemigo al Mcharacter
        Vector2 enemy = this.b2body.getPosition();
        Vector2 player = mcharacter.b2body.getPosition();
        float distance = enemy.dst(player) ;
        //Si el Mcharacter está lejos y el enemigo no está en modo lucha, anda aleatoriamente
        if ((distance >= ((AGGRO + 2) / Game.PPM)) && (fight == false)) {
            //System.out.println(distance);
            int mode = randomGenerator.nextInt(5);
            if (mode == 1) {
                this.b2body.applyLinearImpulse(new Vector2(SSpeed, 0), this.b2body.getWorldCenter(), true);
            } else if (mode == 2){
                this.b2body.applyLinearImpulse(new Vector2(0, -SSpeed), this.b2body.getWorldCenter(), true);
            } else if (mode == 3) {
                this.b2body.applyLinearImpulse(new Vector2(-SSpeed, 0), this.b2body.getWorldCenter(), true);
            } else if (mode == 4) {
                this.b2body.applyLinearImpulse(new Vector2(0, SSpeed), this.b2body.getWorldCenter(), true);
            }
        } else if (distance < ((AGGRO + 2) / Game.PPM)) {  //Si el enemigo está cerca lo empieza a perseguir
            fight = true;
            chase(mcharacter, distance); //Persigue al personaje
        }
    }

    private void chase(MainCharacter mcharacter, float distance) {
        if (distance > (AGGRO / Game.PPM)){  //Si el Mcaracter se aleja lo suficiente entonces deja de persegirlo
            if (fight == true) {
                this.b2body.setLinearVelocity(0,0); //Para que el enémigo no salga disparado si estaba persiguiendo
            }
                fight = false;
        } else  //Sino se aleja lo suficiente persigue al protagonista según su posición
            if ((this.b2body.getPosition().x < mcharacter.b2body.getPosition().x) && (this.b2body.getLinearVelocity().x < 0.8)) {
                this.b2body.applyLinearImpulse(new Vector2(SCSpeed, 0), this.b2body.getWorldCenter(), true);
        }
            if ((this.b2body.getPosition().y > mcharacter.b2body.getPosition().y) && (this.b2body.getLinearVelocity().y > -0.8)) {
                this.b2body.applyLinearImpulse(new Vector2(0, -SCSpeed), this.b2body.getWorldCenter(), true);
        }
            if ((this.b2body.getPosition().x > mcharacter.b2body.getPosition().x && (this.b2body.getLinearVelocity().x > -0.8))) {
                this.b2body.applyLinearImpulse(new Vector2(-SCSpeed, 0), this.b2body.getWorldCenter(), true);
        }
            if ((this.b2body.getPosition().y < mcharacter.b2body.getPosition().y) && (this.b2body.getLinearVelocity().y < 0.8)) {
                this.b2body.applyLinearImpulse(new Vector2(0, SCSpeed), this.b2body.getWorldCenter(), true);
        }
    }
}