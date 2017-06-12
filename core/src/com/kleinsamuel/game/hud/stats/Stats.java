package com.kleinsamuel.game.hud.stats;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import static com.kleinsamuel.game.util.Utils.FACTOR;

/**
 * Created by sam on 03.06.17.
 */

public class Stats {

    private PlayScreen playScreen;

    public static boolean SHOW_STATS = false;

    /* coordinates for close button of bag */
    public final float CLOSE_X_1 = 909.0f * FACTOR;
    public final float CLOSE_Y_1 = 492.0f * FACTOR;
    public final float CLOSE_X_2 = 941.0f * FACTOR;
    public final float CLOSE_Y_2 = 523.0f * FACTOR;

    /* coordinates for bag and encyclopedia buttons */
    public final float BAG_X_1 = 30.0f * FACTOR;
    public final float BAG_Y_1 = 490.0f * FACTOR;
    public final float BAG_X_2 = 112.0f * FACTOR;
    public final float BAG_Y_2 = 525.0f * FACTOR;
    public final float ENCY_X_1 = 159.0f * FACTOR;
    public final float ENCY_Y_1 = 490.0f * FACTOR;
    public final float ENCY_X_2 = 238.0f * FACTOR;
    public final float ENCY_Y_2 = 525.0f * FACTOR;

    private float LEFT_X_1 = 30.0f * Utils.FACTOR;
    private float LEFT_Y_1 = 30.0f * Utils.FACTOR;
    private float LEFT_WIDTH = 211.0f * Utils.FACTOR;
    private float LEFT_HEIGHT = 400.0f * Utils.FACTOR;
    private float LEFT_PADDING = 5.0f;
    private float LEFT_STRING_HEIGHT = 35.0f;
    private float LEFT_PADDING_RIGHT = 90.0f;

    private float DIST = LEFT_STRING_HEIGHT-LEFT_PADDING;

    private float X_1_HP = LEFT_X_1;
    private float Y_1_HP = LEFT_Y_1+LEFT_HEIGHT+5;
    private float X_2_HP = LEFT_X_1+LEFT_WIDTH;
    private float Y_2_HP = Y_1_HP-26.8f;
    private float X_1_MANA = X_1_HP;
    private float Y_1_MANA = Y_1_HP-DIST;
    private float X_2_MANA = X_2_HP;
    private float Y_2_MANA = Y_2_HP-DIST;
    private float X_1_XP = X_1_HP;
    private float Y_1_XP = Y_1_HP-DIST*2;
    private float X_2_XP = X_2_HP;
    private float Y_2_XP = Y_2_HP-DIST*2;
    private float X_1_ATK = X_1_HP;
    private float Y_1_ATK = Y_1_HP-DIST*3;
    private float X_2_ATK = X_2_HP;
    private float Y_2_ATK = Y_2_HP-DIST*3;
    private float X_1_ATKS = X_1_HP;
    private float Y_1_ATKS = Y_1_HP-DIST*4;
    private float X_2_ATKS = X_2_HP;
    private float Y_2_ATKS = Y_2_HP-DIST*4;
    private float X_1_DEF = X_1_HP;
    private float Y_1_DEF = Y_1_HP-DIST*5;
    private float X_2_DEF = X_2_HP;
    private float Y_2_DEF = Y_2_HP-DIST*5;
    private float X_1_AGI = X_1_HP;
    private float Y_1_AGI = Y_1_HP-DIST*6;
    private float X_2_AGI = X_2_HP;
    private float Y_2_AGI = Y_2_HP-DIST*6;
    private float X_1_CRT = X_1_HP;
    private float Y_1_CRT = Y_1_HP-DIST*7;
    private float X_2_CRT = X_2_HP;
    private float Y_2_CRT = Y_2_HP-DIST*7;
    private float X_1_CRTS = X_1_HP;
    private float Y_1_CRTS = Y_1_HP-DIST*8;
    private float X_2_CRTS = X_2_HP;
    private float Y_2_CRTS = Y_2_HP-DIST*8;

    private int clickedIdentifier = -1;
    private float clickedPadding = 0;

