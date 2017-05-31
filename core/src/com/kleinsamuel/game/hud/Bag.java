package com.kleinsamuel.game.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sam on 31.05.17.
 */

public class Bag {

    public static boolean DRAW_BAG = false;
    public static boolean DRAW_ITEM_INFO = false;

    private PlayScreen playScreen;

    private final int bag_size_x = 7;
    private final int bag_size_y = 5;
    private final int bag_container_width = 64;
    private final int bag_container_height = 64;
    private final int bag_padding_x = 8;
    private final int bag_padding_y = 8;
    private final int bag_margin_x = 8;
    private final int bag_margin_y = 8;

    public final int BAG_POS_X = 453;
    public final int BAG_POS_Y = 405-64;

    public final int CLOSE_X_1 = 902;
    public final int CLOSE_Y_1 = 498;
    public final int CLOSE_X_2 = 940;
    public final int CLOSE_Y_2 = 532;

    public final int ITEM_PADDING_X = 6;
    public final int ITEM_PADDING_Y = 6;
    public final int ITEM_HEAD_X = 190;
    public final int ITEM_HEAD_Y = 379;
    public final int ITEM_TORSO_X = 190;
    public final int ITEM_TORSO_Y = 292;
    public final int ITEM_ARM_LEFT_X = 106;
    public final int ITEM_ARM_LEFT_Y = 292;
    public final int ITEM_ARM_RIGHT_X = 275;
    public final int ITEM_ARM_RIGHT_Y = 292;
    public final int ITEM_HAND_LEFT_X = 21;
    public final int ITEM_HAND_LEFT_Y = 274;
    public final int ITEM_HAND_RIGHT_X = 359;
    public final int ITEM_HAND_RIGHT_Y = 274;
    public final int ITEM_LEGS_X = 190;
    public final int ITEM_LEGS_Y = 206;
    public final int ITEM_FOOT_LEFT_X = 148;
    public final int ITEM_FOOT_LEFT_Y = 120;
    public final int ITEM_FOOT_RIGHT_X = 233;
    public final int ITEM_FOOT_RIGHT_Y = 120;
    public final int ITEM_RING_LEFT_X = 35;
    public final int ITEM_RING_LEFT_Y = 360;
    public final int ITEM_RING_RIGHT_X = 373;
    public final int ITEM_RING_RIGHT_Y = 360;
    public final int ITEM_NECKLACE_X = 322;
    public final int ITEM_NECKLACE_Y = 421;

    public ArrayList<Vector3> bag_content_positions;
    private TreeMap<Integer, Integer> assignedMap;

    public ItemInfoWindow itemInfoWindow;

    public Bag(PlayScreen playScreen){

        this.playScreen = playScreen;

        bag_content_positions = new ArrayList<Vector3>();
        assignedMap = new TreeMap<Integer, Integer>();

        initPositions();
    }

    private void initPositions(){

        int currentX = BAG_POS_X;
        int currentY = BAG_POS_Y;

        for (int i = 0; i < bag_size_y; i++) {
            for (int j = 0; j < bag_size_x; j++) {
                bag_content_positions.add(new Vector3(currentX, currentY, 0 ));
                currentX += bag_margin_x+bag_container_width;
            }
            currentY -= bag_margin_y+bag_container_height;
            currentX = BAG_POS_X;
        }
    }

    public void handleClick(int screenX, int screenY) {

        DebugMessageFactory.printInfoMessage("HANDLE CLICK IN BAG: "+screenX+"-"+screenY);

        if(DRAW_ITEM_INFO) {
            itemInfoWindow.handleClick(screenX, screenY);
            return;
        }

        if (clickOnExit(screenX, screenY)) {
            DRAW_ITEM_INFO = false;
            DRAW_BAG = false;
            return;
        }

        if(getItemSlot(screenX, screenY)) {
            return;
        }

        if(getEquipmentSlot(screenX, screenY)) {
            return;
        }
    }

    public boolean clickOnExit(int screenX, int screenY) {
        if(screenX >= CLOSE_X_1 && screenX <= CLOSE_X_2 && screenY >= CLOSE_Y_1 && screenY <= CLOSE_Y_2) {
            return true;
        }
        return false;
    }

