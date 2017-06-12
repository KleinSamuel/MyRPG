package com.kleinsamuel.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 11.06.17.
 */

public class PopupWindow {

    private float X = PlayScreen.V_WIDTH*0.1f;
    private float Y = PlayScreen.V_HEIGHT*0.01f;
    private float WIDTH = PlayScreen.V_WIDTH*0.8f;
    private float HEIGHT = PlayScreen.V_HEIGHT*0.5f;

    private float EXIT_BUTTON_WIDTH = 30;
    private float EXIT_BUTTON_HEIGHT = 30;
    private float EXIT_BUTTON_X = X+WIDTH-(EXIT_BUTTON_WIDTH);
    private float EXIT_BUTTON_Y = Y+HEIGHT-(EXIT_BUTTON_HEIGHT);

    private float PADDING = WIDTH*0.1f;

    private String sequence;

    public PopupWindow(String sequence){
        this.sequence = sequence;
    }

    public boolean handleClick(int screenX, int screenY){
        if(screenX >= EXIT_BUTTON_X && screenX <= EXIT_BUTTON_X+EXIT_BUTTON_WIDTH && screenY >= EXIT_BUTTON_Y && screenY <= EXIT_BUTTON_Y+EXIT_BUTTON_HEIGHT){
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(Assets.popup_background, Texture.class), X, Y, WIDTH, HEIGHT);
        batch.draw(Assets.manager.get(Assets.bag_close_button, Texture.class), EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.setColor(Color.BLACK);
        Utils.testFont.draw(batch, sequence, X+(PADDING/2), Y+HEIGHT-(PADDING/2), WIDTH-PADDING, Align.left, true);
    }

}
