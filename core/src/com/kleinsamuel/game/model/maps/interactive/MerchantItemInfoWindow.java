package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.hud.bag.Bag;
import com.kleinsamuel.game.hud.bag.Equipment;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 16.06.17.
 */

public class MerchantItemInfoWindow {

    private PlayScreen playScreen;
    private int itemId;
    private int price;
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
    private float IMAGE_WIDTH = 4* Utils.TILEWIDTH;
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
    private float USE_BUTTON_X = (PlayScreen.V_WIDTH/2)-(USE_BUTTON_WIDTH/2);
    private float USE_BUTTON_Y = item_info_y+(USE_BUTTON_HEIGHT);
    private String useButtonString;

    private boolean buy;

    public MerchantItemInfoWindow(PlayScreen playScreen, int itemId, int price, boolean buy) {
        this.itemId = itemId;
        this.buy = buy;
        this.price = price;
        this.playScreen = playScreen;

        if(buy){
            useButtonString = Assets.buy_button;
        }else{
            useButtonString = Assets.sell_button;
        }
    }

    public boolean handleClick(float screenX, float screenY) {
        if(checkIfClickedOnClose(screenX, screenY)) {
            playScreen.button_click.play();
            return true;
        }
        if(checkIfClickedOnUse(screenX, screenY)) {
            /* check if player wants to buy */
            if(buy) {
                /* check if player own enough money */
                if (playScreen.player.content.MONEY >= price) {
                /* bag is full */
                    if (playScreen.player.content.BAG_SIZE <= playScreen.player.content.getCurrentBagSize()) {
                        DebugMessageFactory.printInfoMessage("BAG IS FULL!");
                        playScreen.error_beep.play();
                        return false;
                    }
                    playScreen.button_click.play();
                    playScreen.cash_register.play();
                    playScreen.player.content.MONEY -= price;
                    playScreen.player.content.putInBag(itemId);
                    return true;
                }
                playScreen.error_beep.play();
            }else{
                playScreen.button_click.play();
                playScreen.cash_register.play();
                playScreen.player.content.MONEY += price;
                playScreen.player.content.removeFromBag(itemId);
                return true;
            }
        }
        return false;
    }

    public boolean checkIfClickedOnClose(float screenX, float screenY) {
        if(screenX >= close_x && screenX <= close_x+ITEM_INFO_CLOSE_WIDTH && screenY >= close_y && screenY <= close_y+ITEM_INFO_CLOSE_HEIGHT) {
            return true;
        }
        return false;
    }

    public boolean checkIfClickedOnUse(float screenX, float screenY) {
        if(screenX >= USE_BUTTON_X && screenX <= USE_BUTTON_X+USE_BUTTON_WIDTH && screenY >= USE_BUTTON_Y && screenY <= USE_BUTTON_Y+USE_BUTTON_HEIGHT) {
            return true;
        }
        return false;
    }

    private void drawPrice(SpriteBatch batch){
        Utils.moneyFont.getData().setScale(1.0f, 1.0f);
        Utils.moneyFont.setColor(Color.YELLOW);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.moneyFont, "PRICE: "+price+" G");
        Utils.moneyFont.draw(batch, "PRICE: "+price+" G", USE_BUTTON_X+(USE_BUTTON_WIDTH/2)-dims.x/2, USE_BUTTON_Y+USE_BUTTON_HEIGHT+dims.y+5);
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
        drawPrice(batch);

        //g.setFont(Utils.playerNameFont);
    }


}
