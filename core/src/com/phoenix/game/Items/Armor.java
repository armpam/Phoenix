package com.phoenix.game.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by alesd on 3/30/2018.
 */

public class Armor extends EquipableItem {

    public Armor(String group, String pType){
        try {
            XmlReader.Element root = xml.parse(Gdx.files.internal("xml/equipment.xml"));
            XmlReader.Element type = root.getChildByName(group).getChildByName(pType);

            name = type.get("name");
            description = type.get("description");
            effect = type.getInt("effect");
            sellPrice = type.getInt("sellprice");
            buyPrice = type.getInt("buyprice");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
