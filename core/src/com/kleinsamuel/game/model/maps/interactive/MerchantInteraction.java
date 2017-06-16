package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 16.06.17.
 */

public class MerchantInteraction extends Interactive{

    private int merchantIdentifier;

    private float EXIT_X = 913 * Utils.FACTOR;
    private float EXIT_Y = 496 * Utils.FACTOR;
    private float EXIT_WIDTH = 38 * Utils.FACTOR;
    private float EXIT_HEIGHT = 37 * Utils.FACTOR;

    public MerchantInteraction(int merchantIdentifier){
        this.merchantIdentifier = merchantIdentifier;
    }

    @Override
    public boolean handleClick(float screenX, float screenY) {
        if(clickedOnExit(screenX, screenY)){
            return true;
        }
        return false;
    }

    private boolean clickedOnExit(float screenX, float screenY){
        if(screenX >= EXIT_X && screenX <= EXIT_X+EXIT_WIDTH && screenY >= EXIT_Y && screenY <= EXIT_Y*EXIT_HEIGHT){
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(IS_TRIGGERED) {
            batch.draw(Assets.manager.get(Assets.merchant_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);
        }
    }
}
