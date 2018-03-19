package com.phoenix.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by alesd on 3/19/2018.
 */

public class AnimationHandler {

    private static AnimationHandler mAnimationHandler = null;

    private Texture sidescroll;

    //Esqueleto simple
    private Texture simpleSkeleton;
    private final int MAIN_TEXT_WIDTH = 64, MAIN_TEXT_HEIGHT =64 ; //Altura y anchura de los sprites del spritesheet del MC
    private TextureRegion idle_sk; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))

    private TextureRegion idleLeft_sk; // Posturas sin hacer nada mirando hacia distintos lados
    private TextureRegion idleDown_sk;
    private TextureRegion idleRight_sk;

    private Animation runLeft_sk;
    private Animation runRight_sk;
    private Animation runUp_sk;
    private Animation runDown_sk;

    //Orco simple
    private Texture simpleOrc;
    private TextureRegion idle_orc; //Postura sin hacer nada mirando a la izquierda (Sprite (TextureRegion))

    private TextureRegion idleLeft_orc; // Posturas sin hacer nada mirando hacia distintos lados
    private TextureRegion idleDown_orc;
    private TextureRegion idleRight_orc;

    private Animation runLeft_orc;
    private Animation runRight_orc;
    private Animation runUp_orc;
    private Animation runDown_orc;

    //Moneda
    private Texture coin;

    private Animation coinAnimation;


    //Personaje principal
    private Texture mainCharacter;

    private TextureRegion movingBlock;

    private AnimationHandler(){

        sidescroll = new Texture(Gdx.files.internal("generic_platformer_tiles.png"));
        simpleSkeleton = new Texture(Gdx.files.internal("simple_skeleton.png"));
        simpleOrc = new Texture(Gdx.files.internal("simple_orc.png"));
        coin = new Texture(Gdx.files.internal("coin.png"));
        mainCharacter = new Texture(Gdx.files.internal("main.png"));

        movingBlock = new TextureRegion(sidescroll, 32, 192, 64, 32);

        initSkeletonAnimations(simpleSkeleton);
        initOrcAnimations(simpleOrc);
        initCoinAnimation(coin);
    }

    public static AnimationHandler getAnimationHandler(){
        if(mAnimationHandler == null){
            mAnimationHandler = new AnimationHandler();
        }
        return mAnimationHandler;
    }

    private void initSkeletonAnimations(Texture mainTexture){

        Array<TextureRegion> frames = new Array<TextureRegion>();

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
        idleLeft_sk = new TextureRegion(mainTexture, 0 , 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleRight_sk = new TextureRegion(mainTexture, 0 , 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleDown_sk = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initOrcAnimations(Texture mainTexture){
        Array<TextureRegion> frames = new Array<TextureRegion>();

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
        idleLeft_orc = new TextureRegion(mainTexture, 0 , 64, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleRight_orc = new TextureRegion(mainTexture, 0 , 192, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
        idleDown_orc = new TextureRegion(mainTexture, 0 , 128, MAIN_TEXT_WIDTH, MAIN_TEXT_HEIGHT );
    }

    private void initCoinAnimation(Texture mainTexture){

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 7; i++){
            frames.add(new TextureRegion(mainTexture, i* 64, 0, 32, 32));
        }
        coinAnimation = new Animation(0.2f, frames);
        frames.clear();

    }

    public Texture getSidescroll() {
        return sidescroll;
    }

    public Texture getSimpleSkeleton() {
        return simpleSkeleton;
    }

    public Texture getSimpleOrc() {
        return simpleOrc;
    }

    public Texture getCoin() {
        return coin;
    }

    public TextureRegion getMovingBlock() {
        return movingBlock;
    }

    public Texture getMainCharacter() {
        return mainCharacter;
    }

    public TextureRegion getIdle_sk() {return idle_sk;}

    public TextureRegion getIdleLeft_sk() {return idleLeft_sk;}

    public TextureRegion getIdleDown_sk() {return idleDown_sk;}

    public TextureRegion getIdleRight_sk() {return idleRight_sk;}

    public Animation getRunLeft_sk() {return runLeft_sk;}

    public Animation getRunRight_sk() {return runRight_sk;}

    public Animation getRunUp_sk() {return runUp_sk;}

    public Animation getRunDown_sk() {return runDown_sk;}

    public TextureRegion getIdle_orc() {return idle_orc;}

    public TextureRegion getIdleLeft_orc() {return idleLeft_orc;}

    public TextureRegion getIdleDown_orc() {return idleDown_orc;}

    public TextureRegion getIdleRight_orc() {return idleRight_orc;}

    public Animation getRunLeft_orc() {return runLeft_orc;}

    public Animation getRunRight_orc() {return runRight_orc;}

    public Animation getRunUp_orc() {return runUp_orc;}

    public Animation getRunDown_orc() {return runDown_orc;}

    public Animation getCoinAnimation(){return coinAnimation;}
}