    private Vector3 getCoordinatesForSlotId(int slotId) {
        int x = -1;
        int y = -1;
        switch (slotId) {
            case 1:
                x = ITEM_HEAD_X;
                y = ITEM_HEAD_Y;
                break;
            case 2:
                x = ITEM_TORSO_X;
                y = ITEM_TORSO_Y;
                break;
            case 3:
                x = ITEM_ARM_LEFT_X;
                y = ITEM_ARM_LEFT_Y;
                break;
            case 4:
                x = ITEM_ARM_RIGHT_X;
                y = ITEM_ARM_RIGHT_Y;
                break;
            case 5:
                x = ITEM_HAND_LEFT_X;
                y = ITEM_HAND_LEFT_Y;
                break;
            case 6:
                x = ITEM_HAND_RIGHT_X;
                y = ITEM_HAND_RIGHT_Y;
                break;
            case 7:
                x = ITEM_LEGS_X;
                y = ITEM_LEGS_Y;
                break;
            case 8:
                x = ITEM_FOOT_LEFT_X;
                y = ITEM_FOOT_LEFT_Y;
                break;
            case 9:
                x = ITEM_FOOT_RIGHT_X;
                y = ITEM_FOOT_RIGHT_Y;
                break;
            case 10:
                x = ITEM_RING_LEFT_X;
                y = ITEM_RING_LEFT_Y;
                break;
            case 11:
                x = ITEM_RING_RIGHT_X;
                y = ITEM_RING_RIGHT_Y;
                break;
            case 12:
                x = ITEM_NECKLACE_X;
                y = ITEM_NECKLACE_Y;
                break;
            default:
                return null;
        }
        return new Vector3(x, y, 0);
    }

    public Vector3 getWidthAndHeightOfSlot(int slotId) {
        if(slotId < 1 || slotId > 12) {
            return null;
        }
        if(slotId == 10 || slotId == 11 || slotId == 12) {
            return new Vector3(34, 34, 0);
        }else {
            return new Vector3(61, 61, 0);
        }
    }

    public int getAssignedKey(int pos) {
        if(assignedMap.containsKey(pos)) {
            return assignedMap.get(pos);
        }else {
            return -1;
        }
    }

    public boolean getItemSlot(int screenX, int screenY) {
        int position = 0;
        for(Vector3 p : bag_content_positions) {
            if(screenX >= p.x && screenX <= p.x+bag_container_width && screenY >= p.y && screenY <= p.y+bag_container_height) {
                int assigned = getAssignedKey(position);
                if(assigned != -1) {
                    DRAW_ITEM_INFO = true;
                    itemInfoWindow = new ItemInfoWindow(playScreen, assigned, true);
                    return true;
                }
                break;
            }
            position++;
        }
        return false;
    }

    public boolean getEquipmentSlot(int screenX, int screenY) {
        for (int i = 1; i <= 12; i++) {
            Vector3 p = getCoordinatesForSlotId(i);
            Vector3 dims = getWidthAndHeightOfSlot(i);
            if(screenX >= p.x && screenX <= p.x+dims.x && screenY >= p.y && screenY <= p.y+dims.y) {
                if(playScreen.player.content.getEquippedItems().containsKey(i)) {
                    DRAW_ITEM_INFO = true;
                    itemInfoWindow = new ItemInfoWindow(playScreen, playScreen.player.content.getEquippedItems().get(i), false);
                }
                return true;
            }

        }
        return false;
    }

    public void render(SpriteBatch batch){

        batch.draw(Assets.manager.get(Assets.inventory_background, Texture.class), 0, 0);

        assignedMap = new TreeMap();

        int currentBagPosition = 0;
        for(Map.Entry<Integer, Integer> entry : playScreen.player.content.getBag().entrySet()) {

            int isEquipped = playScreen.player.content.checkIfEquipped(entry.getKey());

            if(isEquipped == -1) {
                Vector3 p = bag_content_positions.get(currentBagPosition);
                assignedMap.put(currentBagPosition, entry.getKey());
                batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), p.x+(bag_padding_x/2), p.y+(bag_padding_y/2), bag_container_width-bag_padding_x, bag_container_height-bag_padding_y);
                currentBagPosition++;
            }else {

                Vector3 p = getCoordinatesForSlotId(isEquipped);
                if(p != null) {
                    batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), p.x+(ITEM_PADDING_X/2), p.y+(ITEM_PADDING_Y/2), getWidthAndHeightOfSlot(isEquipped).x-ITEM_PADDING_X, getWidthAndHeightOfSlot(isEquipped).y-ITEM_PADDING_Y);
                }
            }
        }

        if(DRAW_ITEM_INFO) {
            itemInfoWindow.render(batch);
        }
    }

}
