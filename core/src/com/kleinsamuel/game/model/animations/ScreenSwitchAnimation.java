package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.maps.BeamableTile;
import com.kleinsamuel.game.model.maps.MapFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 07.06.17.
 */

public class ScreenSwitchAnimation extends Animation{

    private long lastTimestamp;

    private long speed = 20;
    private float opacity = 0.0f;
    private boolean reachedMiddle = false;
    private boolean beam = true;

    private Player toBeam;
    private BeamableTile beamPosition;

    public ScreenSwitchAnimation(Player toBeam, BeamableTile beamPosition){
        this.toBeam = toBeam;
        this.beamPosition = beamPosition;
        PlayScreen.ACCEPT_INPUT = false;
    }

    public ScreenSwitchAnimation(){
        PlayScreen.ACCEPT_INPUT = false;
        beam = false;
    }

    @Override
    public void update() {

        if(System.currentTimeMillis()-lastTimestamp > speed){
            if(!reachedMiddle) {
                opacity += 0.025f;
            }else{
                if(opacity >= 0.025f){
                    opacity -= 0.025f;
                }
            }
            if(opacity >= 1.0f){
                reachedMiddle = true;
                if(beam){
                    beamPlayer();
                    beam = false;
                }
            }
            lastTimestamp = System.currentTimeMillis();
        }

        if(opacity <= 0.1f && reachedMiddle){
            PlayScreen.ACCEPT_INPUT = true;
            delete = true;
        }

    }

    /*
        Used to beam player to different place on same section
        TODO: beam player to different section
    */
    private void beamPlayer(){
        toBeam.pathToWalk.pathPoints.clear();
        toBeam.xMove = 0;
        toBeam.yMove = 0;
        toBeam.moveTo = null;
        toBeam.oldMoveTo = null;
        toBeam.isAttacking = false;
        toBeam.following = null;

        if(!toBeam.playScreen.currentMapSection.identifier.equals(beamPosition.mapIdentifier)){
            toBeam.playScreen.currentMapSection = MapFactory.getMapSectionForIdentifier(beamPosition.mapIdentifier);
        }

        toBeam.content.x = beamPosition.arrayX * Utils.TILEWIDTH;
        toBeam.content.y = beamPosition.arrayY * Utils.TILEHEIGHT;
        toBeam.content.mapIdentifier = toBeam.playScreen.currentMapSection.identifier;

        toBeam.playScreen.game.playerMovedToAnotherSection(toBeam);
    }

    @Override
    public void render(SpriteBatch batch) {

        Color colorBefore = batch.getColor();
        batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, opacity));
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, 1.0f));
    }
}
