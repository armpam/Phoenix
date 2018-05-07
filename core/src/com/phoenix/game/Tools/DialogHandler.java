package com.phoenix.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.phoenix.game.Entities.NPC;
import com.phoenix.game.Game;
import com.phoenix.game.Screens.BuyScreen;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Screens.SellScreen;

/**
 * Created by alesd on 5/1/2018.
 */

public class DialogHandler {

    private Skin skin = AnimationHandler.getAnimationHandler().getSkin();
    private static DialogHandler mDialogHandler;

    private DialogHandler(){

    }

    public static DialogHandler getDialogHandler(){
        if(mDialogHandler == null){
            mDialogHandler = new DialogHandler();
        }
        return mDialogHandler;
    }

    public void simpleDialog(Stage stage, String title, String text, final GameScreen screen){
        Dialog dialog = new Dialog(title, skin){
            @Override
            protected void result (Object object) {
                if((Boolean)object == true){
                    screen.getController().disableMovement();
                    screen.getController().resetIP();
                    screen.resume();
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
                }
            }
        };
        dialog.text("Has obtenido " + text );
        dialog.button("Aceptar", true);
        dialog.show(screen.getStage());
        Gdx.input.setInputProcessor(stage);
        screen.pause();
    }

    public void npcDialog(Stage stage, String title, String text, final GameScreen screen){
        Dialog dialog = new Dialog(title, skin){
            @Override
            protected void result (Object object) {
                if((Boolean)object == true){
                    screen.getController().disableMovement();
                    screen.getController().resetIP();
                    screen.resume();
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
                }
            }
        };
        dialog.text(text.replaceAll("\\\\n", "\n"));
        dialog.button("Aceptar", true);
        dialog.show(screen.getStage());
        Gdx.input.setInputProcessor(stage);
        screen.pause();
    }

    public void vendorDialog(Stage stage, String title, String text, final GameScreen screen, final NPC npc){
        Dialog dialog = new Dialog(title, skin){
            @Override
            protected void result (Object object) {
                if((Boolean)object == true){
                    screen.getController().disableMovement();
                    ScreenHandler.getScreenHandler().setScreen(new BuyScreen(screen.getGame(), screen, screen.getMcharacter(), npc));
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
                    screen.resume();
                }
                if((Boolean)object == false){
                    screen.getController().disableMovement();
                    ScreenHandler.getScreenHandler().setScreen(new SellScreen(screen.getGame(), screen, screen.getMcharacter()));
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/pick.wav", Sound.class).play(Game.volume);
                    screen.resume();
                }
            }
        };
        dialog.text(text.replaceAll("\\\\n", "\n"));
        dialog.button("Comprar", true);
        dialog.button("Vender", false);
        dialog.show(screen.getStage());
        Gdx.input.setInputProcessor(stage);
        screen.pause();
    }
}
