package com.phoenix.game.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;
import com.phoenix.game.Items.HpPotion;
import com.phoenix.game.Items.MpPotion;
import com.phoenix.game.Items.UsableItem;

/**
 * Created by alesd on 4/4/2018.
 */

public class ItemsMenu extends SubMenu {

    public ItemsMenu(Game game, MainMenu prevMenu, MainCharacter mc) {
        super(game, prevMenu, mc);
        titleLabel.setText("OBJETOS");
        showItems();
    }

    private void showItems(){
        int i = 1;
        infoTable.add(new Label("", skin));
        infoTable.row();
        for(final UsableItem item : mc.getUsableInventory()){
            Label label = new Label(item.getName(), skin);
            label.addListener(new ClickListener(){

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(item instanceof HpPotion){
                        mc.useHpPot();
                    }
                    else if(item instanceof MpPotion){
                        mc.useMpPot();
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
