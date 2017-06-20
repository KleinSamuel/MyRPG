package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 17.06.17.
 */

public class InfoAnimation extends Animation {

    private String message;

    private float x;
    private float y;

    private long duration = 1500;
    private long startTimestamp;

    private float scale = 0.7f;
    private boolean bigger = true;

    public InfoAnimation(String message){
        isHUD = true;
        this.message = message;
        this.startTimestamp = System.currentTimeMillis();
        this.x = PlayScreen.V_WIDTH/2;
        this.y = PlayScreen.V_HEIGHT*0.7f;
    }

    @Override
    public void update() {
        if(System.currentTimeMillis()-startTimestamp > duration){
            delete = true;
        }
        this.y += 1;
        if(scale > 1.2f && bigger) {
            bigger = false;
        }

        this.scale += (bigger) ? 0.01f : -0.01f;
    }

    @Override
    public void render(SpriteBatch batch) {
        //Utils.moneyFont.getData().setScale(1.0f, 1.0f);
        Utils.moneyFont.getData().setScale(scale, scale);
        Utils.moneyFont.setColor(Color.WHITE);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.moneyFont, message);
        Utils.moneyFont.draw(batch, message, x-(dims.x/2), y, PlayScreen.V_WIDTH*0.8f, Align.left, true);
    }
}
