package com.phoenix.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.phoenix.game.Game;
import com.phoenix.game.Items.Armor;
import com.phoenix.game.Items.EquipableItem;
import com.phoenix.game.Items.HpPotion;
import com.phoenix.game.Items.MpPotion;
import com.phoenix.game.Items.UsableItem;
import com.phoenix.game.Items.Weapon;
import com.phoenix.game.Maps.RectTileObject;
import com.phoenix.game.Screens.GameScreen;
import com.phoenix.game.Tools.DialogHandler;

/**
 * Created by alesd on 5/3/2018.
 */

public class NPC extends RectTileObject {

    private GameScreen screen;
    private String name;
    private String dialog;
    private XmlReader.Element type;
    private Array<UsableItem> usableItems;
    private Array<EquipableItem> equipableItems;

    private XmlReader xml = new XmlReader();

    public NPC(World world, TiledMap map, MapObject object, GameScreen screen, String group) {
        super(world, map, object);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(Game.ROCK_BIT);

        type = getNode(group);

        name = type.get("name");
        dialog = type.get("dialog");

        if(type.getName().equals("type_5")){
            usableItems = new Array<UsableItem>();
            equipableItems = new Array<EquipableItem>();
            initShop();
        }
    }

    private XmlReader.Element getNode(String group){
        try {
            XmlReader.Element root = xml.parse(Gdx.files.internal("xml/npc_dialogs.xml"));
            return root.getChildByName(group);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onPlayerHit() {
        if(!type.getName().equals("type_5")){
            DialogHandler.getDialogHandler().npcDialog(screen.getStage(), name, dialog, screen);
        }
        else{
            DialogHandler.getDialogHandler().vendorDialog(screen.getStage(), name, dialog, screen, this);
        }
    }

    @Override
    public void onFireBallHit() {

    }

    private void initShop(){
        equipableItems.add(new Weapon("swords", "type_3"));
        equipableItems.add(new Weapon("swords", "type_2"));
        equipableItems.add(new Armor("chests", "type_3"));
        equipableItems.add(new Armor("chests", "type_2"));

        for(int i = 0; i < 2; i++ ){
            usableItems.add(new HpPotion());
            usableItems.add(new MpPotion());
        }

    }

    @Override
    public void setCategoryFilter(short bit) {
        super.setCategoryFilter(bit);
    }

    @Override
    public TiledMapTileLayer.Cell getCell(int layerNumber) {
        return super.getCell(layerNumber);
    }

    public Array<EquipableItem> getEquipableItems() {
        return equipableItems;
    }

    public Array<UsableItem> getUsableItems() {
        return usableItems;
    }
}

