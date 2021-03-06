package com.kleinsamuel.game.hud.bars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;

/**
 * Created by sam on 30.05.17.
 */

public class Manabar {

    private final float PADDING_LEFT = 10.0f;
    private final float PADDING_TOP = 38.0f;

    private final float WIDTH = PlayScreen.V_WIDTH*0.3f;
    private final float HEIGHT = PlayScreen.V_HEIGHT*0.06f;

    public float CURRENT_WIDTH = WIDTH;

    private double computePercent(int current, int max) {
        return (current*1.0)/(max*1.0);
    }

    public void setMana(int current, int max){

        double perc = computePercent(current, max);
        CURRENT_WIDTH = (int)(perc*WIDTH);
    }

    public void render(SpriteBatch batch){

        batch.draw(Assets.manager.get(Assets.uf_manabar, Texture.class), PADDING_LEFT, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH, HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), PADDING_LEFT+WIDTH-(WIDTH-CURRENT_WIDTH), PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH-CURRENT_WIDTH, HEIGHT);

        //batch.draw(Assets.manager.get(Assets.empty_bar, Texture.class), PADDING_LEFT, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH, HEIGHT);
        //batch.draw(Assets.manager.get(Assets.blue_bar, Texture.class), PADDING_LEFT+INTERNAL_PADDING-1, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP+INTERNAL_PADDING-4, CURRENT_WIDTH, HEIGHT-(2*(INTERNAL_PADDING-4)));

    }
}
