package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 15.06.17.
 */

public class MessageInteraction extends Interactive {

    private int message_identifier_before;
    private int message_identifier_after;

    public float X = PlayScreen.V_WIDTH*0.1f;
    public float Y = PlayScreen.V_HEIGHT*0.01f;
    public float WIDTH = PlayScreen.V_WIDTH*0.8f;
    public float HEIGHT = PlayScreen.V_HEIGHT*0.5f;

    private float OK_WIDTH = 60;
    private float OK_HEIGHT = 30;
    private float OK_X = PlayScreen.V_WIDTH/2 - OK_WIDTH/2;
    private float OK_Y = Y;

    public MessageInteraction(int message_identifier){
        this.NEEDED_ITEM_IDENTIFIER = -1;
        this.GOT_NEEDED_ITEM = true;
        this.message_identifier_after = message_identifier;
    }

    public MessageInteraction(int itemNeeded, int message_identifier_before, int message_identifier_after){
        this.NEEDED_ITEM_IDENTIFIER = itemNeeded;
        this.message_identifier_before = message_identifier_before;
        this.message_identifier_after = message_identifier_after;
    }

    public MessageInteraction setWidthAndHeigth(float width, float heigth){
        WIDTH = width;
        HEIGHT = heigth;
        return this;
    }

    @Override
    public boolean handleClick(float screenX, float screenY){
        if(screenX >= OK_X && screenX <= OK_X+OK_WIDTH && screenY >= OK_Y && screenY <= OK_Y+OK_HEIGHT){
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch){

        if(IS_TRIGGERED) {

            batch.draw(Assets.manager.get(Assets.popup_background, Texture.class), X, Y, WIDTH, HEIGHT);
            Utils.font10.getData().setScale(2.0f, 2.0f);
            Utils.font10.setColor(Color.valueOf("000000ff"));
            if(!GOT_NEEDED_ITEM) {
                Utils.font10.draw(batch, MessageFactory.getMessageForMessageIdentifier(message_identifier_before), X + 40, Y + HEIGHT - 40, WIDTH - 50, Align.left, true);
            }else {
                Utils.font10.draw(batch, MessageFactory.getMessageForMessageIdentifier(message_identifier_after), X + 40, Y + HEIGHT - 40, WIDTH - 50, Align.left, true);
            }

            batch.draw(Assets.manager.get(Assets.ok_button, Texture.class), OK_X, OK_Y, OK_WIDTH, OK_HEIGHT);
        }
    }

}
