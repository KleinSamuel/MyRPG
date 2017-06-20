package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sam on 16.06.17.
 */

public class MerchantInteraction extends Interactive{

    private PlayScreen playScreen;

    private int merchantIdentifier;

    private boolean BUY;

    private float EXIT_X = 913 * Utils.FACTOR;
    private float EXIT_Y = 496 * Utils.FACTOR;
    private float EXIT_WIDTH = 38 * Utils.FACTOR;
    private float EXIT_HEIGHT = 37 * Utils.FACTOR;

    private float SWITCH_X = 94 * Utils.FACTOR;
    private float SWITCH_Y = 476 * Utils.FACTOR;
    private float SWITCH_WIDTH = 81 * Utils.FACTOR;
    private float SWITCH_HEIGHT = 37 * Utils.FACTOR;

    private float LEFT_UP_X = 17 * Utils.FACTOR;
    private float LEFT_UP_Y = 378 * Utils.FACTOR;
    private int LEFT_COL = 3;
    private int LEFT_ROW = 5;

    private float MIDDLE_UP_X = 279 * Utils.FACTOR;
    private float MIDDLE_UP_Y = 378 * Utils.FACTOR;
    private int MIDDLE_COL = 5;
    private int MIDDLE_ROW = 5;

    private float RIGHT_UP_X = 711 * Utils.FACTOR;
    private float RIGHT_UP_Y = 378 * Utils.FACTOR;
    private int RIGHT_COL = 3;
    private int RIGHT_ROW = 5;

    private float CELL_WIDTH = 63 * Utils.FACTOR;
    private float CELL_HEIGHT = 63 * Utils.FACTOR;
    private float PADDING_LEFT = 22 * Utils.FACTOR;
    private float PADDING_TOP = 22 * Utils.FACTOR;

    private float MONEY_SKULL_BAR_X = 294 * Utils.FACTOR;
    private float MONEY_SKULL_BAR_Y = 490 * Utils.FACTOR;

    private TreeMap<Integer, Integer> consumables;
    private TreeMap<Integer, Integer> equipment;
    private TreeMap<Integer, Integer> weapons;

    private ArrayList<Vector3> consumables_positions;
    private ArrayList<Vector3> equipment_positions;
    private ArrayList<Vector3> weapons_positions;

    /* hashmap containing consumables position index as key and consumable id as value */
    private TreeMap<Integer, Integer> consumablesAssignedTo;
    private TreeMap<Integer, Integer> equipmentAssignedTo;
    private TreeMap<Integer, Integer> weaponsAssignedTo;

    private TreeMap<Integer, Integer> consumablesAssignedTo_player;
    private TreeMap<Integer, Integer> equipmentAssignedTo_player;
    private TreeMap<Integer, Integer> weaponsAssignedTo_player;

    private MerchantItemInfoWindow merchantItemInfoWindow;

    public MerchantInteraction(PlayScreen playScreen, int merchantIdentifier){
        this.playScreen = playScreen;
        this.merchantIdentifier = merchantIdentifier;
        this.consumables = MerchantFactory.getConsumablesForIdentifier(merchantIdentifier);
        this.equipment = MerchantFactory.getEquipmentForIdentifier(merchantIdentifier);
        this.weapons = MerchantFactory.getWeaponsForIdentifier(merchantIdentifier);

        consumablesAssignedTo = new TreeMap<Integer, Integer>();
        equipmentAssignedTo = new TreeMap<Integer, Integer>();
        weaponsAssignedTo = new TreeMap<Integer, Integer>();

        consumablesAssignedTo_player = new TreeMap<Integer, Integer>();
        equipmentAssignedTo_player = new TreeMap<Integer, Integer>();
        weaponsAssignedTo_player = new TreeMap<Integer, Integer>();

        this.consumables_positions = initConsumablePositions();
        this.equipment_positions = initEquipmentPositions();
        this.weapons_positions = initWeaponPositions();

        assignConsumables();
        assignEquipment();
        assignWeapons();

        this.BUY = true;
    }

    private ArrayList<Vector3> initConsumablePositions(){
        ArrayList<Vector3> outputList = new ArrayList<Vector3>();
        for (int row = 0; row < LEFT_ROW; row++) {
            float y = LEFT_UP_Y - ((CELL_HEIGHT+PADDING_TOP)*row);
            for (int col = 0; col < LEFT_COL; col++) {
                float x = LEFT_UP_X + ((CELL_WIDTH+PADDING_LEFT)*col);
                outputList.add(new Vector3(x, y, 0));
            }
        }
        return outputList;
    }

    private ArrayList<Vector3> initEquipmentPositions(){
        ArrayList<Vector3> outputList = new ArrayList<Vector3>();
        for (int row = 0; row < MIDDLE_ROW; row++) {
            float y = MIDDLE_UP_Y - ((CELL_HEIGHT+PADDING_TOP)*row);
            for (int col = 0; col < MIDDLE_COL; col++) {
                float x = MIDDLE_UP_X + ((CELL_WIDTH+PADDING_LEFT)*col);
                outputList.add(new Vector3(x, y, 0));
            }
        }
        return outputList;
    }

