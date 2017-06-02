package com.kleinsamuel.game.hud.stats;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 03.06.17.
 */

public class Stats {

    private PlayScreen playScreen;

    public static boolean SHOW_STATS = false;

    /* coordinates for close button of bag */
    public final float CLOSE_X_1 = 909.0f * Utils.FACTOR;
    public final float CLOSE_Y_1 = 492.0f * Utils.FACTOR;
    public final float CLOSE_X_2 = 941.0f * Utils.FACTOR;
    public final float CLOSE_Y_2 = 523.0f * Utils.FACTOR;

    public Stats(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public void handleClick(int screenX, int screenY) {
        if (clickOnExit(screenX, screenY)) {
            SHOW_STATS = false;
            return;
        }
    }

    public boolean clickOnExit(int screenX, int screenY) {
        if(screenX >= CLOSE_X_1 && screenX <= CLOSE_X_2 && screenY >= CLOSE_Y_1 && screenY <= CLOSE_Y_2) {
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.manager.get(Assets.stats_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);

    }
}
