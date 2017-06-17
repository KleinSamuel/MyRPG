package com.kleinsamuel.game.model.maps.interactive;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by sam on 16.06.17.
 */

public class MerchantFactory {

    public static TreeMap<Integer, Integer> getConsumablesForIdentifier(int merchantIdentifier) {
        TreeMap<Integer, Integer> outputMap = new TreeMap<Integer, Integer>();

        switch (merchantIdentifier){
            case 0:
                outputMap.put(15, 100);
                outputMap.put(16, 101);
                outputMap.put(17, 102);
                outputMap.put(18, 103);
                outputMap.put(19, 104);
                outputMap.put(20, 105);
                outputMap.put(21, 106);
                break;
        }

        return outputMap;
    }

    public static TreeMap<Integer, Integer> getEquipmentForIdentifier(int merchantIdentifier) {
        TreeMap<Integer, Integer> outputMap = new TreeMap<Integer, Integer>();

        switch (merchantIdentifier){
            case 0:
                outputMap.put(15, 101);
                outputMap.put(16, 102);
                outputMap.put(17, 103);
                outputMap.put(18, 104);
                outputMap.put(19, 105);
                outputMap.put(20, 106);
                outputMap.put(21, 107);
                break;
        }

        return outputMap;
    }

    public static TreeMap<Integer, Integer> getWeaponsForIdentifier(int merchantIdentifier) {
        TreeMap<Integer, Integer> outputMap = new TreeMap<Integer, Integer>();

        switch (merchantIdentifier){
            case 0:
                outputMap.put(15, 101);
                outputMap.put(16, 102);
                outputMap.put(17, 103);
                outputMap.put(18, 104);
                outputMap.put(19, 105);
                outputMap.put(20, 106);
                outputMap.put(21, 107);
                break;
        }

        return outputMap;
    }
}
