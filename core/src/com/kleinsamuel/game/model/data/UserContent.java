package com.kleinsamuel.game.model.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.model.maps.MapFactory;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 29.05.17.
 */

public class UserContent {

    /* ID to map object to a unique user */
    public int ID;
    /* users NAME */
    public String NAME;
    public float x;
    public float y;

    public String mapIdentifier;

    public long VALUE_ATTACK;
    public long VALUE_ATTACK_SPEED;
    public long VALUE_DEFENSE;
    public float VALUE_MOVING_SPEED;
    public int VALUE_RANGE;

    /* users LEVEL needed for calculation of several values */
    public int LEVEL;
    /* users current xp */
    public int CURRENT_EXPERIENCE;
    /* users max MAX_HEALTH */
    public int MAX_HEALTH;
    /* users current MAX_HEALTH */
    public int CURRENT_HEALTH;
    /* users MAX_MANA */
    public int MAX_MANA;
    /* users current MAX_MANA */
    public int CURRENT_MANA;
    /* users MONEY */
    public int MONEY;
    public int SKULLS;
    /* users max carry amount */
    public int BAG_SIZE;
    /* users bag containing items */
    private HashMap<Integer, Integer> bag;
    /* equipped items
     * key = int position for equipment slot
     * value = int ID for item
     */
    private HashMap<Integer, Integer> equippedItems;


    public UserContent() {
        this.bag = new HashMap();
        this.equippedItems = new HashMap();
    }

    public void writeToFile() {

        DebugMessageFactory.printInfoMessage("READ FILE IN LOCAL DIR: "+Gdx.files.getLocalStoragePath());
        FileHandle handle = Gdx.files.local("player.txt");

        Vector3 arrayPos = Utils.getArrayCoordinates(x, y);

        StringBuilder sb = new StringBuilder();

        sb.append("ID:"+ ID +"\n");
        sb.append("NAME:"+ NAME +"\n");
        sb.append("x:"+(int)arrayPos.x+"\n");
        sb.append("y:"+(int)arrayPos.y+"\n");
        sb.append("map:"+mapIdentifier+"\n");
        sb.append("stat_attack:"+VALUE_ATTACK+"\n");
        sb.append("stat_attack_speed:"+VALUE_ATTACK_SPEED+"\n");
        sb.append("stat_defense:"+VALUE_DEFENSE+"\n");
        sb.append("stat_moving_speed:"+VALUE_MOVING_SPEED+"\n");
        sb.append("stat_range:"+VALUE_RANGE+"\n");
        sb.append("LEVEL:"+ LEVEL +"\n");
        sb.append("exp:"+ CURRENT_EXPERIENCE +"\n");
        sb.append("MAX_HEALTH:"+ MAX_HEALTH +"\n");
        sb.append("currenthealth:"+ CURRENT_HEALTH +"\n");
        sb.append("MAX_MANA:"+ MAX_MANA +"\n");
        sb.append("currentmana:"+ CURRENT_MANA +"\n");
        sb.append("MONEY:"+ MONEY +"\n");
        sb.append("SKULLS:"+SKULLS +"\n");
        sb.append("BAG_SIZE:"+ BAG_SIZE +"\n");
        sb.append("bag_content:");
        for(Map.Entry<Integer, Integer> e : bag.entrySet()) {
            sb.append(e.getKey()+"-"+e.getValue()+",");
        }
        sb.append("\n");
        sb.append("equipped:");
        for(Map.Entry<Integer, Integer> e : equippedItems.entrySet()) {
            sb.append(e.getKey()+"-"+e.getValue()+",");
        }
        sb.append("\n");

        DebugMessageFactory.printNormalMessage("SAVED USER CONTENT TO FILE.");

        handle.writeString(sb.toString(), false);
    }

    public static UserContent readFromFile() {

        FileHandle handle = Gdx.files.local("player.txt");

        DebugMessageFactory.printNormalMessage("SAVEGAME: "+handle.path());

        //DebugMessageFactory.printNormalMessage("READING PLAYER SAVEGAME..."+ handle.readString());

		/* if user file does not exists start with standard */
        if(!handle.exists()) {
            DebugMessageFactory.printInfoMessage("NO SAVEGAME FOUND. CREATED NEW STANDARD SAVEGAME.");
            return createStandard();
        }

        UserContent uc = new UserContent();

        String[] tmp = handle.readString().split("\n");

        DebugMessageFactory.printInfoMessage("USER CONTENT:");

        for (int i = 0; i < tmp.length; i++) {

            DebugMessageFactory.printInfoMessage(tmp[i]);

            String line = tmp[i];
            String[] lineArray = line.split(":");

            if (lineArray[0].equals("ID")) {
                uc.ID = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("NAME")) {
                uc.NAME = lineArray[1];
            } else if (lineArray[0].equals("x")) {
                uc.x = Integer.parseInt(lineArray[1])*Utils.TILEWIDTH;
            } else if (lineArray[0].equals("y")) {
                uc.y = Integer.parseInt(lineArray[1])*Utils.TILEHEIGHT;
            } else if (lineArray[0].equals("map")) {
                uc.mapIdentifier = lineArray[1];
            } else if (lineArray[0].equals("stat_attack")) {
                uc.VALUE_ATTACK = Long.parseLong(lineArray[1]);
            } else if (lineArray[0].equals("stat_attack_speed")) {
                uc.VALUE_ATTACK_SPEED = Long.parseLong(lineArray[1]);
            } else if (lineArray[0].equals("stat_defense")) {
                uc.VALUE_DEFENSE = Long.parseLong(lineArray[1]);
            } else if (lineArray[0].equals("stat_moving_speed")) {
                uc.VALUE_MOVING_SPEED = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("stat_range")) {
                uc.VALUE_RANGE = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("LEVEL")) {
                uc.LEVEL = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("exp")) {
                uc.CURRENT_EXPERIENCE = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("MAX_HEALTH")) {
                uc.MAX_HEALTH = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("currenthealth")) {
                uc.CURRENT_HEALTH = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("MAX_MANA")) {
                uc.MAX_MANA = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("currentmana")) {
                uc.CURRENT_MANA = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("MONEY")) {
                uc.MONEY = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("SKULLS")) {
                uc.SKULLS = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("BAG_SIZE")) {
                uc.BAG_SIZE = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("bag_content")) {
                uc.bag = (lineArray.length >= 2) ? fillBag(lineArray[1]) : fillBag("");
            } else if (lineArray[0].equals("equipped")) {
                uc.equippedItems = (lineArray.length >= 2) ? parseEquippedItems(lineArray[1]) : parseEquippedItems("");
            }
        }
        return uc;
    }

