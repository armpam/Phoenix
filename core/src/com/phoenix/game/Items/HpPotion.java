package com.phoenix.game.Items;


/**
 * Created by alesd on 3/28/2018.
 */

public class HpPotion extends UsableItem {

    public HpPotion(){
        name = "Poción de vida";
        effect = 200;
        description = "Recupera 200 puntos de vida";
        sellPrice = 20;
        buyPrice = 40;
    }
}
