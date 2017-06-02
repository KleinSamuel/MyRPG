package com.kleinsamuel.game.hud.bag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.screens.PlayScreen;

/**
 * Created by sam on 31.05.17.
 */

public class ItemInfoWindow {

    private PlayScreen playScreen;
    private int itemId;
    private String itemName;
    private String description;

    /* size of item info window */
    private float ITEM_INFO_WIDTH = PlayScreen.V_WIDTH*0.6f;
    private float ITEM_INFO_HEIGHT = PlayScreen.V_HEIGHT*0.8f;

    /* size of item info window close button */
    private float ITEM_INFO_CLOSE_WIDTH = PlayScreen.V_WIDTH*0.05f;
    private float ITEM_INFO_CLOSE_HEIGHT = PlayScreen.V_WIDTH*0.05f;

    private float item_info_x = (PlayScreen.V_WIDTH/2)-(ITEM_INFO_WIDTH/2);
    private float item_info_y = (PlayScreen.V_HEIGHT/2)-(ITEM_INFO_HEIGHT/2);
    private float close_x = item_info_x+ITEM_INFO_WIDTH-ITEM_INFO_CLOSE_WIDTH;
    private float close_y = item_info_y+ITEM_INFO_HEIGHT-(ITEM_INFO_CLOSE_HEIGHT);

    private float IMAGE_PADDING = 0f;
    private float IMAGE_WIDTH = 10f;
    private float IMAGE_HEIGHT = 10f;
    private float IMAGE_X = item_info_x+IMAGE_PADDING;
    private float IMAGE_Y = item_info_y+ITEM_INFO_HEIGHT-IMAGE_HEIGHT-IMAGE_PADDING;

    //private Font nameFont = new Font("arial", Font.BOLD, 30);
    private float NAME_X = IMAGE_X+IMAGE_WIDTH+IMAGE_PADDING;
    private float NAME_Y = IMAGE_Y+IMAGE_PADDING;
    private float NAME_PADDING = 60;

    //private Font descFont = new Font("arial", Font.BOLD, 18);
    private float DESC_X = IMAGE_X;
    private float DESC_Y = IMAGE_Y+IMAGE_HEIGHT+NAME_PADDING;
    private float DESC_PADDING = 30;

    private float USE_BUTTON_WIDTH = PlayScreen.V_WIDTH*0.1f;
    private float USE_BUTTON_HEIGHT = PlayScreen.V_WIDTH*0.05f;
    private float USE_BUTTON_X = (PlayScreen.V_WIDTH/2)-(USE_BUTTON_WIDTH/2);
    private float USE_BUTTON_Y = item_info_y+(USE_BUTTON_HEIGHT);

    private boolean use_button_pressed = false;
    private String resourceStringPressed;
    private String resourceStringUnpressed;
    private boolean equip;

    public ItemInfoWindow(PlayScreen playScreen, int itemId, boolean equip) {
        this.itemId = itemId;
        this.equip = equip;
        this.playScreen = playScreen;

        int equipmentSlot = Equipment.getEquipmentSlotByItemId(itemId);
        if(equipmentSlot != -1) {
            if(equip) {
                resourceStringUnpressed = Assets.equip_button_1;
                resourceStringPressed = Assets.equip_button_2;
                USE_BUTTON_WIDTH += PlayScreen.V_WIDTH*0.05f;
            }else{
                resourceStringUnpressed = Assets.unequip_button_1;
                resourceStringPressed = Assets.unequip_button_2;
                USE_BUTTON_WIDTH += PlayScreen.V_WIDTH*0.07f;
            }
            USE_BUTTON_X = (PlayScreen.V_WIDTH/2)-(USE_BUTTON_WIDTH/2);
        }else {
            resourceStringUnpressed = Assets.use_button_1;
            resourceStringPressed = Assets.use_button_2;
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.manager.get(Assets.item_selected_background, Texture.class), item_info_x, item_info_y, ITEM_INFO_WIDTH, ITEM_INFO_HEIGHT);

        batch.draw(Assets.manager.get(Assets.bag_close_button, Texture.class), close_x, close_y, ITEM_INFO_CLOSE_WIDTH, ITEM_INFO_CLOSE_HEIGHT);

        batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(itemId), Texture.class), IMAGE_X, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);

        //g.drawString(ItemFactory.getNameOfItemById(itemId), NAME_X, NAME_Y);

        String[] descArray = ItemFactory.getDescriptionOfItemById(itemId).split("\n");
        for (int i = 0; i < descArray.length; i++) {
            //g.drawString(descArray[i], DESC_X, DESC_Y+(i*DESC_PADDING));
        }

        if(use_button_pressed) {
            batch.draw(Assets.manager.get(resourceStringPressed, Texture.class), USE_BUTTON_X, USE_BUTTON_Y, USE_BUTTON_WIDTH, USE_BUTTON_HEIGHT);
        }else {
            batch.draw(Assets.manager.get(resourceStringUnpressed, Texture.class), USE_BUTTON_X, USE_BUTTON_Y, USE_BUTTON_WIDTH, USE_BUTTON_HEIGHT);
        }

        //g.setFont(Utils.playerNameFont);
    }

    public void handleClick(int screenX, int screenY) {
        if(checkIfClickedOnClose(screenX, screenY)) {
            Bag.SHOW_ITEM_INFO = false;
            return;
        }
        if(checkIfClickedOnUse(screenX, screenY)) {
            int equipmentSlot = Equipment.getEquipmentSlotByItemId(itemId);
            if(equipmentSlot > 0) {
                if(equip) {
                    playScreen.player.content.equipItemById(itemId, equipmentSlot);
                }else {
                    playScreen.player.content.unequipItemBySlotId(equipmentSlot);
                }
                Bag.SHOW_ITEM_INFO = false;
                return;
            }
        }
    }

    public boolean checkIfClickedOnClose(int screenX, int screenY) {
        if(screenX >= close_x && screenX <= close_x+ITEM_INFO_CLOSE_WIDTH && screenY >= close_y && screenY <= close_y+ITEM_INFO_CLOSE_HEIGHT) {
            return true;
        }
        return false;
    }

    public boolean checkIfClickedOnUse(int screenX, int screenY) {
        if(screenX >= USE_BUTTON_X && screenX <= USE_BUTTON_X+USE_BUTTON_WIDTH && screenY >= USE_BUTTON_Y && screenY <= USE_BUTTON_Y+USE_BUTTON_HEIGHT) {
            return true;
        }
        return false;
    }

    public void setUseButtonPressed() {
        use_button_pressed = true;
    }

    public void setUseButtonUnPressed() {
        use_button_pressed = false;
    }

}
