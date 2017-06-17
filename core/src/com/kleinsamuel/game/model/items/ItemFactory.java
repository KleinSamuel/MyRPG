package com.kleinsamuel.game.model.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by sam on 31.05.17.
 */

public class ItemFactory {

    public static final int ITEM_WIDTH = 32;
    public static final int ITEM_HEIGHT = 32;
    public static HashMap<Integer, ItemEnum> items;

    private static TreeSet<Integer> stackableSet;

    private static HashSet<Integer> consumables;
    private static HashSet<Integer> equipment;
    private static HashSet<Integer> weapons;

    static {
        items = new HashMap();

        items.put(1, ItemEnum.ARMOR_BLOOD_TORSO);
        items.put(2, ItemEnum.ARMOR_BLOOD_HELMET);
        items.put(3, ItemEnum.ARMOR_BLOOD_LEGS);
        items.put(4, ItemEnum.ARMOR_BLOOD_FOOT_LEFT);
        items.put(5, ItemEnum.ARMOR_BLOOD_FOOT_RIGHT);
        items.put(6, ItemEnum.ARMOR_BLOOD_HAND_LEFT);
        items.put(7, ItemEnum.ARMOR_BLOOD_HAND_RIGHT);

        items.put(8, ItemEnum.ARMOR_CHAIN_TORSO);
        items.put(9, ItemEnum.ARMOR_CHAIN_HELMET);
        items.put(10, ItemEnum.ARMOR_CHAIN_LEGS);
        items.put(11, ItemEnum.ARMOR_CHAIN_FOOT_LEFT);
        items.put(12, ItemEnum.ARMOR_CHAIN_FOOT_RIGHT);
        items.put(13, ItemEnum.ARMOR_CHAIN_HAND_LEFT);
        items.put(14, ItemEnum.ARMOR_CHAIN_HAND_RIGHT);

        items.put(15, ItemEnum.ARMOR_CLOTH_TORSO);
        items.put(16, ItemEnum.ARMOR_CLOTH_HELMET);
        items.put(17, ItemEnum.ARMOR_CLOTH_LEGS);
        items.put(18, ItemEnum.ARMOR_CLOTH_FOOT_LEFT);
        items.put(19, ItemEnum.ARMOR_CLOTH_FOOT_RIGHT);
        items.put(20, ItemEnum.ARMOR_CLOTH_HAND_LEFT);
        items.put(21, ItemEnum.ARMOR_CLOTH_HAND_RIGHT);

        items.put(22, ItemEnum.ARMOR_GOLD_TORSO);
        items.put(23, ItemEnum.ARMOR_GOLD_HELMET);
        items.put(24, ItemEnum.ARMOR_GOLD_LEGS);
        items.put(25, ItemEnum.ARMOR_GOLD_FOOT_LEFT);
        items.put(26, ItemEnum.ARMOR_GOLD_FOOT_RIGHT);
        items.put(27, ItemEnum.ARMOR_GOLD_HAND_LEFT);
        items.put(28, ItemEnum.ARMOR_GOLD_HAND_RIGHT);

        items.put(29, ItemEnum.ARMOR_LEATHER_TORSO);
        items.put(30, ItemEnum.ARMOR_LEATHER_HELMET);
        items.put(31, ItemEnum.ARMOR_LEATHER_LEGS);
        items.put(32, ItemEnum.ARMOR_LEATHER_FOOT_LEFT);
        items.put(33, ItemEnum.ARMOR_LEATHER_FOOT_RIGHT);
        items.put(34, ItemEnum.ARMOR_LEATHER_HAND_LEFT);
        items.put(35, ItemEnum.ARMOR_LEATHER_HAND_RIGHT);

        items.put(36, ItemEnum.ARMOR_MYSTIC_TORSO);
        items.put(37, ItemEnum.ARMOR_MYSTIC_HELMET);
        items.put(38, ItemEnum.ARMOR_MYSTIC_LEGS);
        items.put(39, ItemEnum.ARMOR_MYSTIC_FOOT_LEFT);
        items.put(40, ItemEnum.ARMOR_MYSTIC_FOOT_RIGHT);
        items.put(41, ItemEnum.ARMOR_MYSTIC_HAND_LEFT);
        items.put(42, ItemEnum.ARMOR_MYSTIC_HAND_RIGHT);

        items.put(43, ItemEnum.ARMOR_PLATE_TORSO);
        items.put(44, ItemEnum.ARMOR_PLATE_HELMET);
        items.put(45, ItemEnum.ARMOR_PLATE_LEGS);
        items.put(46, ItemEnum.ARMOR_PLATE_FOOT_LEFT);
        items.put(47, ItemEnum.ARMOR_PLATE_FOOT_RIGHT);
        items.put(48, ItemEnum.ARMOR_PLATE_HAND_LEFT);
        items.put(49, ItemEnum.ARMOR_PLATE_HAND_RIGHT);

        items.put(50, ItemEnum.ARMOR_ROYAL_TORSO);
        items.put(51, ItemEnum.ARMOR_ROYAL_HELMET);
        items.put(52, ItemEnum.ARMOR_ROYAL_LEGS);
        items.put(53, ItemEnum.ARMOR_ROYAL_FOOT_LEFT);
        items.put(54, ItemEnum.ARMOR_ROYAL_FOOT_RIGHT);
        items.put(55, ItemEnum.ARMOR_ROYAL_HAND_LEFT);
        items.put(56, ItemEnum.ARMOR_ROYAL_HAND_RIGHT);

        items.put(57, ItemEnum.ARMOR_STUDDED_TORSO);
        items.put(58, ItemEnum.ARMOR_STUDDED_HELMET);
        items.put(59, ItemEnum.ARMOR_STUDDED_LEGS);
        items.put(60, ItemEnum.ARMOR_STUDDED_FOOT_LEFT);
        items.put(61, ItemEnum.ARMOR_STUDDED_FOOT_RIGHT);
        items.put(62, ItemEnum.ARMOR_STUDDED_HAND_LEFT);
        items.put(63, ItemEnum.ARMOR_STUDDED_HAND_RIGHT);

        items.put(64, ItemEnum.ARMOR_WILD_TORSO);
        items.put(65, ItemEnum.ARMOR_WILD_HELMET);
        items.put(66, ItemEnum.ARMOR_WILD_LEGS);
        items.put(67, ItemEnum.ARMOR_WILD_FOOT_LEFT);
        items.put(68, ItemEnum.ARMOR_WILD_FOOT_RIGHT);
        items.put(69, ItemEnum.ARMOR_WILD_HAND_LEFT);
        items.put(70, ItemEnum.ARMOR_WILD_HAND_RIGHT);

        items.put(1000, ItemEnum.KEY_GRAY);
        items.put(1001, ItemEnum.POTION_HEALTH);

        stackableSet = new TreeSet<Integer>(Arrays.asList(1001));

        consumables = new HashSet<Integer>(Arrays.asList(1001));
        equipment = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70));
        weapons = new HashSet<Integer>(Arrays.asList(-1));
    }

    public static boolean isStackable(int item_key){
        return stackableSet.contains(item_key);
    }

    public static HashSet<ItemData> getItemDataFromString(String input) {
        HashSet<ItemData> set = new HashSet();

        String[] array = input.split(";");
        for (int i = 0; i < array.length; i++) {
            String[] data = array[i].substring(1, array[i].length()-1).split(",");
            int id = Integer.parseInt(data[0]);
            int item_key = Integer.parseInt(data[1]);
            int x = Integer.parseInt(data[2]);
            int y = Integer.parseInt(data[3]);
            int amount = Integer.parseInt(data[4]);

            ItemData itemData = new ItemData(id, item_key, x, y, amount, "");
            set.add(itemData);
        }

        return set;
    }

    public static boolean isConsumable(int item_id){
        return (item_id >= 1000 && item_id < 2000);
        //return consumables.contains(item_id);
    }

    public static boolean isEquipment(int item_id){
        return (item_id >= 1 && item_id < 1000);
        //return equipment.contains(item_id);
    }

    public static boolean isWeapon(int item_id){
        return (item_id >= 2000 && item_id < 3000);
        //return weapons.contains(item_id);
    }

    public static String getResourceStringForItemId(int id) {

        switch (id) {
            case 1:
                return Assets.armor_blood_torso;
            case 2:
                return Assets.armor_blood_helmet;
            case 3:
                return Assets.armor_blood_legs;
            case 4:
                return Assets.armor_blood_foot_left;
            case 5:
                return Assets.armor_blood_foot_right;
            case 6:
                return Assets.armor_blood_hand_left;
            case 7:
                return Assets.armor_blood_hand_right;

            case 8:
                return Assets.armor_chain_torso;
            case 9:
                return Assets.armor_chain_helmet;
            case 10:
                return Assets.armor_chain_legs;
            case 11:
                return Assets.armor_chain_foot_left;
            case 12:
                return Assets.armor_chain_foot_right;
            case 13:
                return Assets.armor_chain_hand_left;
            case 14:
                return Assets.armor_chain_hand_right;

            case 15:
                return Assets.armor_cloth_torso;
            case 16:
                return Assets.armor_cloth_helmet;
            case 17:
                return Assets.armor_cloth_legs;
            case 18:
                return Assets.armor_cloth_foot_left;
            case 19:
                return Assets.armor_cloth_foot_right;
            case 20:
                return Assets.armor_cloth_hand_left;
            case 21:
                return Assets.armor_cloth_hand_right;

            case 22:
                return Assets.armor_gold_torso;
            case 23:
                return Assets.armor_gold_helmet;
            case 24:
                return Assets.armor_gold_legs;
            case 25:
                return Assets.armor_gold_foot_left;
            case 26:
                return Assets.armor_gold_foot_right;
            case 27:
                return Assets.armor_gold_hand_left;
            case 28:
                return Assets.armor_gold_hand_right;

            case 29:
                return Assets.armor_leather_torso;
            case 30:
                return Assets.armor_leather_helmet;
            case 31:
                return Assets.armor_leather_legs;
            case 32:
                return Assets.armor_leather_foot_left;
            case 33:
                return Assets.armor_leather_foot_right;
            case 34:
                return Assets.armor_leather_hand_left;
            case 35:
                return Assets.armor_leather_hand_right;

            case 36:
                return Assets.armor_mystic_torso;
            case 37:
                return Assets.armor_mystic_helmet;
            case 38:
                return Assets.armor_mystic_legs;
            case 39:
                return Assets.armor_mystic_foot_left;
            case 40:
                return Assets.armor_mystic_foot_right;
            case 41:
                return Assets.armor_mystic_hand_left;
            case 42:
                return Assets.armor_mystic_hand_right;

            case 43:
                return Assets.armor_plate_torso;
            case 44:
                return Assets.armor_plate_helmet;
            case 45:
                return Assets.armor_plate_legs;
            case 46:
                return Assets.armor_plate_foot_left;
            case 47:
                return Assets.armor_plate_foot_right;
            case 48:
                return Assets.armor_plate_hand_left;
            case 49:
                return Assets.armor_plate_hand_right;

            case 50:
                return Assets.armor_royal_torso;
            case 51:
                return Assets.armor_royal_helmet;
            case 52:
                return Assets.armor_royal_legs;
            case 53:
                return Assets.armor_royal_foot_left;
            case 54:
                return Assets.armor_royal_foot_right;
            case 55:
                return Assets.armor_royal_hand_left;
            case 56:
                return Assets.armor_royal_hand_right;

            case 57:
                return Assets.armor_studded_torso;
            case 58:
                return Assets.armor_studded_helmet;
            case 59:
                return Assets.armor_studded_legs;
            case 60:
                return Assets.armor_studded_foot_left;
            case 61:
                return Assets.armor_studded_foot_right;
            case 62:
                return Assets.armor_studded_hand_left;
            case 63:
                return Assets.armor_studded_hand_right;

            case 64:
                return Assets.armor_wild_torso;
            case 65:
                return Assets.armor_wild_helmet;
            case 66:
                return Assets.armor_wild_legs;
            case 67:
                return Assets.armor_wild_foot_left;
            case 68:
                return Assets.armor_wild_foot_right;
            case 69:
                return Assets.armor_wild_hand_left;
            case 70:
                return Assets.armor_wild_hand_right;

            case 1000:
                return Assets.key_gray;
            case 1001:
                return Assets.potion_red;
        }
        return null;
    }

    public static Vector3 getWidthAndHeight(int item_key){
        Vector3 v = new Vector3(Utils.TILEWIDTH, Utils.TILEHEIGHT, 0);

        switch (item_key){
            case -1:
                v.x = 13;
                v.y = 10;
                break;
        }

        return v;
    }

    public static void useConsumable(int item_key, Player p){
        switch (item_key){

            /* health potion */
            case 1001:
                if(p.content.getBag().containsKey(item_key)) {
                    p.content.removeFromBag(item_key);
                    if (p.content.MAX_HEALTH - p.content.CURRENT_HEALTH >= 10) {
                        p.content.CURRENT_HEALTH += 10;
                    } else {
                        p.content.CURRENT_HEALTH = p.content.MAX_HEALTH;
                    }
                    p.playScreen.drink_potion.play();
                    DebugMessageFactory.printInfoMessage("PLAY DRINK SOUND!");
                }
                break;
        }
    }

    public static String getNameOfItemById(int itemId) {
        switch (itemId) {
            case 1:
                return "HEALTH POTION";
            case 2:
                return "MANA POTION";
            case 3:
                return "WOOD ARROWS";
            default:
                return "NOT AVAILABLE";
        }
    }

    public static String getDescriptionOfItemById(int itemId) {
        switch (itemId) {
            case 1:
                return "A delicious red potion which restores a given\n"
                        + "amount of MAX_HEALTH points of the lucky person\n"
                        + "to drink it.\n"
                        + "One of the most common items to be found.";
            case 2:
                return "A disgusting blue potion which smells like troll\n"
                        + "sweat. What does not kill you makes you stronger.\n"
                        + "One of the most common items to be found.";
            case 3:
                return "Short fragile wooden arrows. Probably crafted by\n"
                        + "the carpenters apprentice. Still does decent\n"
                        + "damage to eye balls when well targeted.\n"
                        + "One of the most common items to be found.";
            default:
                return "Description not available.";
        }
    }

}
