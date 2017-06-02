package com.kleinsamuel.game.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 31.05.17.
 */

public class MyInputProcessor implements InputProcessor {

    private PlayScreen playScreen;

    public MyInputProcessor(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {

        DebugMessageFactory.printInfoMessage("CLICKED!");

        Vector3 v = playScreen.hud.stage.getCamera().unproject(new Vector3(x,y,0));

        int scaledX = (int)v.x;
        int scaledY = (int)v.y;

        if(playScreen.bag.DRAW_BAG){
            playScreen.bag.handleClick(scaledX, scaledY);
            return true;
        }

        if(playScreen.hud.clickOnSettings((int)scaledX, (int)scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON SETTINGS");
            return true;
        }
        else if(playScreen.hud.clickOnSocial((int)scaledX, (int)scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON SOCIAL");
            return true;
        }
        else if(playScreen.hud.clickOnNews((int)scaledX, (int)scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON NEWS");
            return true;
        }
        else if(playScreen.hud.clickOnChat((int)scaledX, (int)scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON CHAT");
            return true;
        }
        else if(playScreen.hud.clickOnBag((int)scaledX, (int)scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON BAG");
            playScreen.bag.DRAW_BAG = true;
            return true;
        }

        Vector3 v3 = playScreen.gameCam.unproject(new Vector3(x, y, 0f));
        Vector3 arrayCoord = Utils.getArrayCoordinates(v3.x, v3.y);

        if(playScreen.checkIfTileIsClickable((int)arrayCoord.x, (int)arrayCoord.y)) {

            if(playScreen.player.checkIfPointIsEndpointOfCurrentPath(arrayCoord)){
                return true;
            }

            playScreen.tilemarker.setVisible(true);
            playScreen.tilemarker.setEntityX((int) arrayCoord.x * Utils.TILEWIDTH);
            playScreen.tilemarker.setEntityY((int) arrayCoord.y * Utils.TILEHEIGHT);

            playScreen.player.createSmartPathTo(arrayCoord, new Vector3(playScreen.player.content.x,playScreen. player.content.y, 0));
        }
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}
