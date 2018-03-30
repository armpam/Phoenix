package com.phoenix.game.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by alesd on 3/30/2018.
 */

public class Weapon extends EquipableItem {

    public Weapon(String group, String pType){
            XmlReader.Element type = getNode(group, pType);

           name = type.get("name");
           description = type.get("description");
           effect = type.getInt("effect");
    }

    private XmlReader.Element getNode(String group, String pType){
        try {
            XmlReader.Element root = xml.parse(Gdx.files.internal("xml/equipment.xml"));
            return root.getChildByName(group).getChildByName(pType);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
