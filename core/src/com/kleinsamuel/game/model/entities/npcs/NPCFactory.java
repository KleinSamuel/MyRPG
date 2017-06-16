package com.kleinsamuel.game.model.entities.npcs;

import com.kleinsamuel.game.model.Assets;

import java.util.HashMap;

/**
 * Created by sam on 13.06.17.
 */

public class NPCFactory {

    public static HashMap<Integer, NPCEnum> npcs;

    static {
        npcs = new HashMap();

        npcs.put(1, NPCEnum.MOTH_BLACK);
        npcs.put(2, NPCEnum.BUG_SMALL);
        npcs.put(3, NPCEnum.BIRD_CROW);
    }

    public static String getResourceStringForId(int id){
        switch (id){
            case 0:
                return Assets.moth_black;
            case 1:
                return Assets.bug_small;
            case 3:
                return Assets.bird_crow;
        }

        return null;
    }

}