    private static HashMap<Integer, Integer> fillBag(String content){
        HashMap<Integer, Integer> map = new HashMap();

        if(content.equals("")) {
            return map;
        }

        String[] tmp = content.split(",");

        for (int i = 0; i < tmp.length; i++) {
            int key = Integer.parseInt(tmp[i].split("-")[0]);
            int value = Integer.parseInt(tmp[i].split("-")[1]);
            map.put(key, value);
        }

        return map;
    }

    private static HashMap<Integer, Integer> parseEquippedItems(String equippedString){
        HashMap<Integer, Integer> map = new HashMap();

        if(equippedString.equals("")) {
            return map;
        }

        String[] tmp = equippedString.split(",");

        for (int i = 0; i < tmp.length; i++) {
            int key = Integer.parseInt(tmp[i].split("-")[0]);
            int value = Integer.parseInt(tmp[i].split("-")[1]);
            map.put(key, value);
        }

        return map;
    }

    public static UserContent createStandard() {
        UserContent uc = new UserContent();
        uc.ID = -1;
        uc.x = 5*Utils.TILEWIDTH;
        uc.y = 17*Utils.TILEHEIGHT;
        uc.mapIdentifier = MapFactory.START_HOUSE;
        uc.VALUE_ATTACK = 1;
        uc.VALUE_ATTACK_SPEED = 1000;
        uc.VALUE_DEFENSE = 0;
        uc.VALUE_MOVING_SPEED = 0.5f;
        uc.VALUE_RANGE = 1;
        uc.MAX_HEALTH = CharacterFactory.getHealthForLevelLinear(1, 1.0);
        uc.CURRENT_HEALTH = CharacterFactory.getHealthForLevelLinear(1, 1.0);
        uc.MAX_MANA = 10;
        uc.CURRENT_MANA = 10;
        uc.MONEY = 12345678;
        uc.SKULLS = 31;
        uc.LEVEL = 1;
        uc.CURRENT_EXPERIENCE = 40;
        uc.NAME = "unknown";
        uc.BAG_SIZE = 30;
        uc.bag = new HashMap();

        uc.bag.put(1,1);
        uc.bag.put(2,1);
        uc.bag.put(3,1);
        uc.bag.put(4,1);
        uc.bag.put(5,1);
        uc.bag.put(6,2);
        uc.bag.put(7,3);

        uc.bag.put(1001, 31);

        uc.equippedItems = new HashMap();
        return uc;
    }

    public HashMap<Integer, Integer> getBag() {
        return bag;
    }

    /* put item in bag */
    public boolean putInBag(int i) {
        if(bag.size() > BAG_SIZE) {
            return false;
        }
        if(bag.containsKey(i)) {
            bag.put(i, bag.get(i)+1);
        }else {
            bag.put(i, 1);
        }
        return true;
    }

    public int getCurrentBagSize(){
        int size = 0;

        for(Map.Entry<Integer, Integer> entry : bag.entrySet()){
            if(checkIfEquipped(entry.getKey()) == -1) {
                if (ItemFactory.isStackable(entry.getKey())) {
                    size += 1;
                } else {
                    size += entry.getValue();
                }
            }
        }

        return size;
    }

    public void removeFromBag(int item_key){
        int amount = bag.get(item_key);
        if(amount <= 1){
            bag.remove(item_key);
        }else{
            bag.put(item_key, amount-1);
        }
    }

    public HashMap<Integer, Integer> getEquippedItems() {
        return equippedItems;
    }

    public int checkIfEquipped(int itemId) {
        for(Map.Entry<Integer, Integer> entry : equippedItems.entrySet()) {
            if(entry.getValue() == itemId) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public void setEquippedItems(HashMap<Integer, Integer> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public void equipItemById(int itemId, int slotId) {
        equippedItems.put(slotId, itemId);
    }

    public void unequipItemBySlotId(int slotId) {
        equippedItems.remove(slotId);
    }

}
