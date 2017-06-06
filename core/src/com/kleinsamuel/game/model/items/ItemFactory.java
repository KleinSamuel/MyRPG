package com.kleinsamuel.game.model.items;

import com.kleinsamuel.game.model.Assets;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by sam on 31.05.17.
 */

public class ItemFactory {

    public static final int ITEM_WIDTH = 32;
    public static final int ITEM_HEIGHT = 32;
    public static HashMap<Integer, ItemEnum> items;

    static {
        items = new HashMap();

        items.put(1, ItemEnum.POTION_HEALTH_1);
        items.put(2, ItemEnum.POTION_MANA_1);
        items.put(3, ItemEnum.ARROWS_1);
        items.put(4, ItemEnum.HELMET_3);
        items.put(5, ItemEnum.TORSO_1);
        items.put(6, ItemEnum.LEGS_1);
        items.put(7, ItemEnum.HAND_LEFT_1);
        items.put(8, ItemEnum.HAND_RIGHT_1);
        items.put(9, ItemEnum.FOOT_LEFT_1);
        items.put(10, ItemEnum.FOOT_RIGHT_1);
        items.put(11, ItemEnum.NECKLACE_1);
        items.put(12, ItemEnum.HELMET_2);
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

            ItemData itemData = new ItemData(id, item_key, x, y, amount);
            set.add(itemData);
        }

        return set;
    }

    public static String getResourceStringForItemId(int id) {

        switch (id) {
            case 1:
                return Assets.potion_red_1;
            case 2:
                return Assets.potion_blue_1;
            case 3:
                return Assets.arrows_1;
            case 4:
                return Assets.helmet_3;
            case 5:
                return Assets.torso_1;
            case 6:
                return Assets.legs_1;
            case 7:
                return Assets.hand_left_1;
            case 8:
                return Assets.hand_right_1;
            case 9:
                return Assets.foot_left_1;
            case 10:
                return Assets.foot_right_1;
            case 11:
                return Assets.necklace_1;
            case 12:
                return Assets.helmet_2;
        }
        return null;
    }

    public static String getCommonnessByProbabilty(double itemId) {
        if(itemId >= 0.9) {
            return "common";
        }
        return "unknown";
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
