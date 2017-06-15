package com.kleinsamuel.game.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.hud.PopupWindow;
import com.kleinsamuel.game.model.animations.ScreenSwitchAnimation;
import com.kleinsamuel.game.model.maps.InteractiveTile;
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

        if(playScreen.bag.SHOW_ITEM_INFO){
            //playScreen.bag.itemInfoWindow.setUseButtonPressed();
        }

        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {

        Vector3 hudCamUnprojected = playScreen.hud.stage.getCamera().unproject(new Vector3(x,y,0));

        int hudCamUnprojected_scaledX = (int)hudCamUnprojected.x;
        int hudCamUnprojected_scaledY = (int)hudCamUnprojected.y;

        Vector3 playscreenCamUnprojected = playScreen.gameCam.unproject(new Vector3(x, y, 0f));
        Vector3 arrayCoord = Utils.getArrayCoordinates(playscreenCamUnprojected.x, playscreenCamUnprojected.y);

        if(playScreen.popupWindow != null){
            if(playScreen.popupWindow.handleClick(hudCamUnprojected_scaledX, hudCamUnprojected_scaledY)){
                playScreen.button_click.play();
                playScreen.popupWindow = null;
            }
            return true;
        }

        /* TODO handle click of interactive tiles */
        for(InteractiveTile interactiveTile : playScreen.currentMapSection.interactiveTiles){
            if(interactiveTile.interactive.IS_TRIGGERED){
                if(interactiveTile.handleClick(hudCamUnprojected_scaledX, hudCamUnprojected_scaledY)){
                    PlayScreen.ACCEPT_INPUT = true;
                    playScreen.button_click.play();
                    return true;
                }
            }
        }

        if(!PlayScreen.ACCEPT_INPUT){
            return true;
        }

        /* check if player clicked on interactive tile */
        for(InteractiveTile interactiveTile : playScreen.currentMapSection.interactiveTiles){
            if(interactiveTile.arrayX == arrayCoord.x && interactiveTile.arrayY == arrayCoord.y){
                Vector3 playerArrayCoords = Utils.getArrayCoordinates(playScreen.player.content.x, playScreen.player.content.y);
                if(interactiveTile.checkIfTriggered(playerArrayCoords.x, playerArrayCoords.y)){
                    playScreen.ACCEPT_INPUT = false;
                    interactiveTile.interactive.checkIfOwnsNeededItem(playScreen.player);
                }
                return true;
            }
        }

        /* handle click if HUD element is visible */
        if(playScreen.bag.SHOW_BAG){
            playScreen.bag.handleClick(hudCamUnprojected_scaledX, hudCamUnprojected_scaledY);
            return true;
        }else if(playScreen.stats.SHOW_STATS){
            playScreen.stats.handleClick(hudCamUnprojected_scaledX, hudCamUnprojected_scaledY);
            return true;
        }else if(playScreen.lexicon.SHOW_LEXICON){
            playScreen.lexicon.handleClick(hudCamUnprojected_scaledX, hudCamUnprojected_scaledY);
            return true;
        }

        if(playScreen.hud.clickOnSettings((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON SETTINGS");
            playScreen.button_click.play();
            //playScreen.popupWindow = new PopupWindow("this is a motherfucking long test text to see if line wrapping works in this motherfucking popup window to display some text to peaople. Also this is a tesst to see how breaking works and if it breaks up words properly or just fucks them up like i do your mom.");
            return true;
        }
        else if(playScreen.hud.clickOnSocial((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON SOCIAL");
            playScreen.button_click.play();
            return true;
        }
        else if(playScreen.hud.clickOnNews((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON NEWS");
            playScreen.button_click.play();
            return true;
        }
        else if(playScreen.hud.clickOnStat((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON STAT");
            playScreen.button_click.play();
            playScreen.bag.SHOW_BAG = false;
            playScreen.bag.SHOW_ITEM_INFO = false;
            playScreen.lexicon.SHOW_LEXICON = false;
            playScreen.stats.SHOW_STATS = true;
            return true;
        }
        else if(playScreen.hud.clickOnLexicon((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON LEXICON");
            playScreen.button_click.play();
            playScreen.bag.SHOW_BAG = false;
            playScreen.bag.SHOW_ITEM_INFO = false;
            playScreen.stats.SHOW_STATS = false;
            playScreen.lexicon.SHOW_LEXICON = true;
            return true;
        }
        else if(playScreen.hud.clickOnShop((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON SHOP");
            playScreen.button_click.play();
            return true;
        }
        else if(playScreen.hud.clickOnBag((int)hudCamUnprojected_scaledX, (int)hudCamUnprojected_scaledY)){
            DebugMessageFactory.printNormalMessage("CLICK ON BAG");
            playScreen.button_click.play();
            playScreen.stats.SHOW_STATS = false;
            playScreen.lexicon.SHOW_LEXICON = false;
            playScreen.bag.SHOW_BAG = true;
            return true;
        }

        /* check if player clicked on npc to follow */
        if(playScreen.checkIfClickedOnNPC((int)arrayCoord.x, (int)arrayCoord.y)){
            return true;
        }
        /* if player clicked on another tiled than an npc -> stop following current npc */
        playScreen.player.following = null;

        if(playScreen.checkIfTileIsClickable((int)arrayCoord.x, (int)arrayCoord.y)) {

            if(playScreen.player.checkIfPointIsEndpointOfCurrentPath(arrayCoord)){
                return true;
            }

            playScreen.tilemarker.setToNormal();
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
