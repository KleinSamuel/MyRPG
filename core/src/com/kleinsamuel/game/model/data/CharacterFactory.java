package com.kleinsamuel.game.model.data;

/**
 * Created by sam on 29.05.17.
 */

public class CharacterFactory {

    private static int computeFormula(int x) {
        return (int)(3*(Math.pow(x*1.0, 3)))/(4);
    }

    public static int getHealthForLevelLinear(int level, double mutliplier) {
        return 10+(level-1)*(int)(7*mutliplier);
    }

    public static int getManaForLevelLinear(int level, double mutliplier) {
        return 10+(level-1)*(int)(7*mutliplier);
    }

    public static int getNeededXpForLevel(int level) {
        return computeFormula(level+3);
    }

    public static int getGainedXpForLevel(int level) {
        return getHealthForLevelLinear(level, 1.0)*(level)/4;
    }

    public static int getDamageForLevel(int level) {
        return getHealthForLevelLinear(level, 1.0)/10;
    }

    public static int getDefenseForLevel(int level) {
        return getDamageForLevel(level)/5;
    }

}
