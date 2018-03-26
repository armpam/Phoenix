package com.phoenix.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by alesd on 3/19/2018.
 */

public class AnimationHandler implements Disposable {

    private static AnimationHandler mAnimationHandler = null;

    Array<TextureRegion> frames = new Array<TextureRegion>();

    private Texture sidescroll;

    //Esqueleto simple
    private Texture simpleSkeleton;
    private final int MAIN_TEXT_WIDTH = 64, MAIN_TEXT_HEIGHT =64 ; //Altura y anchura de los sprites del spritesheet del MC

    private TextureRegion idle_sk; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))
    private TextureRegion dead_sk;

    private Animation runLeft_sk;
    private Animation runRight_sk;
    private Animation runUp_sk;
    private Animation runDown_sk;

    //Orco simple
    private Texture simpleOrc;

    private TextureRegion idle_orc; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))
    private TextureRegion dead_orc;

    private Animation runLeft_orc;
    private Animation runRight_orc;
    private Animation runUp_orc;
    private Animation runDown_orc;

    //Elfo oscuro

    private Texture darkElf;

    private TextureRegion idle_elf;
    private TextureRegion dead_elf;

    private Animation shootingRight;
    private Animation shootingLeft;

    //Moneda
    private Texture coin;

    private Animation coinAnimation;


    //Personaje principal
    private Texture mainCharacter;

    private Animation runLeft_mc;

    private Animation runRight_mc;
    private Animation runUp_mc;
    private Animation runDown_mc;

    private TextureRegion idle_mc; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))
    private TextureRegion idleLeft_mc; // Posturas sin hacer nada mirando hacia distintos lados
    private TextureRegion idleDown_mc;
    private TextureRegion idleRight_mc;

    private TextureRegion movingBlock;

    //Bolas elementales
    private Animation fanim;
    private Animation ibAnim;
    private Animation lbAnim;

    private TextureRegion fanim_region;
    private TextureRegion iceball_region;
    private TextureRegion lightningball_region;

    //Murciélago
    private Texture dungeon;

    private Animation bat_up;
    private Animation bat_down;
    private Animation bat_left;
    private Animation bat_right;

    //Planta carnívora
    private Animation plant_up;
    private Animation plant_down;
    private Animation plant_left;
    private Animation plant_right;

    //Texturas sueltas

    private TextureRegion lightBall;

    private AnimationHandler(){

        sidescroll = new Texture(Gdx.files.internal("generic_platformer_tiles.png"));
        simpleSkeleton = new Texture(Gdx.files.internal("simple_skeleton.png"));
        darkElf = new Texture(Gdx.files.internal("dark_elf.png"));
        simpleOrc = new Texture(Gdx.files.internal("simple_orc.png"));
        coin = new Texture(Gdx.files.internal("coin.png"));
        mainCharacter = new Texture(Gdx.files.internal("main.png"));
        dungeon = new Texture(Gdx.files.internal("dungeon_pack_2.png"));

        movingBlock = new TextureRegion(sidescroll, 32, 192, 64, 32);
        lightBall = new TextureRegion(dungeon,192, 224 , 32, 32);

        initSkeletonAnimations(simpleSkeleton);
        initOrcAnimations(simpleOrc);
        initCoinAnimation(coin);
        initMCAnimations(mainCharacter);
        initBallAnimations(dungeon);
        initElfAnimations(darkElf);
        initBatAnimations(dungeon);
        initPlantAnimations(dungeon);
    }

    public static AnimationHandler getAnimationHandler(){
        if(mAnimationHandler == null){
            mAnimationHandler = new AnimationHandler();
        }
        return mAnimationHandler;
    }

    private void initSkeletonAnimations(Texture mainTexture){

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 512, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runUp_sk = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 576, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runLeft_sk = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 640, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runDown_sk = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 704, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runRight_sk = new Animation(0.1f, frames);
        frames.clear();

        idle_sk = new TextureRegion(mainTexture, 0 , 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT ); //Cogemos el sprite del punto 0,0 con W y H 64
        dead_sk = new TextureRegion(mainTexture, 320 , 1280, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initOrcAnimations(Texture mainTexture){

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 512, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runUp_orc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 576, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runLeft_orc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 640, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runDown_orc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 704, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runRight_orc = new Animation(0.1f, frames);
        frames.clear();

        idle_orc = new TextureRegion(mainTexture, 0 , 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT ); //Cogemos el sprite del punto 0,0 con W y H 64
        dead_orc = new TextureRegion(mainTexture, 320 , 1280, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initCoinAnimation(Texture mainTexture){

        for(int i = 0; i < 7; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 0, 32, 32));
        }
        coinAnimation = new Animation(0.2f, frames);
        frames.clear();

    }

    private void initMCAnimations(Texture mainTexture){

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 512, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runUp_mc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 576, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runLeft_mc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 640, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runDown_mc = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 9; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 704, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        runRight_mc = new Animation(0.1f, frames);
        frames.clear();

        idle_mc = new TextureRegion(mainTexture, 0 , 0, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT ); //Cogemos el sprite del punto 0,0 con W y H 64
        idleLeft_mc = new TextureRegion(mainTexture, 0 , 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleRight_mc = new TextureRegion(mainTexture, 0 , 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleDown_mc = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initBallAnimations(Texture mainTexture){

        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 160, 32, 32));
        }
        fanim = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 3; i < 5; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 160, 32, 32));
        }
        ibAnim = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 6; i < 8; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 160, 32, 32));
        }
        lbAnim = new Animation(0.1f, frames);
        frames.clear();

        fanim_region = new TextureRegion(mainTexture, 0, 160, 32, 32);
        iceball_region = new TextureRegion(mainTexture, 96, 160, 32, 32);
        lightningball_region = new TextureRegion(mainTexture, 192, 160, 32, 32);
    }

    private void initElfAnimations(Texture mainTexture){

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        shootingRight = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT));
        }
        shootingLeft = new Animation(0.2f, frames);
        frames.clear();

        idle_elf = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        dead_elf = new TextureRegion(mainTexture, 320 , 1280, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initBatAnimations(Texture mainTexture){

        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 32, 32, 32));
        }
        bat_left = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 0, 32, 32));
        }
        bat_up = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 96, 32, 32));
        }
        bat_right = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(mainTexture, i* 32, 64, 32, 32));
        }
        bat_down = new Animation(0.1f, frames);
        frames.clear();
    }

    private void initPlantAnimations(Texture mainTexture){
        for(int i = 6; i < 8; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 161, 64, 76));
        }
        plant_left = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 6; i < 8; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 85, 64, 76));
        }
        plant_up = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 6; i < 8; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 313, 64, 76));
        }
        plant_right = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 6; i < 8; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 237, 64, 76));
        }
        plant_down = new Animation(0.1f, frames);
        frames.clear();
    }

    public TextureRegion getMovingBlock() {
        return movingBlock;
    }

    public TextureRegion getIdle_sk() {return idle_sk;}

    public Animation getRunLeft_sk() {return runLeft_sk;}

    public Animation getRunRight_sk() {return runRight_sk;}

    public Animation getRunUp_sk() {return runUp_sk;}

    public Animation getRunDown_sk() {return runDown_sk;}

    public TextureRegion getDead_sk(){return dead_sk;}

    public TextureRegion getIdle_orc() {return idle_orc;}

    public TextureRegion getDead_orc(){return dead_orc;}

    public Animation getRunLeft_orc() {return runLeft_orc;}

    public Animation getRunRight_orc() {return runRight_orc;}

    public Animation getRunUp_orc() {return runUp_orc;}

    public Animation getRunDown_orc() {return runDown_orc;}

    public Animation getCoinAnimation(){return coinAnimation;}

    public Animation getRunLeft_mc() {return runLeft_mc;}

    public Animation getRunRight_mc() {return runRight_mc;}

    public Animation getRunUp_mc() {return runUp_mc;}

    public Animation getRunDown_mc() {return runDown_mc;}

    public TextureRegion getIdle_mc() {return idle_mc;}

    public TextureRegion getIdleLeft_mc() {return idleLeft_mc;}

    public TextureRegion getIdleDown_mc() {return idleDown_mc;}

    public TextureRegion getIdleRight_mc() {return idleRight_mc;}

    public Animation getFanim() {return fanim;}

    public Animation getIbAnim(){ return ibAnim;}

    public Animation getLbAnim(){return lbAnim;}

    public TextureRegion getFanim_region(){return fanim_region;}

    public TextureRegion getIceball_region(){return iceball_region;}

    public TextureRegion getLightningball_region(){return lightningball_region;}

    public TextureRegion getIdleElf(){
        return idle_elf;
    }

    public TextureRegion getDead_elf(){return dead_elf;}

    public Animation getElfShootingRight(){return shootingRight;}

    public Animation getElfShootingLeft(){return shootingLeft;}

    public TextureRegion getLightBall(){return lightBall;}

    public Animation getBatUp(){return bat_up;}

    public Animation getBatDown(){return bat_down;}

    public Animation getBatLeft(){return bat_left;}

    public Animation getBatRight(){return bat_right;}

    public Animation getPlant_left(){return plant_left;}

    public Animation getPlant_right(){return plant_right;}

    public Animation getPlant_up(){return plant_up;}

    public Animation getPlant_down(){return plant_down;}

    @Override
    public void dispose() {
        sidescroll.dispose();
        simpleSkeleton.dispose();
        simpleOrc.dispose();
        coin.dispose();
        mainCharacter.dispose();
        dungeon.dispose();
    }
}
