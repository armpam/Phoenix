package com.phoenix.game.Entities;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.phoenix.game.Game;
import com.phoenix.game.Projectiles.IceBall;
import com.phoenix.game.Projectiles.LightBall;
import com.phoenix.game.Projectiles.LightningBall;
import com.phoenix.game.Projectiles.MainFireball;
import com.phoenix.game.Projectiles.MainProjectile;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.AnimationHandler;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 2/23/2017.
 */

public class MainCharacter extends Sprite {

    private World world;
    public Body b2body;
    protected Fixture fixture;

    //Posición inicial del jugador
    private int x = 3000;
    private int y = 100;

    //Atributos del jugador
    private int life;
    private int maxLife;
    private int mana;
    private int maxMana;
    private int money;
    private int level;
    private int ap;
    private int dp;
    private int currentXp;
    private int xpGoal;

    //Invisible al daño cuando es True
    private boolean iframe = false;

    //Devuelve la hora del reloj en nanosegundos.
    private long startTime = TimeUtils.nanoTime();

    private final int MAIN_TEXT_WIDTH = 64, MAIN_TEXT_HEIGHT =64 ; //Altura y anchura de los sprites del spritesheet del MC

    private enum MovState {UP, DOWN, LEFT, RIGHT, IDLE}; //Hacia dónde se mueve
    private MovState currentState; //Estado actual del personaje
    private MovState previousState;// Estado en el que se ha quedado al pararse

    private BodyDef bdef = new BodyDef();
    private FixtureDef fdef = new FixtureDef();

    private Array<MainProjectile> projectiles;

    private float stateTimer;

    private GameScreen screen;

    public MainCharacter(World world, GameScreen screen){
        this.world = world;
        defineMainCharacter();
        currentState = MovState.IDLE;
        stateTimer = 0;
        this.screen = screen;
        this.projectiles = new Array<MainProjectile>();

        setBounds(0, 0, MAIN_TEXT_WIDTH / Game.PPM, MAIN_TEXT_HEIGHT / Game.PPM);

        this.life = 1000;
        this.mana = 1000;
        this.money = 0;
        this.level = 1;
        this.ap = 1;
        this.dp = 1;
        this.currentXp = 0;
        this.xpGoal = 1000;
    }

    public MainCharacter(World world, GameScreen screen, MainCharacter cmc){
        this.world = world;
        defineMainCharacter();
        currentState = MovState.IDLE;
        stateTimer = 0;
        this.screen = screen;
        this.projectiles = new Array<MainProjectile>();

        setBounds(0, 0, MAIN_TEXT_WIDTH / Game.PPM, MAIN_TEXT_HEIGHT / Game.PPM);

        //Aquí se da el proceso de copiar los atributos del jugador que viene de otro mapa
        this.life = cmc.getLife();
        this.mana = cmc.getMana();
        this.money = cmc.getMoney();
        this.level = cmc.getLevel();
    }

