package com.kleinsamuel.game.hud.settings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.GameClass;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 21.06.17.
 */

public class SettingsHUD {

    public boolean SHOW_SETTINGS = false;

    private PlayScreen playScreen;

    private final float EXIT_X = 913 * Utils.FACTOR;
    private final float EXIT_Y = 496 * Utils.FACTOR;
    private final float EXIT_WIDTH = 38 * Utils.FACTOR;
    private final float EXIT_HEIGHT = 37 * Utils.FACTOR;

    private final float DESC_X = PlayScreen.V_WIDTH*0.3f;

    private final float OPTIONS_X = PlayScreen.V_WIDTH*0.6f;

    private boolean show_small_chat = true;
    private float TICK_SHOW_CHAT_WIDTH = 28;
    private float TICK_SHOW_CHAT_HEIGHT = 28;
    private float TICK_SHOW_CHAT_X = OPTIONS_X;
    private float TICK_SHOW_CHAT_Y = PlayScreen.V_HEIGHT*0.7f-TICK_SHOW_CHAT_HEIGHT/2;

    private float LOGOUT_WIDTH = 80;
    private float LOGOUT_HEIGHT = 30;
    private float LOGOUT_X = PlayScreen.V_WIDTH/2-LOGOUT_WIDTH/2;
    private float LOGOUT_Y = PlayScreen.V_HEIGHT*0.2f;

    public SettingsHUD(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public void handleClick(float screenX, float screenY){
        if(clickOnExit(screenX, screenY)){
            SHOW_SETTINGS = false;
            playScreen.button_click.play();
            return;
        }
        if(clickOnShowSettings(screenX, screenY)){
            show_small_chat = !show_small_chat;
            playScreen.hud.chatWindowSmall.SHOW_CHAT_SMALL = show_small_chat;
            playScreen.button_click.play();
            return;
        }
        if(clickOnLogout(screenX, screenY)){
            playScreen.button_click.play();
            playScreen.player.content.ID = -1;
            playScreen.player.content.writeToFile();

            playScreen.game.IS_FIRST_STARTUP = true;
            if(playScreen != null) playScreen.hide();
            if(!playScreen.game.main_menu_music.isPlaying()){
                playScreen.game.main_menu_music.play();
            }
            playScreen.game.setScreen(playScreen.game.startScreen);
            playScreen.game.connect();
            return;
        }
    }

    public boolean clickOnExit(float screenX, float screenY){
        return (screenX >= EXIT_X && screenX <= EXIT_X+EXIT_WIDTH && screenY >= EXIT_Y && screenY <= EXIT_Y+EXIT_HEIGHT);
    }

    public boolean clickOnShowSettings(float screenX, float screenY){
        return (screenX >= TICK_SHOW_CHAT_X && screenX <= TICK_SHOW_CHAT_X+TICK_SHOW_CHAT_WIDTH && screenY >= TICK_SHOW_CHAT_Y && screenY <= TICK_SHOW_CHAT_Y+TICK_SHOW_CHAT_HEIGHT);
    }

    public boolean clickOnLogout(float screenX, float screenY){
        return (screenX >= LOGOUT_X && screenX <= LOGOUT_X+LOGOUT_WIDTH && screenY >= LOGOUT_Y && screenY <= LOGOUT_Y+LOGOUT_HEIGHT);
    }

    private void drawHeadline(SpriteBatch batch){
        Utils.font10.getData().setScale(3.0f, 3.0f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, "SETTINGS");
        Utils.font10.draw(batch, "SETTINGS", PlayScreen.V_WIDTH/2-dims.x/2, PlayScreen.V_HEIGHT*0.9f);
    }

    private void drawShowSmallChat(SpriteBatch batch){
        Utils.font10.getData().setScale(2.0f, 2.0f);
        Utils.font10.setColor(Color.BLACK);
        String s = "Show chat";
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, s);
        Utils.font10.draw(batch, s, DESC_X, PlayScreen.V_HEIGHT*0.7f+(dims.y/2));

        if(show_small_chat){
            batch.draw(Assets.manager.get(Assets.tick_box_ticked, Texture.class), TICK_SHOW_CHAT_X, TICK_SHOW_CHAT_Y, TICK_SHOW_CHAT_WIDTH, TICK_SHOW_CHAT_HEIGHT);
        }else{
            batch.draw(Assets.manager.get(Assets.tick_box_unticked, Texture.class), TICK_SHOW_CHAT_X, TICK_SHOW_CHAT_Y, TICK_SHOW_CHAT_WIDTH, TICK_SHOW_CHAT_HEIGHT);
        }
    }

    private void drawLogout(SpriteBatch batch){
        batch.draw(Assets.manager.get(Assets.logout_button, Texture.class), LOGOUT_X, LOGOUT_Y, LOGOUT_WIDTH, LOGOUT_HEIGHT);
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(Assets.chat_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);

        drawHeadline(batch);
        drawShowSmallChat(batch);

        drawLogout(batch);

    }

}
