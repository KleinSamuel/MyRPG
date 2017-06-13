package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 12.06.17.
 */

public class ConfirmationDialog {

    private StartScreen startScreen;

    private float X;
    private float Y;
    private float WIDTH;
    private float HEIGHT;

    private float YES_X;
    private float YES_Y;
    private float YES_WIDTH = 120;
    private float YES_HEIGHT = 60;

    private float NO_X;
    private float NO_Y;
    private float NO_WIDTH = 120;
    private float NO_HEIGHT = 60;

    private String message;

    public ConfirmationDialog(StartScreen startScreen, float width, float height) {
        this.startScreen = startScreen;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.X = StartScreen.START_WIDTH / 2 - WIDTH / 2;
        this.Y = StartScreen.START_HEIGHT / 2 - HEIGHT / 2;

        this.YES_X = StartScreen.START_WIDTH/2-YES_WIDTH-10;
        this.YES_Y = Y-YES_HEIGHT/2;

        this.NO_X = StartScreen.START_WIDTH/2+10;
        this.NO_Y = Y-NO_HEIGHT/2;
    }

    public void setText(String message){
        this.message = message;
    }

    public int handleClick(float screenX, float screenY){
        if(screenX >= YES_X && screenX <= YES_X+YES_WIDTH && screenY >= YES_Y && screenY <= YES_Y+YES_HEIGHT){
            DebugMessageFactory.printInfoMessage("YES CLICKED");
            startScreen.game.button_click.play();
            return 0;
        }
        if(screenX >= NO_X && screenX <= NO_X+NO_WIDTH && screenY >= NO_Y && screenY <= NO_Y+NO_HEIGHT){
            startScreen.raceChooseScreen.confirmationDialog = null;
            startScreen.game.button_click.play();
            return 1;
        }
        return -1;
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(Assets.popup_background, Texture.class), X, Y, WIDTH, HEIGHT);
        Utils.testFont.getData().setScale(2.0f, 2.0f);
        Utils.testFont.setColor(Color.valueOf("000000ff"));
        Utils.testFont.draw(batch, message,  X+40, Y+HEIGHT-40, WIDTH-50, Align.left, true);

        batch.draw(Assets.manager.get(Assets.yes_button, Texture.class), YES_X, YES_Y, YES_WIDTH, YES_HEIGHT);
        batch.draw(Assets.manager.get(Assets.no_button, Texture.class), NO_X, NO_Y, NO_WIDTH, NO_HEIGHT);
    }

}