    //Actualiza la posición de dónde dibujamos al jugador (sigue a la cámara)
    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta)); //Decide la región del spritesheet que va a dibujar

        for(com.phoenix.game.Projectiles.MainProjectile projectile : projectiles){ //Actualiza las bolas de fuego
            projectile.update(delta);
            if(projectile.isDestroyed()){
                projectiles.removeValue(projectile, true); //Elimina la bola de fuego del array si se ha destruido
            }
        }
    }

    //Da el frame a dibujar según el estado del jugador
    private TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case UP:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunUp_mc().getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunDown_mc().getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunLeft_mc().getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = (TextureRegion)AnimationHandler.getAnimationHandler().getRunRight_mc().getKeyFrame(stateTimer, true);
                break;
            default: //Caso IDLE
                if(previousState == MovState.DOWN){
                    region = AnimationHandler.getAnimationHandler().getIdleDown_mc(); //Se queda quieto mirando abajo
                }
                else if(previousState == MovState.LEFT){
                    region = AnimationHandler.getAnimationHandler().getIdleLeft_mc();
                }
                else if(previousState == MovState.RIGHT){
                    region = AnimationHandler.getAnimationHandler().getIdleRight_mc();
                }
                else{
                    region = AnimationHandler.getAnimationHandler().getIdle_mc();
                }
                break;
        }
        stateTimer = stateTimer + delta; //El StateTimer es magia, pero hay que sumarle delta para que se anime bien
        return region;
    }

    //Devuelve el estado de movimiento del jugador (corriendo hacia la dcha/izquierda, quieto...)
    private MovState getState(){
        if(b2body.getLinearVelocity().x < 0){ //Si la X disminuye es que está yendo hacia la izquierda
            previousState = MovState.LEFT;
            return previousState;
        }
        else if(b2body.getLinearVelocity().x > 0){
            previousState = MovState.RIGHT;
            return previousState;
        }
        else if(b2body.getLinearVelocity().y < 0){
            previousState = MovState.DOWN;
            return previousState;
        }
        else if(b2body.getLinearVelocity().y > 0){
            previousState = MovState.UP;
            return previousState;
        }
        else return MovState.IDLE;
    }

    private void defineMainCharacter(){
        bdef.position.set(x / Game.PPM, y / Game.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody; //El jugador es dinámico, se mueve

        b2body = world.createBody(bdef);

        //El Body del jugador es un círculo de radio 5
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Game.PPM);
        fdef.filter.categoryBits = Game.MC_BIT; //Bit del jugador
        fdef.filter.maskBits = Game.DEFAULT_BIT | Game.CHEST_BIT | Game.ROCK_BIT | Game.TREE_BIT | Game.COIN_BIT | Game.ENEMY_BIT | Game.LADDER_BIT | Game.MB_BIT | Game.SENSOR_BIT
                                | Game.LIGHTBALL_BIT; //Con qué puede el personaje chocar

        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.density = 0;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this); //Se crea la fixture y la asignamos la propia clase para la COLISIÓN
    }

    public String getPreviousState(){

        if (previousState == MovState.UP)
                return "UP";
        else if (previousState == MovState.DOWN)
            return "DOWN";
        else if (previousState == MovState.LEFT)
            return "LEFT";
        else
            return "RIGHT";
    }

    public void decreaseLife(int quantity){  //Método para quitarle vida cuando le atacan.
        long iFrameDuration = 3000000000L;

        if ( TimeUtils.timeSinceNanos(startTime) > iFrameDuration) {
            iframe = false;
        }
        if (!iframe) {
            iframe = true;
            this.life = this.life - quantity;
            startTime = TimeUtils.nanoTime();
        }
        screen.getUI().updateLife(this);
    }

    public void fire(int projectile){
        if(projectile == 1){
            MainFireball fireball = new MainFireball(this.screen, b2body.getPosition().x, b2body.getPosition().y, getPreviousState());
            projectiles.add(fireball);
            mana = mana - 100;
        }
        else if(projectile == 2){
            IceBall iceball = new IceBall(this.screen, b2body.getPosition().x, b2body.getPosition().y, getPreviousState());
            projectiles.add(iceball);
            mana = mana - 50;
        }
        else{
            LightningBall lightningBall = new LightningBall(this.screen, b2body.getPosition().x, b2body.getPosition().y, getPreviousState());
            projectiles.add(lightningBall);
            mana = mana - 50;
        }
        screen.getUI().updateMana(this);
    }

    public void onCoinHit(int value){
        money = money + value;
        SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/coin.ogg", Music.class).play();
        screen.getUI().updateScore(this);
    }

    public void lvlUp(){
        level = level + 1;
        maxLife = life + 200;
        maxMana = maxMana + 200;
        life = maxLife;
        mana = maxMana;
        ap = ap + 1;
        dp = dp + 1;
        xpGoal = xpGoal * 2;
        screen.getUI().updateUI(this);
    }

    public void teleport(float x, float y){
        b2body.setTransform(x, y, 0);
    }

    public int getLife(){return this.life;}

    public int getMana(){return this.mana;}

    public int getAp(){return ap;}

    public int getDp(){return dp;}

    public int getMoney(){return this.money;}

    public int getLevel(){return this.level;}

    public Array<MainProjectile> getProjectiles(){
        return this.projectiles;
    }

}
