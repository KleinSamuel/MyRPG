package com.kleinsamuel.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 30.05.17.
 */

public class Healthbar {

    private final int PADDING_LEFT = 10;
    private final int PADDING_TOP = 10;
    private final int INTERNAL_PADDING = 3;
    private final int WIDTH = 300;
    private final int HEIGHT = 40;
    private int currentHealth, maxHealth;

    public int CURRENT_WIDTH = WIDTH-(10*INTERNAL_PADDING);

    private double computePercent(int current, int max) {
        return (current*1.0)/(max*1.0);
    }

    public void setHealth(int current, int max){
        currentHealth = current;
        maxHealth = max;
        double perc = computePercent(current, max);
        CURRENT_WIDTH = (int)(perc*(WIDTH-(2*INTERNAL_PADDING)));
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), PADDING_LEFT, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP, WIDTH, HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), PADDING_LEFT+INTERNAL_PADDING, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP+INTERNAL_PADDING, WIDTH-(2*INTERNAL_PADDING), HEIGHT-(2*INTERNAL_PADDING));
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), PADDING_LEFT+INTERNAL_PADDING, PlayScreen.V_HEIGHT-HEIGHT-PADDING_TOP+INTERNAL_PADDING, CURRENT_WIDTH, HEIGHT-(2*INTERNAL_PADDING));

        Utils.basicFont.getData().setScale(0.8f, 0.8f);
        Utils.basicFont.setColor(Color.WHITE);

        String s = currentHealth+"/"+maxHealth;

        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.basicFont, s);
        float width = dims.x;
        float height = dims.y;
        Utils.basicFont.draw(batch, s, (WIDTH/2)-(width/2)+PADDING_LEFT, PlayScreen.V_HEIGHT-PADDING_TOP-(HEIGHT/2));

    }
}
