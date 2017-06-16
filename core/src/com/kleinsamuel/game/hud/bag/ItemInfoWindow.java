package com.kleinsamuel.game.hud.bag;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

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

    private float IMAGE_PADDING = 20.0f;
    private float IMAGE_WIDTH = 4*Utils.TILEWIDTH;
    private float IMAGE_HEIGHT = 4*Utils.TILEHEIGHT;
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
    private float USE_BUTTON_X = (PlayScreen.V_WIDTH/2)-(USE_BUTTON_WIDTH)-3;
    private float USE_BUTTON_Y = item_info_y+(USE_BUTTON_HEIGHT);

    private float DROP_BUTTON_WIDTH = PlayScreen.V_WIDTH*0.1f;
    private float DROP_BUTTON_HEIGHT = PlayScreen.V_WIDTH*0.05f;
    private float DROP_BUTTON_X = (PlayScreen.V_WIDTH/2)+3;
    private float DROP_BUTTON_Y = item_info_y+(USE_BUTTON_HEIGHT);

    private String useButtonString;
    private boolean equip;

    public ItemInfoWindow(PlayScreen playScreen, int itemId, boolean equip) {
        this.itemId = itemId;
        this.equip = equip;
        this.playScreen = playScreen;

        int equipmentSlot = Equipment.getEquipmentSlotByItemId(itemId);
        if(equipmentSlot != -1) {
            if(equip) {
                useButtonString = Assets.equip_button;
                USE_BUTTON_WIDTH += PlayScreen.V_WIDTH*0.03f;
            }else{
                useButtonString = Assets.unequip_button;
                USE_BUTTON_WIDTH += PlayScreen.V_WIDTH*0.05f;
            }
            USE_BUTTON_X = (PlayScreen.V_WIDTH/2)-(USE_BUTTON_WIDTH)-3;
        }else {
            useButtonString = Assets.use_button;
        }
    }

    public void render(SpriteBatch batch) {

        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 0.7f);
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);
        batch.setColor(c.r, c.g, c.b, 1f);

        batch.draw(Assets.manager.get(Assets.item_selected_background, Texture.class), item_info_x, item_info_y, ITEM_INFO_WIDTH, ITEM_INFO_HEIGHT);

        batch.draw(Assets.manager.get(Assets.bag_close_button, Texture.class), close_x, close_y, ITEM_INFO_CLOSE_WIDTH, ITEM_INFO_CLOSE_HEIGHT);

        batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(itemId), Texture.class), IMAGE_X, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);

        //g.drawString(ItemFactory.getNameOfItemById(itemId), NAME_X, NAME_Y);

        String[] descArray = ItemFactory.getDescriptionOfItemById(itemId).split("\n");
        for (int i = 0; i < descArray.length; i++) {
            //g.drawString(descArray[i], DESC_X, DESC_Y+(i*DESC_PADDING));
        }

        batch.draw(Assets.manager.get(useButtonString, Texture.class), USE_BUTTON_X, USE_BUTTON_Y, USE_BUTTON_WIDTH, USE_BUTTON_HEIGHT);
        batch.draw(Assets.manager.get(Assets.drop_button, Texture.class), DROP_BUTTON_X, DROP_BUTTON_Y, DROP_BUTTON_WIDTH, DROP_BUTTON_HEIGHT);

        //g.setFont(Utils.playerNameFont);
    }

    public void handleClick(int screenX, int screenY) {
        if(checkIfClickedOnClose(screenX, screenY)) {
            Bag.SHOW_ITEM_INFO = false;
            playScreen.button_click.play();
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
            }else{
                ItemFactory.useConsumable(itemId, playScreen.player);
            }
            playScreen.button_click.play();
        }
        if(checkIfClickedOnDrop(screenX, screenY)){
            playScreen.player.content.removeFromBag(itemId);
            Bag.SHOW_ITEM_INFO = false;
            playScreen.button_click.play();
            return;
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

    public boolean checkIfClickedOnDrop(int screenX, int screenY) {
        if(screenX >= DROP_BUTTON_X && screenX <= DROP_BUTTON_X+DROP_BUTTON_WIDTH && screenY >= DROP_BUTTON_Y && screenY <= DROP_BUTTON_Y+DROP_BUTTON_HEIGHT) {
            return true;
        }
        return false;
    }

}
