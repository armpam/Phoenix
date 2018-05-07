package com.phoenix.game.Items;

import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by alesd on 3/30/2018.
 */

public abstract class EquipableItem {

    protected String name;
    protected String description;
    protected int effect;
    protected int sellPrice;
    protected  int buyPrice;

    protected XmlReader xml = new XmlReader();

    public EquipableItem(){

    }

    public int getEffect(){
        return  effect;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return  description;
    }

    public int getsellPrice(){return sellPrice;}

    public int getBuyPrice(){return  buyPrice;}
}
