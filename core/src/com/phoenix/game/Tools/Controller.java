package com.phoenix.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Game;

/**
 * Created by alesd on 3/21/2018.
 */

public class Controller {

    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed, firePressed, lightningPressed, icePressed, bagPressed, hpPressed, mpPressed, jumpPressed;
    private OrthographicCamera cam;
    private Skin skin;
    private boolean sideScroll;

    public Controller(SpriteBatch batch, boolean sideScroll){
        cam = new OrthographicCamera();
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
        stage = new Stage(viewport, batch);
        skin = AnimationHandler.getAnimationHandler().getSkin();
        this.sideScroll = sideScroll;

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.NUM_1:
                        firePressed = true;
                        break;
                    case Input.Keys.NUM_2:
                        icePressed = true;
                        break;
                    case Input.Keys.NUM_3:
                        lightningPressed = true;
                        break;
                    case Input.Keys.NUM_4:
                        hpPressed = true;
                        break;
                    case Input.Keys.NUM_5:
                        mpPressed = true;
                        break;
                    case Input.Keys.I:
                        bagPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        jumpPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.NUM_1:
                        firePressed = false;
                        break;
                    case Input.Keys.NUM_2:
                        icePressed = false;
                        break;
                    case Input.Keys.NUM_3:
                        lightningPressed = false;
                        break;
                    case Input.Keys.NUM_4:
                        hpPressed = false;
                        break;
                    case Input.Keys.NUM_5:
                        mpPressed = false;
                        break;
                    case Input.Keys.I:
                        bagPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        jumpPressed = false;
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        Table icon_table = new Table(skin);
        table.left().bottom();
        icon_table.right().top();

        Image upImg = new Image(new Texture("flatDark25.png"));
        upImg.setSize(70, 70);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("flatDark26.png"));
        downImg.setSize(70, 70);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("flatDark24.png"));
        rightImg.setSize(70, 70);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("flatDark23.png"));
        leftImg.setSize(70, 70);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image fireImg = new Image(new Texture("fireball.png"));
        fireImg.setSize(70, 70);
        fireImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                firePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                firePressed = false;
            }
        });

        Image iceImg = new Image(new Texture("blizzard.png"));
        iceImg.setSize(70, 70);
        iceImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                icePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                icePressed = false;
            }
        });

        Image lightImg = new Image(new Texture("lightning.png"));
        lightImg.setSize(70, 70);
        lightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lightningPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                lightningPressed = false;
            }
        });

        Image bagImg = new Image(new Texture("bag.png"));
        bagImg.setSize(70, 70);
        bagImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bagPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bagPressed = false;
            }
        });

        Image hpImg = new Image(new Texture("hppotion.png"));
        hpImg.setSize(70, 70);
        hpImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hpPressed = false;
            }
        });

        Image mpImg = new Image(new Texture("mppotion.png"));
        mpImg.setSize(70, 70);
        mpImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mpPressed = false;
            }
        });

        Image jumpImg = new Image(new Texture("jump.png"));
        jumpImg.setSize(70, 70);
        jumpImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = false;
            }
        });

        if(!this.sideScroll) {
            icon_table.add(lightImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY().padRight(50);
            icon_table.add();
            icon_table.add(hpImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.row();
            icon_table.add(fireImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY().padRight(50);
            icon_table.add();
            icon_table.add(bagImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.row();
            icon_table.add(iceImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY().padRight(50);
            icon_table.add();
            icon_table.add(mpImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.setDebug(true);
            icon_table.setFillParent(true);
        }
        else{
            icon_table.add("");
            icon_table.add();
            icon_table.add(hpImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.row();
            icon_table.add(jumpImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY().padRight(50);
            icon_table.add();
            icon_table.add(bagImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.row();
            icon_table.add();
            icon_table.add();
            icon_table.add(mpImg).size(fireImg.getWidth(), fireImg.getHeight()).expandY();
            icon_table.setDebug(true);
            icon_table.setFillParent(true);
        }

        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();
        table.setDebug(true);

        stage.addActor(icon_table);
        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isFirePressed() {return firePressed;}

    public boolean isIcePressed() {return icePressed;}

    public boolean isLightningPressed() {return lightningPressed;}

    public boolean isBagPressed() {return bagPressed;}

    public boolean isHpPressed() {return hpPressed;}

    public boolean isMpPressed() {return mpPressed;}

    public boolean isJumpPressed() {return jumpPressed;}

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void resetIP(){
        Gdx.input.setInputProcessor(stage);
    }

    public void disableMovement(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    public void resetBag(){
        bagPressed = false;
    }
}
