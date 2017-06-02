package com.kleinsamuel.game.model.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 29.05.17.
 */

public class UserContent {

    /* id to map object to a unique user */
    public int id;
    /* users name */
    public String name;
    public int x;
    public int y;
    /* users level needed for calculation of several values */
    public int level;
    /* users current xp */
    public int experience;
    /* users max health */
    public int health;
    /* users current health */
    public int current_health;
    /* users mana */
    public int mana;
    /* users current mana */
    public int current_mana;
    /* users money */
    public int money;
    /* users max carry amount */
    public int bag_size;
    /* users bag containing items */
    private HashMap<Integer, Integer> bag;
    /* equipped items
     * key = int position for equipment slot
     * value = int id for item
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

        sb.append("id:"+id+"\n");
        sb.append("name:"+name+"\n");
        sb.append("x:"+(int)arrayPos.x+"\n");
        sb.append("y:"+(int)arrayPos.y+"\n");
        sb.append("level:"+level+"\n");
        sb.append("exp:"+experience+"\n");
        sb.append("health:"+health+"\n");
        sb.append("currenthealth:"+current_health+"\n");
        sb.append("mana:"+mana+"\n");
        sb.append("currentmana:"+current_mana+"\n");
        sb.append("money:"+money+"\n");
        sb.append("bag_size:"+bag_size+"\n");
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

        for (int i = 0; i < tmp.length; i++) {

            String line = tmp[i];
            String[] lineArray = line.split(":");

            if (lineArray[0].equals("id")) {
                uc.id = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("name")) {
                uc.name = lineArray[1];
            } else if (lineArray[0].equals("x")) {
                uc.x = Integer.parseInt(lineArray[1])*Utils.TILEWIDTH;
            } else if (lineArray[0].equals("y")) {
                uc.y = Integer.parseInt(lineArray[1])*Utils.TILEHEIGHT;
            } else if (lineArray[0].equals("level")) {
                uc.level = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("exp")) {
                uc.experience = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("health")) {
                uc.health = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("currenthealth")) {
                uc.current_health = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("mana")) {
                uc.mana = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("currentmana")) {
                uc.current_mana = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("money")) {
                uc.money = Integer.parseInt(lineArray[1]);
            } else if (lineArray[0].equals("bag_size")) {
                uc.bag_size = Integer.parseInt(lineArray[1]);
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
        uc.id = -1;
        uc.x = 640;
        uc.y = 640;
        uc.health = CharacterFactory.getHealthForLevel(1);
        uc.current_health = CharacterFactory.getHealthForLevel(1);
        uc.mana = 100;
        uc.current_mana = 100;
        uc.money = 100;
        uc.level = 1;
        uc.experience = 0;
        uc.name = "ADMIN";
        uc.bag_size = 10;
        uc.bag = new HashMap();
        uc.bag.put(1,1);
        uc.bag.put(2,1);
        uc.bag.put(3,1);
        uc.bag.put(4,1);
        uc.bag.put(5,1);
        uc.bag.put(6,1);
        uc.bag.put(7,1);
        uc.bag.put(8,1);
        uc.bag.put(9,1);
        uc.bag.put(10,1);
        uc.bag.put(11,1);
        uc.bag.put(12,1);

        uc.equippedItems = new HashMap();
        return uc;
    }

    public HashMap<Integer, Integer> getBag() {
        return bag;
    }

    /* put item in bag */
    public boolean putInBag(int i) {
        if(bag.size() > bag_size) {
            return false;
        }
        if(bag.containsKey(i)) {
            bag.put(i, bag.get(i)+1);
        }else {
            bag.put(i, 1);
        }
        return true;
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

    public void uneauipItemBySlotId(int slotId) {
        equippedItems.remove(slotId);
    }

}