    public Stats(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public void handleClick(int screenX, int screenY) {
        if (clickOnExit(screenX, screenY)) {
            SHOW_STATS = false;
            playScreen.button_click.play();
            return;
        }
        if(clickOnBag(screenX, screenY)){
            SHOW_STATS = false;
            playScreen.bag.SHOW_BAG = true;
            playScreen.button_click.play();
            return;
        }
        if(clickOnLexicon(screenX, screenY)){
            SHOW_STATS = false;
            playScreen.bag.SHOW_BAG = true;
            playScreen.button_click.play();
            return;
        }
        if(clickOnHP(screenX, screenY)){
            clickedIdentifier = 0;
            playScreen.button_click.play();
            return;
        }
        if(clickOnMANA(screenX, screenY)){
            clickedIdentifier = 1;
            playScreen.button_click.play();
            return;
        }
        if(clickOnXP(screenX, screenY)){
            clickedIdentifier = 2;
            playScreen.button_click.play();
            return;
        }
        if(clickOnATK(screenX, screenY)){
            clickedIdentifier = 3;
            playScreen.button_click.play();
            return;
        }
        if(clickOnATKS(screenX, screenY)){
            clickedIdentifier = 4;
            playScreen.button_click.play();
            return;
        }
        if(clickOnDEF(screenX, screenY)){
            clickedIdentifier = 5;
            playScreen.button_click.play();
            return;
        }
        if(clickOnAGI(screenX, screenY)){
            clickedIdentifier = 6;
            playScreen.button_click.play();
            return;
        }
        if(clickOnCRT(screenX, screenY)){
            clickedIdentifier = 7;
            playScreen.button_click.play();
            return;
        }
        if(clickOnCRTS(screenX, screenY)){
            clickedIdentifier = 8;
            playScreen.button_click.play();
            return;
        }
        clickedIdentifier = -1;
    }

    public boolean clickOnExit(int screenX, int screenY) {
        if(screenX >= CLOSE_X_1 && screenX <= CLOSE_X_2 && screenY >= CLOSE_Y_1 && screenY <= CLOSE_Y_2) {
            return true;
        }
        return false;
    }

    public boolean clickOnBag(int screenX, int screenY) {
        if(screenX >= BAG_X_1 && screenX <= BAG_X_2 && screenY >= BAG_Y_1 && screenY <= BAG_Y_2) {
            return true;
        }
        return false;
    }

    public boolean clickOnLexicon(int screenX, int screenY) {
        if(screenX >= ENCY_X_1 && screenX <= ENCY_X_2 && screenY >= ENCY_Y_1 && screenY <= ENCY_Y_2) {
            return true;
        }
        return false;
    }

    private boolean clickOnHP(int screenX, int screenY){
        if(screenX >= X_1_HP && screenX <= X_2_HP && screenY >= Y_2_HP && screenY <= Y_1_HP){
            return true;
        }
        return false;
    }
    private boolean clickOnMANA(int screenX, int screenY){
        if(screenX >= X_1_MANA && screenX <= X_2_MANA && screenY >= Y_2_MANA && screenY <= Y_1_MANA){
            return true;
        }
        return false;
    }
    private boolean clickOnXP(int screenX, int screenY){
        if(screenX >= X_1_XP && screenX <= X_2_XP && screenY >= Y_2_XP && screenY <= Y_1_XP){
            return true;
        }
        return false;
    }
    private boolean clickOnATK(int screenX, int screenY){
        if(screenX >= X_1_ATK && screenX <= X_2_ATK && screenY >= Y_2_ATK && screenY <= Y_1_ATK){
            return true;
        }
        return false;
    }
    private boolean clickOnATKS(int screenX, int screenY){
        if(screenX >= X_1_ATKS && screenX <= X_2_ATKS && screenY >= Y_2_ATKS && screenY <= Y_1_ATKS){
            return true;
        }
        return false;
    }
    private boolean clickOnDEF(int screenX, int screenY){
        if(screenX >= X_1_DEF && screenX <= X_2_DEF && screenY >= Y_2_DEF && screenY <= Y_1_DEF){
            return true;
        }
        return false;
    }
    private boolean clickOnAGI(int screenX, int screenY){
        if(screenX >= X_1_AGI && screenX <= X_2_AGI && screenY >= Y_2_AGI && screenY <= Y_1_AGI){
            return true;
        }
        return false;
    }
    private boolean clickOnCRT(int screenX, int screenY){
        if(screenX >= X_1_CRT && screenX <= X_2_CRT && screenY >= Y_2_CRT && screenY <= Y_1_CRT){
            return true;
        }
        return false;
    }
    private boolean clickOnCRTS(int screenX, int screenY){
        if(screenX >= X_1_CRTS && screenX <= X_2_CRTS && screenY >= Y_2_CRTS && screenY <= Y_1_CRTS){
            return true;
        }
        return false;
    }

    private void changeColorIfClicked(int current){
        if(clickedIdentifier == current){
            Utils.testFont.setColor(Color.valueOf("3b83f7ff"));
            clickedPadding = 10.0f;
        }else{
            Utils.testFont.setColor(Color.valueOf("1e1e58ff"));
            clickedPadding = 0.0f;
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.manager.get(Assets.stats_background, Texture.class), 0, 0, PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);

        changeColorIfClicked(0);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "HP", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*0);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, ""+playScreen.player.content.MAX_HEALTH, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*0);

        changeColorIfClicked(1);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "MANA", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*1);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, ""+playScreen.player.content.MAX_MANA, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*1);

        changeColorIfClicked(2);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "XP", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*2);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, ""+playScreen.player.content.CURRENT_EXPERIENCE, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*2);

        changeColorIfClicked(3);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "ATK", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*3);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, ""+playScreen.player.content.VALUE_ATTACK, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*3);

        changeColorIfClicked(4);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "ATKS", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*4);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, "x "+playScreen.player.content.VALUE_ATTACK_SPEED/1000, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*4);

        changeColorIfClicked(5);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "DEF", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*5);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, ""+playScreen.player.content.VALUE_DEFENSE, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*5);

        changeColorIfClicked(6);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "AGI", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*6);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, "x "+playScreen.player.content.VALUE_MOVING_SPEED/0.5f, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*6);

        changeColorIfClicked(7);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "CRT", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*7);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, "x "+playScreen.player.multiplier.MULTIPLIER_CRIT_DAMAGE, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*7);

        changeColorIfClicked(8);
        Utils.testFont.getData().setScale(1.8f, 1.4f);
        Utils.testFont.draw(batch, "CRTC", LEFT_X_1+clickedPadding, LEFT_Y_1+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*8);
        Utils.testFont.getData().setScale(1.0f, 1.0f);
        Utils.testFont.draw(batch, "x "+playScreen.player.multiplier.MULTIPLIER_CRIT_CHANCE, LEFT_X_1+LEFT_PADDING_RIGHT, LEFT_Y_1-3+LEFT_HEIGHT-(LEFT_STRING_HEIGHT-LEFT_PADDING)*8);

        playScreen.hud.drawMoneyString(batch);
    }
}