    private ArrayList<Vector3> initWeaponPositions(){
        ArrayList<Vector3> outputList = new ArrayList<Vector3>();
        for (int row = 0; row < RIGHT_ROW; row++) {
            float y = RIGHT_UP_Y - ((CELL_HEIGHT+PADDING_TOP)*row);
            for (int col = 0; col < RIGHT_COL; col++) {
                float x = RIGHT_UP_X + ((CELL_WIDTH+PADDING_LEFT)*col);
                outputList.add(new Vector3(x, y, 0));
            }
        }
        return outputList;
    }

    private void assignConsumables(){
        int index = 0;
        for(Integer consumableId : consumables.keySet()){
            consumablesAssignedTo.put(index, consumableId);
            index++;
        }
    }

    private void assignEquipment(){
        int index = 0;
        for(Integer equipmentId : equipment.keySet()){
            equipmentAssignedTo.put(index, equipmentId);
            index++;
        }
    }

    private void assignWeapons(){
        int index = 0;
        for(Integer weaponId : weapons.keySet()){
            weaponsAssignedTo.put(index, weaponId);
            index++;
        }
    }

    @Override
    public boolean handleClick(float screenX, float screenY) {

        if(merchantItemInfoWindow != null){
            if(merchantItemInfoWindow.handleClick(screenX, screenY)){
                merchantItemInfoWindow = null;
            }
            return false;
        }

        if(clickedOnExit(screenX, screenY)){
            return true;
        }
        if(clickedOnSwitch(screenX, screenY)){
            BUY = !BUY;
            return false;
        }

        int consumablesId = clickedOnConsumables(screenX, screenY);
        if(consumablesId != -1){
            DebugMessageFactory.printInfoMessage("CLICKED ON CONSUMABLES");
            int price = 0;
            if(BUY){
                price = consumables.get(consumablesId);
            }else{
                price = 100;
            }
            merchantItemInfoWindow = new MerchantItemInfoWindow(playScreen, consumablesId, price, BUY);
            return false;
        }

        int equipmentId = clickedOnEquipment(screenX, screenY);
        if(equipmentId != -1){
            DebugMessageFactory.printInfoMessage("CLICKED ON EQUIPMENT");
            int price = 0;
            if(BUY){
                price = equipment.get(equipmentId);
            }else{
                price = 100;
            }
            merchantItemInfoWindow = new MerchantItemInfoWindow(playScreen, equipmentId, price, BUY);
            return false;
        }

        int weaponId = clickedOnWeapons(screenX, screenY);
        if(weaponId != -1){
            DebugMessageFactory.printInfoMessage("CLICKED ON WEAPONS");
            int price = 0;
            if(BUY){
                price = weapons.get(weaponId);
            }else{
                price = 100;
            }
            merchantItemInfoWindow = new MerchantItemInfoWindow(playScreen, weaponId, price, BUY);
            return false;
        }

        return false;
    }

    private boolean clickedOnExit(float screenX, float screenY){
        if(screenX >= EXIT_X && screenX <= EXIT_X+EXIT_WIDTH && screenY >= EXIT_Y && screenY <= EXIT_Y*EXIT_HEIGHT){
            return true;
        }
        return false;
    }

    private boolean clickedOnSwitch(float screenX, float screenY){
        if(screenX >= SWITCH_X && screenX <= SWITCH_X+SWITCH_WIDTH && screenY >= SWITCH_Y && screenY <= SWITCH_Y*SWITCH_HEIGHT){
            return true;
        }
        return false;
    }

    private int clickedOnConsumables(float screenX, float screenY){
        int index = 0;
        for(Vector3 v3 : consumables_positions){
            if(screenX >= v3.x && screenX <= v3.x+CELL_WIDTH && screenY >= v3.y && screenY <= v3.y+CELL_HEIGHT){
                if(BUY) {
                    if (consumablesAssignedTo.containsKey(index)) {
                        return consumablesAssignedTo.get(index);
                    } else {
                        return -1;
                    }
                }else{
                    if (consumablesAssignedTo_player.containsKey(index)) {
                        return consumablesAssignedTo_player.get(index);
                    } else {
                        return -1;
                    }
                }
            }
            index++;
        }
        return -1;
    }

    private int clickedOnEquipment(float screenX, float screenY){
        int index = 0;
        for(Vector3 v3 : equipment_positions){
            if(screenX >= v3.x && screenX <= v3.x+CELL_WIDTH && screenY >= v3.y && screenY <= v3.y+CELL_HEIGHT){
                if(BUY) {
                    if (equipmentAssignedTo.containsKey(index)) {
                        return equipmentAssignedTo.get(index);
                    } else {
                        return -1;
                    }
                }else{
                    if (equipmentAssignedTo_player.containsKey(index)) {
                        return equipmentAssignedTo_player.get(index);
                    } else {
                        return -1;
                    }
                }
            }
            index++;
        }
        return -1;
    }

