package com.phoenix.game.Screens;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;
import com.phoenix.game.Items.Armor;
import com.phoenix.game.Items.EquipableItem;
import com.phoenix.game.Items.UsableItem;
import com.phoenix.game.Items.Weapon;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 5/3/2018.
 */

public class SellScreen extends SubMenu {

    public SellScreen(Game game, GameScreen screen, MainCharacter mc) {
        super(game, screen, mc);
        titleLabel.setText("VENDER");
        showItems();
    }

    private void showItems(){
        infoTable.add(new Label("", skin));
        infoTable.row();
        for(final UsableItem item : mc.getUsableInventory()){
            Label label = new Label(item.getName(), skin);
            Label priceLabel = new Label(Integer.toString(item.getSellPrice()), skin);
            label.addListener(new ClickListener(){

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    mc.sellUsableItem(item);
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/coin.ogg", Sound.class).play(Game.volume);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    descriptionLabel.setText(item.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    descriptionLabel.setText("Descripción");
                }
            });
            infoTable.add(label);
            infoTable.add(priceLabel);
            infoTable.row();
        }

        infoTable.add(new Label("", skin));
        infoTable.row();
        for(final EquipableItem item : mc.getEquipableInventory()){
            Label label = new Label(item.getName(), skin);
            Label priceLabel = new Label(Integer.toString(item.getsellPrice()), skin);
            label.addListener(new ClickListener(){

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    mc.sellEquipableItem(item);
                    SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/coin.ogg", Sound.class).play(Game.volume);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    descriptionLabel.setText(item.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    descriptionLabel.setText("Descripción");
                }
            });
            infoTable.add(label);
            infoTable.add(priceLabel);
            infoTable.row();
        }
    }
}
