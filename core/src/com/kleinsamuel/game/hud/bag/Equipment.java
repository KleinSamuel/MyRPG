package com.kleinsamuel.game.hud.bag;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by sam on 31.05.17.
 */

public class Equipment {

    public static final int HELMET_SLOT_ID = 1;
    public static final int TORSO_SLOT_ID = 2;
    public static final int LEFT_ARM_SLOT_ID = 3;
    public static final int RIGHT_ARM_SLOT_ID = 4;
    public static final int LEFT_HAND_SLOT_ID = 5;
    public static final int RIGHT_HAND_SLOT_ID = 6;
    public static final int LEGS_SLOT_ID = 7;
    public static final int LEFT_FOOT_SLOT_ID = 8;
    public static final int RIGHT_FOOT_SLOT_ID = 9;
    public static final int LEFT_RING_SLOT_ID = 10;
    public static final int RIGHT_RING_SLOT_ID = 11;
    public static final int NECKLACE_SLOT_ID = 12;

    public static HashSet<Integer> itemsForHelmet;
    public static HashSet<Integer> itemsForTorso;
    public static HashSet<Integer> itemsForLeftArm;
    public static HashSet<Integer> itemsForRightArm;
    public static HashSet<Integer> itemsForLeftHand;
    public static HashSet<Integer> itemsForRightHand;
    public static HashSet<Integer> itemsForLegs;
    public static HashSet<Integer> itemsForLeftFoot;
    public static HashSet<Integer> itemsForRightFoot;
    public static HashSet<Integer> itemsForLeftRing;
    public static HashSet<Integer> itemsForRightRing;
    public static HashSet<Integer> itemsForNecklace;

    static {
        itemsForHelmet = new HashSet<Integer>(Arrays.asList(4,12));
        itemsForTorso = new HashSet<Integer>(Arrays.asList(5));
        itemsForLeftHand = new HashSet<Integer>(Arrays.asList(7));
        itemsForRightHand = new HashSet<Integer>(Arrays.asList(8));
        itemsForLegs = new HashSet<Integer>(Arrays.asList(6));
        itemsForLeftFoot = new HashSet<Integer>(Arrays.asList(9));
        itemsForRightFoot = new HashSet<Integer>(Arrays.asList(10));
        itemsForLeftRing = new HashSet<Integer>(Arrays.asList(-1));
        itemsForRightRing = new HashSet<Integer>(Arrays.asList(-1));
        itemsForNecklace = new HashSet<Integer>(Arrays.asList(11));
    }

    public static HashSet<Integer> getItemSetForEquipmentSlot(int slotId){
        switch (slotId) {
            case 1:
                return itemsForHelmet;
            case 2:
                return itemsForTorso;
            case 3:
                return itemsForLeftHand;
            case 4:
                return itemsForRightHand;
            case 5:
                return itemsForLegs;
            case 6:
                return itemsForLeftFoot;
            case 7:
                return itemsForRightFoot;
            case 8:
                return itemsForLeftRing;
            case 9:
                return itemsForRightRing;
            case 10:
                return itemsForNecklace;
        }
        return null;
    }

    /**
     * Get int id of item equipment slot.
     * Return -1 if item is not equippable.
     *
     * @param itemId
     * @return
     */
    public static int getEquipmentSlotByItemId(int itemId) {
        for (int i = 1; i <= 10; i++) {
            if(getItemSetForEquipmentSlot(i).contains(itemId)) {
                return i;
            }
        }
        return -1;
    }

}
