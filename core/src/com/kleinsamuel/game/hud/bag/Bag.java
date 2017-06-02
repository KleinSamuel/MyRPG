package com.kleinsamuel.game.hud.bag;

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

import static com.kleinsamuel.game.util.Utils.FACTOR;

/**
 * Created by sam on 31.05.17.
 */

public class Bag {

    public static boolean SHOW_BAG = false;
    public static boolean SHOW_ITEM_INFO = false;

    private PlayScreen playScreen;

    private final int bag_size_x = 6;
    private final int bag_size_y = 5;
    private final float bag_container_width = 66 * FACTOR;
    private final float bag_container_height = 66 * FACTOR;
    private final float bag_padding_x = 10 * FACTOR;
    private final float bag_padding_y = 10 * FACTOR;
    private final float bag_margin_x = 19 * FACTOR;
    private final float bag_margin_y = 19 * FACTOR;

    /* coordinates of first bag entry */
    public final float BAG_POS_X = 440.0f * FACTOR;
    public final float BAG_POS_Y = 394.0f * FACTOR;

    /* coordinates for close button of bag */
    public final float CLOSE_X_1 = 909.0f * FACTOR;
    public final float CLOSE_Y_1 = 492.0f * FACTOR;
    public final float CLOSE_X_2 = 941.0f * FACTOR;
    public final float CLOSE_Y_2 = 523.0f * FACTOR;

    /* coordinates for stat and encyclopedia buttons */
    public final float STAT_X_1 = 32.0f * FACTOR;
    public final float STAT_Y_1 = 492.0f * FACTOR;
    public final float STAT_X_2 = 108.0f * FACTOR;
    public final float STAT_Y_2 = 522.0f * FACTOR;
    public final float ENCY_X_1 = 161.0f * FACTOR;
    public final float ENCY_Y_1 = 492.0f * FACTOR;
    public final float ENCY_X_2 = 235.0f * FACTOR;
    public final float ENCY_Y_2 = 522.0f * FACTOR;


    public final float ITEM_PADDING_X = bag_padding_x;
    public final float ITEM_PADDING_Y = bag_padding_y;
    public final float ITEM_HEAD_X = 177.0f * FACTOR;
    public final float ITEM_HEAD_Y = 387.0f * FACTOR;
    public final float ITEM_TORSO_X = 177.0f * FACTOR;
    public final float ITEM_TORSO_Y = 297.0f * FACTOR;
    public final float ITEM_HAND_LEFT_X = 80.0f * FACTOR;
    public final float ITEM_HAND_LEFT_Y = 273.0f * FACTOR;
    public final float ITEM_HAND_RIGHT_X = 273.0f * FACTOR;
    public final float ITEM_HAND_RIGHT_Y = 273.0f * FACTOR;
    public final float ITEM_LEGS_X = 177.0f * FACTOR;
    public final float ITEM_LEGS_Y = 210.0f * FACTOR;
    public final float ITEM_FOOT_LEFT_X = 128.0f * FACTOR;
    public final float ITEM_FOOT_LEFT_Y = 111.0f * FACTOR;
    public final float ITEM_FOOT_RIGHT_X = 226.0f * FACTOR;
    public final float ITEM_FOOT_RIGHT_Y = 111.0f * FACTOR;
    public final float ITEM_RING_LEFT_X = 28.0f * FACTOR;
    public final float ITEM_RING_LEFT_Y = 291.0f * FACTOR;
    public final float ITEM_RING_RIGHT_X = 359.0f * FACTOR;
    public final float ITEM_RING_RIGHT_Y = 291.0f * FACTOR;
    public final float ITEM_NECKLACE_X = 265.0f * FACTOR;
    public final float ITEM_NECKLACE_Y = 362.0f * FACTOR;

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

        float currentX = BAG_POS_X;
        float currentY = BAG_POS_Y;

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

        if(SHOW_ITEM_INFO) {
            itemInfoWindow.handleClick(screenX, screenY);
            return;
        }

        if (clickOnExit(screenX, screenY)) {
            SHOW_ITEM_INFO = false;
            SHOW_BAG = false;
            return;
        }

        if(clickOnStat(screenX, screenY)){
            return;
        }

