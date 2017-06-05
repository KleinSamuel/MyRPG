package com.kleinsamuel.game.model.data;

/**
 * Created by sam on 29.05.17.
 */

public class CharacterFactory {

    private static int computeFormula(int x) {
        return (int)(5*(Math.pow(x*1.0, 3)))/(4);
    }

    public static int getNeededXpForLevel(int level) {
        if(level <= 3){
            return level * 20;
        }
        return computeFormula(level);
    }

    public static int getGainedXpForLevel(int level) {
        if(level < 30){
            return 1;
        }
        return getNeededXpForLevel(level)/30;
    }

    public static int getHealthForLevel(int level) {

        if(level <= 3){
            return level * 100;
        }
        return computeFormula(level)/2;
    }
}
