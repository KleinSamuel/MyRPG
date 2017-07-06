package com.kleinsamuel.game.model.entities;

import com.kleinsamuel.game.model.Assets;

/**
 * Created by sam on 20.06.17.
 */

public class PlayerFactory {

    public static String getResourceStringForPlayerSprite(String playerspriteIdentifier){
        if(playerspriteIdentifier.equals("dwarf")){
            return Assets.dwarf_basic;
        }else if(playerspriteIdentifier.equals("elb")){
            return Assets.elb_basic;
        }else if(playerspriteIdentifier.equals("halfling")){
            return Assets.halfling_basic;
        }else if(playerspriteIdentifier.equals("human")){
            return Assets.human_basic;
        }else if(playerspriteIdentifier.equals("murony")){
            return Assets.murony_basic;
        }else if(playerspriteIdentifier.equals("orc")){
            return Assets.orc_basic;
        }else if(playerspriteIdentifier.equals("wiedergaenger")){
            return Assets.wiedergaenger_basic;
        }
        return "";
    }

    public static String getRaceName(int identifier){
        switch (identifier){
            case 0:
                return "human";
            case 1:
                return "halfling";
            case 2:
                return "dwarf";
            case 3:
                return "elb";
            case 4:
                return "orc";
            case 5:
                return "wiedergaenger";
            case 6:
                return "murony";
        }
        return "";
    }

}
