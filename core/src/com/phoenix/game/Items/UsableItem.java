package com.phoenix.game.Items;

/**
 * Created by alesd on 3/28/2018.
 */

public abstract class UsableItem {

    protected String name;
    protected int effect;
    protected String description;
    protected int sellPrice;
    protected int buyPrice;

    public UsableItem(){

    }

    public int getEffect(){
        return effect;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){return name;}

    public int getSellPrice(){return sellPrice;}

    public int getBuyPrice(){
        return  buyPrice;
    }

}