        if(clickOnEncyclopedia(screenX, screenY)){
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

    public boolean clickOnStat(int screenX, int screenY) {
        if(screenX >= STAT_X_1 && screenX <= STAT_X_2 && screenY >= STAT_Y_1 && screenY <= STAT_Y_2) {
            DebugMessageFactory.printInfoMessage("CLICKED ON STAT");
            return true;
        }
        return false;
    }

    public boolean clickOnEncyclopedia(int screenX, int screenY) {
        if(screenX >= ENCY_X_1 && screenX <= ENCY_X_2 && screenY >= ENCY_Y_1 && screenY <= ENCY_Y_2) {
            DebugMessageFactory.printInfoMessage("CLICKED ON ENCYCLOPEDIA");
            return true;
        }
        return false;
    }

    private Vector3 getCoordinatesForSlotId(int slotId) {
        float x = -1;
        float y = -1;
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
                x = ITEM_HAND_LEFT_X;
                y = ITEM_HAND_LEFT_Y;
                break;
            case 4:
                x = ITEM_HAND_RIGHT_X;
                y = ITEM_HAND_RIGHT_Y;
                break;
            case 5:
                x = ITEM_LEGS_X;
                y = ITEM_LEGS_Y;
                break;
            case 6:
                x = ITEM_FOOT_LEFT_X;
                y = ITEM_FOOT_LEFT_Y;
                break;
            case 7:
                x = ITEM_FOOT_RIGHT_X;
                y = ITEM_FOOT_RIGHT_Y;
                break;
            case 8:
                x = ITEM_RING_LEFT_X;
                y = ITEM_RING_LEFT_Y;
                break;
            case 9:
                x = ITEM_RING_RIGHT_X;
                y = ITEM_RING_RIGHT_Y;
                break;
            case 10:
                x = ITEM_NECKLACE_X;
                y = ITEM_NECKLACE_Y;
                break;
            default:
                return null;
        }
        return new Vector3(x, y, 0);
    }

    public Vector3 getWidthAndHeightOfSlot(int slotId) {
        if(slotId < 1 || slotId > 10) {
            return null;
        }
        if(slotId == 8 || slotId == 9) {
            return new Vector3(32*FACTOR, 32*FACTOR, 0);
        }else if(slotId == 10 || slotId == 11) {
            return new Vector3(53*FACTOR, 53*FACTOR, 0);
        }else {
            return new Vector3(66*FACTOR, 66*FACTOR, 0);
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
                    SHOW_ITEM_INFO = true;
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
        for (int i = 1; i <= 10; i++) {
            Vector3 p = getCoordinatesForSlotId(i);
            Vector3 dims = getWidthAndHeightOfSlot(i);
            if(screenX >= p.x && screenX <= p.x+dims.x && screenY >= p.y && screenY <= p.y+dims.y) {
                if(playScreen.player.content.getEquippedItems().containsKey(i)) {
                    SHOW_ITEM_INFO = true;
                    itemInfoWindow = new ItemInfoWindow(playScreen, playScreen.player.content.getEquippedItems().get(i), false);
                }
                return true;
            }

        }
        return false;
    }

    public void render(SpriteBatch batch){

        batch.draw(Assets.manager.get(Assets.inventory_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);

        assignedMap = new TreeMap();

        int currentBagPosition = 0;
        for(Map.Entry<Integer, Integer> entry : playScreen.player.content.getBag().entrySet()) {

            int isEquipped = playScreen.player.content.checkIfEquipped(entry.getKey());

            if(isEquipped == -1) {
                Vector3 p = bag_content_positions.get(currentBagPosition);
                assignedMap.put(currentBagPosition, entry.getKey());
                batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), p.x+bag_padding_x, p.y+bag_padding_y, bag_container_width-(2*bag_padding_x), bag_container_height-(2*bag_padding_y));
                currentBagPosition++;
            }else {

                Vector3 p = getCoordinatesForSlotId(isEquipped);
                if(p != null) {
                    batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(entry.getKey()), Texture.class), p.x+(ITEM_PADDING_X/2), p.y+(ITEM_PADDING_Y/2), getWidthAndHeightOfSlot(isEquipped).x-ITEM_PADDING_X, getWidthAndHeightOfSlot(isEquipped).y-ITEM_PADDING_Y);
                }
            }
        }

        if(SHOW_ITEM_INFO) {
            itemInfoWindow.render(batch);
        }
    }

}
