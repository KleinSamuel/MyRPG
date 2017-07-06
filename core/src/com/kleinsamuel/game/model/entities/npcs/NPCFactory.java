package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.Utils;

import java.util.HashMap;

/**
 * Created by sam on 13.06.17.
 */

public class NPCFactory {

    public static HashMap<Integer, NPCEnum> npcs;

    static {
        npcs = new HashMap();

        /* ANIMALS */
        npcs.put(1, NPCEnum.BUG_SMALL_GREEN);
        npcs.put(2, NPCEnum.BUG_SMALL_PURPLE);
        npcs.put(3, NPCEnum.BUG_SMALL_RED);
        npcs.put(4, NPCEnum.BUG_BIG_GREEN);
        npcs.put(5, NPCEnum.BUG_BIG_PURPLE);
        npcs.put(6, NPCEnum.BUG_BIG_RED);

        npcs.put(7, NPCEnum.BUTTERFLY_SMAlL_BLACK);
        npcs.put(8, NPCEnum.BUTTERFLY_SMALL_RED);
        npcs.put(9, NPCEnum.BUTTERFLY_SMALL_WHITE);

        npcs.put(10, NPCEnum.CROW_SMALL_BLACK);
        npcs.put(11, NPCEnum.CROW_SMALL_BROWN);
        npcs.put(12, NPCEnum.CROW_SMALL_WHITE);

        npcs.put(13, NPCEnum.FROG_SMALL_GREEN);
        npcs.put(14, NPCEnum.FROG_SMALL_BLUE);
        npcs.put(15, NPCEnum.FROG_SMALL_BROWN);

        npcs.put(16, NPCEnum.RAT_SMALL_BLACK);
        npcs.put(17, NPCEnum.RAT_SMALL_BROWN);
        npcs.put(18, NPCEnum.RAT_SMALL_GREY);
        npcs.put(19, NPCEnum.RAT_SMALL_WHITE);
        npcs.put(20, NPCEnum.RAT_BIG_BLACK);
        npcs.put(21, NPCEnum.RAT_BIG_BROWN);
        npcs.put(22, NPCEnum.RAT_BIG_GREY);
        npcs.put(23, NPCEnum.RAT_BIG_WHITE);

        npcs.put(24, NPCEnum.SNAKE_SMALL_GREEN);
        npcs.put(25, NPCEnum.SNAKE_BIG_GREEN);

        npcs.put(26, NPCEnum.SPIDER_SMALL_BLACK);
        npcs.put(27, NPCEnum.SPIDER_SMALL_BROWN);
        npcs.put(28, NPCEnum.SPIDER_SMALL_PURPLE);
        npcs.put(29, NPCEnum.SPIDER_BIG_BLACK);
        npcs.put(30, NPCEnum.SPIDER_BIG_BROWN);
        npcs.put(31, NPCEnum.SPIDER_BIG_PURPLE);

        npcs.put(32, NPCEnum.WOLF_SMALL_BLACK);
        npcs.put(33, NPCEnum.WOLF_SMALL_BROWN);
        npcs.put(34, NPCEnum.WOLF_SMALL_DARKBLACK);
        npcs.put(35, NPCEnum.WOLF_SMALL_WHITE);

        npcs.put(36, NPCEnum.WORM_SMALL_BROWN);
        npcs.put(37, NPCEnum.WORM_BIG_BROWN);
    }

    public static String getResourceStringForId(int id){
        switch (id){
            case 1:
                return Assets.bug_small_green;
            case 2:
                return Assets.bug_small_purple;
            case 3:
                return Assets.bug_small_red;
            case 4:
                return Assets.bug_big_green;
            case 5:
                return Assets.bug_big_purple;
            case 6:
                return Assets.bug_big_red;
            case 7:
                return Assets.butterfly_small_black;
            case 8:
                return Assets.butterfly_small_red;
            case 9:
                return Assets.butterfly_small_white;
            case 10:
                return Assets.crow_small_black;
            case 11:
                return Assets.crow_small_brown;
            case 12:
                return Assets.crow_small_white;
            case 13:
                return Assets.frog_small_green;
            case 14:
                return Assets.frog_small_blue;
            case 15:
                return Assets.frog_small_brown;
            case 16:
                return Assets.rat_small_black;
            case 17:
                return Assets.rat_small_brown;
            case 18:
                return Assets.rat_small_grey;
            case 19:
                return Assets.rat_small_white;
            case 20:
                return Assets.rat_big_black;
            case 21:
                return Assets.rat_big_brown;
            case 22:
                return Assets.rat_big_grey;
            case 23:
                return Assets.rat_big_white;
            case 24:
                return Assets.snake_small_green;
            case 25:
                return Assets.snake_big_green;
            case 26:
                return Assets.spider_small_black;
            case 27:
                return Assets.spider_small_brown;
            case 28:
                return Assets.spider_small_purple;
            case 29:
                return Assets.spider_big_black;
            case 30:
                return Assets.spider_big_brown;
            case 31:
                return Assets.spider_big_purple;
            case 32:
                return Assets.wolf_small_black;
            case 33:
                return Assets.wolf_small_brown;
            case 34:
                return Assets.wolf_small_darkblack;
            case 35:
                return Assets.wolf_small_white;
            case 36:
                return Assets.worm_small_brown;
            case 37:
                return Assets.worm_big_brown;

        }

        return null;
    }

    public static Vector3 getWidthAndHeight(int npc_key){
        Vector3 wAh = new Vector3(Utils.TILEWIDTH, Utils.TILEHEIGHT, 0);
        switch (npc_key){
            case 1:
                wAh.set(Utils.TILEWIDTH*1.0f, Utils.TILEHEIGHT*1.0f, 0);
                break;
            case 2:
                wAh.set(Utils.TILEWIDTH*1.0f, Utils.TILEHEIGHT*1.0f, 0);
                break;
            case 3:
                wAh.set(Utils.TILEWIDTH*1.0f, Utils.TILEHEIGHT*1.0f, 0);
                break;
            case 4:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 5:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 6:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 7:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 8:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 9:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 10:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 11:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 12:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 13:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 14:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 15:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 16:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 17:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 18:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 19:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 20:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 21:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 22:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 23:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 24:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 25:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 26:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 27:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 28:
                wAh.set(Utils.TILEWIDTH*1.2f, Utils.TILEHEIGHT*1.2f, 0);
                break;
            case 29:
                wAh.set(Utils.TILEWIDTH*1.3f, Utils.TILEHEIGHT*1.3f, 0);
                break;
            case 30:
                wAh.set(Utils.TILEWIDTH*1.3f, Utils.TILEHEIGHT*1.3f, 0);
                break;
            case 31:
                wAh.set(Utils.TILEWIDTH*1.3f, Utils.TILEHEIGHT*1.3f, 0);
                break;
            case 32:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 33:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 34:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 35:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 37:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
            case 38:
                wAh.set(Utils.TILEWIDTH*1.5f, Utils.TILEHEIGHT*1.5f, 0);
                break;
        }
        return wAh;
    }

}
