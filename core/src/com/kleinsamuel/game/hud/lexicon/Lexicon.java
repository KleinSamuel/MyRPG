package com.kleinsamuel.game.hud.lexicon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;

import static com.kleinsamuel.game.util.Utils.FACTOR;

/**
 * Created by sam on 03.06.17.
 */

public class Lexicon {

    private PlayScreen playScreen;

    public static boolean SHOW_LEXICON = false;

    /* coordinates for close button of bag */
    public final float CLOSE_X_1 = 909.0f * FACTOR;
    public final float CLOSE_Y_1 = 492.0f * FACTOR;
    public final float CLOSE_X_2 = 941.0f * FACTOR;
    public final float CLOSE_Y_2 = 523.0f * FACTOR;

    /* coordinates for stat and bag buttons */
    public final float BAG_X_1 = 30.0f * FACTOR;
    public final float BAG_Y_1 = 490.0f * FACTOR;
    public final float BAG_X_2 = 112.0f * FACTOR;
    public final float BAG_Y_2 = 525.0f * FACTOR;
    public final float STAT_X_1 = 157.0f * FACTOR;
    public final float STAT_Y_1 = 490.0f * FACTOR;
    public final float STAT_X_2 = 238.0f * FACTOR;
    public final float STAT_Y_2 = 525.0f * FACTOR;

    public Lexicon(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public void handleClick(int screenX, int screenY) {
        if (clickOnExit(screenX, screenY)) {
            SHOW_LEXICON = false;
            return;
        }
        if(clickOnBag(screenX, screenY)){
            SHOW_LEXICON = false;
            playScreen.bag.SHOW_BAG = true;
            return;
        }
        if(clickOnStat(screenX, screenY)){
            SHOW_LEXICON = false;
            playScreen.stats.SHOW_STATS = true;
            return;
        }
    }

    public boolean clickOnExit(int screenX, int screenY) {
        if(screenX >= CLOSE_X_1 && screenX <= CLOSE_X_2 && screenY >= CLOSE_Y_1 && screenY <= CLOSE_Y_2) {
            return true;
        }
        return false;
    }

    public boolean clickOnBag(int screenX, int screenY){
        if(screenX >= BAG_X_1 && screenX <= BAG_X_2 && screenY >= BAG_Y_1 && screenY <= BAG_Y_2) {
            return true;
        }
        return false;
    }

    public boolean clickOnStat(int screenX, int screenY){
        if(screenX >= STAT_X_1 && screenX <= STAT_X_2 && screenY >= STAT_Y_1 && screenY <= STAT_Y_2) {
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.manager.get(Assets.lexicon_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);

        playScreen.hud.drawMoneyString(batch);

    }

}