    private int clickedOnWeapons(float screenX, float screenY){
        int index = 0;
        for(Vector3 v3 : weapons_positions){
            if(screenX >= v3.x && screenX <= v3.x+CELL_WIDTH && screenY >= v3.y && screenY <= v3.y+CELL_HEIGHT){
                if(BUY) {
                    if (weaponsAssignedTo.containsKey(index)) {
                        return weaponsAssignedTo.get(index);
                    } else {
                        return -1;
                    }
                }else{
                    if (weaponsAssignedTo_player.containsKey(index)) {
                        return weaponsAssignedTo_player.get(index);
                    } else {
                        return -1;
                    }
                }
            }
            index++;
        }
        return -1;
    }

    public void drawMoneyString(SpriteBatch batch){
        Utils.basicFont.getData().setScale(0.4f, 0.5f);
        Utils.basicFont.setColor(new Color(255.0f, 255.0f, 0.0f, 10.0f));
        String s = ""+playScreen.player.content.MONEY;
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.basicFont, s);
        Utils.basicFont.draw(batch, s, MONEY_SKULL_BAR_X+25, MONEY_SKULL_BAR_Y+15);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(IS_TRIGGERED) {
            if(BUY) {
                batch.draw(Assets.manager.get(Assets.merchant_background_buy, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);
                drawMoneyString(batch);

                int index = 0;
                for(Map.Entry<Integer, Integer> entry : consumablesAssignedTo.entrySet()){
                    batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getValue()), Texture.class), consumables_positions.get(index).x, consumables_positions.get(index).y, CELL_WIDTH, CELL_HEIGHT);
                    index++;
                }
                /*for (int i = index; i < consumables_positions.size(); i++) {
                    batch.draw(Assets.manager.get(Assets.locked, Texture.class), consumables_positions.get(i).x, consumables_positions.get(i).y, CELL_WIDTH, CELL_HEIGHT);
                }*/

                index = 0;
                for(Map.Entry<Integer, Integer> entry : equipmentAssignedTo.entrySet()){
                    batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getValue()), Texture.class), equipment_positions.get(index).x, equipment_positions.get(index).y, CELL_WIDTH, CELL_HEIGHT);
                    index++;
                }

                index = 0;
                for(Map.Entry<Integer, Integer> entry : weaponsAssignedTo.entrySet()){
                    batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getValue()), Texture.class), weapons_positions.get(index).x, weapons_positions.get(index).y, CELL_WIDTH, CELL_HEIGHT);
                    index++;
                }

            }else{

                batch.draw(Assets.manager.get(Assets.merchant_background_sell, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);
                drawMoneyString(batch);

                int consumableIndex = 0;
                int equipmentIndex = 0;
                int weaponIndex = 0;
                HashSet<Integer> alreadySkipped = new HashSet<Integer>();
                for(Map.Entry<Integer, Integer> entry : playScreen.player.content.getBag().entrySet()){

                    int amount = entry.getValue();
                    if(ItemFactory.isStackable(entry.getKey())){
                        amount = 1;
                    }

                    for (int i = 0; i < amount; i++) {
                        if (playScreen.player.content.checkIfEquipped(entry.getKey()) != -1) {
                            if (!alreadySkipped.contains(entry.getKey())) {
                                alreadySkipped.add(entry.getKey());
                                continue;
                            }
                        }
                        if (ItemFactory.isEquipment(entry.getKey())) {
                            equipmentAssignedTo_player.put(equipmentIndex, entry.getKey());
                            batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), equipment_positions.get(equipmentIndex).x, equipment_positions.get(equipmentIndex).y, CELL_WIDTH, CELL_HEIGHT);
                            equipmentIndex++;
                        } else if (ItemFactory.isConsumable(entry.getKey())) {
                            consumablesAssignedTo_player.put(consumableIndex, entry.getKey());
                            batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), consumables_positions.get(consumableIndex).x, consumables_positions.get(consumableIndex).y, CELL_WIDTH, CELL_HEIGHT);
                            consumableIndex++;
                        } else if (ItemFactory.isWeapon(entry.getKey())) {
                            weaponsAssignedTo_player.put(weaponIndex, entry.getKey());
                            batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), weapons_positions.get(weaponIndex).x, weapons_positions.get(weaponIndex).y, CELL_WIDTH, CELL_HEIGHT);
                            weaponIndex++;
                        }
                    }
                }

            }

            if(merchantItemInfoWindow != null){
                merchantItemInfoWindow.render(batch);
            }

        }
    }
}
