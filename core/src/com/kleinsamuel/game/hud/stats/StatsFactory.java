package com.kleinsamuel.game.hud.stats;

/**
 * Created by sam on 17.06.17.
 */

public class StatsFactory {

    public static String getHeadline(int identifier){
        switch (identifier){
            case 0:
                return "HEALTH POINTS";
            case 1:
                return "MANA POINTS";
            case 2:
                return "EXPERIENCE";
            case 3:
                return "ATTACK DAMAGE";
            case 4:
                return "ATTACK SPEED";
            case 5:
                return "DEFENSE";
            case 6:
                return "AGILITY";
            case 7:
                return "CRITICAL DAMAGE";
            case 8:
                return "CRITICAL CHANCE";
        }
        return "not available";
    }

    public static String getDescription(int identifier){
        switch (identifier){
            case 0:
                return "How much damage you can take. Influenced by class and race. Increased by level and equipment.";
            case 1:
                return "How many maximum mana point you can have. Influenced by race and class. Increased by level.";
            case 2:
                return "How much experience you gained.";
            case 3:
                return "How much damage you deal to your opponent with a single hit. Increased by weapon.";
            case 4:
                return "How fast you can hit. Increased by equipment.";
            case 5:
                return "How much damage you can avert. Increased by equipment.";
            case 6:
                return "How fast you move. Influenced by race and class. Increased by equipment";
            case 7:
                return "How much damage a critical hit deals to your opponent.";
            case 8:
                return "The chance to land a critical hit.";
        }
        return "not available";
    }

}
