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
import com.phoenix.game.Items.HpPotion;
import com.phoenix.game.Items.MpPotion;
import com.phoenix.game.Items.UsableItem;
import com.phoenix.game.Items.Weapon;
import com.phoenix.game.Tools.SoundHandler;

/**
 * Created by alesd on 4/4/2018.
 */

public class EquipmentMenu extends SubMenu {

    private Label currentWeapon;
    private Label currentArmor;

    public EquipmentMenu(Game game, MainMenu prevMenu, MainCharacter mc) {
        super(game, prevMenu, mc);
        titleLabel.setText("EQUIPO");
        showEquipment();
    }

    private void showEquipment(){
        int i = 1;
        currentWeapon = new Label("Arma equipada: " + mc.getEqWeapon().getName(), skin);
        currentArmor = new Label("Armadura equipada: " + mc.getEqArmor().getName(), skin);
        infoTable.add(new Label("", skin));
        infoTable.row().colspan(2);
        infoTable.add(currentWeapon);
        infoTable.row().colspan(2);
        infoTable.add(currentArmor);
        infoTable.row();
        infoTable.add(new Label("", font));
        infoTable.row();

        for(final EquipableItem item : mc.getEquipableInventory()){
            Label label = new Label(item.getName(), skin);
            label.addListener(new ClickListener(){

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(item instanceof Weapon){
                        mc.switchWeapon((Weapon)item);
                        SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/equip.wav", Sound.class).play(Game.volume);
                    }
                    else if(item instanceof Armor){
                        mc.switchArmor((Armor)item);
                        SoundHandler.getSoundHandler().getAssetManager().get("audio/sounds/equip.wav", Sound.class).play(Game.volume);
                    }
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
            if((i % 2 == 0) && (i != 0)){
                infoTable.row();
            }
            i++;
        }
    }
}
