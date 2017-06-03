package com.kleinsamuel.game.hud.bars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;

/**
 * Created by sam on 30.05.17.
 */

public class Healthbar {

    private final int PADDING_LEFT = 10;
    private final int PADDING_TOP = 10;

    private final float WIDTH = PlayScreen.V_WIDTH*0.30f;
    private final float HEIGHT = PlayScreen.V_HEIGHT*0.075f;

    public float CURRENT_WIDTH = WIDTH;

    private double computePercent(int current, int max) {
        return (current*1.0)/(max*1.0);
    }

    public void setHealth(int current, int max){
        double perc = computePercent(current, max);
        CURRENT_WIDTH = (float)(perc*WIDTH);
    }

    public void render(SpriteBatch batch){

        batch.draw(Assets.manager.get(Assets.uf_healthbar, Texture.class), PADDING_LEFT, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH, HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), PADDING_LEFT+WIDTH-(WIDTH-CURRENT_WIDTH), PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH-CURRENT_WIDTH, HEIGHT);

        /*
        Utils.basicFont.getData().setScale(0.8f, 0.8f);
        Utils.basicFont.setColor(Color.WHITE);
        String s = currentHealth+"/"+maxHealth;
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.basicFont, s);
        float width = dims.x;
        float height = dims.y;
        Utils.basicFont.draw(batch, s, (WIDTH/2)-(width/2)+PADDING_LEFT, PlayScreen.V_HEIGHT-PADDING_TOP-(HEIGHT/2));
        */
    }
}
