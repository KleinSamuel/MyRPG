package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

import java.util.ArrayList;

/**
 * Created by sam on 12.06.17.
 */

public class StartscreenInputProcessor implements InputProcessor {

    private ArrayList<Dialog> dialogs;

    private StartScreen startScreen;

    public StartscreenInputProcessor(StartScreen startScreen){
        this.startScreen = startScreen;
        dialogs = new ArrayList<Dialog>();
    }

    public void addDialog(Dialog d){
        this.dialogs.add(d);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 raceCamUnprojected = startScreen.raceChooseScreen.stage.getCamera().unproject(new Vector3(screenX,screenY,0));

        float clickX = raceCamUnprojected.x;
        float clickY = raceCamUnprojected.y;

        if(startScreen.raceChooseScreen.confirmationDialog != null){
            int code = startScreen.raceChooseScreen.confirmationDialog.handleClick(clickX, clickY);

            if(code == 0){
                startScreen.game.main_menu_music.stop();
                startScreen.game.setScreen(new PlayScreen(startScreen.game));
            }

            return true;
        }

        if(startScreen.raceChooseScreen != null){
            startScreen.raceChooseScreen.handleClick(clickX, clickY);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
