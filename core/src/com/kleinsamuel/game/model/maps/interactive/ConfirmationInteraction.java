package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 15.06.17.
 */

public class ConfirmationInteraction extends Interactive {

    private String message;

    public float X = PlayScreen.V_WIDTH*0.1f;
    public float Y = PlayScreen.V_HEIGHT*0.01f;
    public float WIDTH = PlayScreen.V_WIDTH*0.8f;
    public float HEIGHT = PlayScreen.V_HEIGHT*0.5f;

    private float YES_WIDTH = 60;
    private float YES_HEIGHT = 30;
    private float YES_X = PlayScreen.V_WIDTH/2 - YES_WIDTH - 3;
    private float YES_Y = Y;

    private float NO_WIDTH = 60;
    private float NO_HEIGHT = 30;
    private float NO_X = PlayScreen.V_WIDTH/2 + 3;
    private float NO_Y = Y;

    public ConfirmationInteraction(int message_identifier){
        this.message = MessageFactory.getMessageForMessageIdentifier(message_identifier);
    }

    public boolean handleClick(float screenX, float screenY){
        if(screenX >= YES_X && screenX <= YES_X+YES_WIDTH && screenY >= YES_Y && screenY <= YES_Y+YES_HEIGHT){
            DebugMessageFactory.printInfoMessage("YES CLICKED");
            return true;
        }
        if(screenX >= NO_X && screenX <= NO_X+NO_WIDTH && screenY >= NO_Y && screenY <= NO_Y+NO_HEIGHT){
            DebugMessageFactory.printInfoMessage("NO CLICKED");
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {

        if(IS_TRIGGERED) {
            batch.draw(Assets.manager.get(Assets.popup_background, Texture.class), X, Y, WIDTH, HEIGHT);

            Utils.font10.getData().setScale(2.0f, 2.0f);
            Utils.font10.setColor(Color.valueOf("000000ff"));
            Utils.font10.draw(batch, message, X + 40, Y + HEIGHT - 40, WIDTH - 50, Align.left, true);

            batch.draw(Assets.manager.get(Assets.yes_button, Texture.class), YES_X, YES_Y, YES_WIDTH, YES_HEIGHT);
            batch.draw(Assets.manager.get(Assets.no_button, Texture.class), NO_X, NO_Y, NO_WIDTH, NO_HEIGHT);
        }
    }
}
