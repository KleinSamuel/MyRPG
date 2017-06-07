package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.Player;
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
    private Vector3 beamPosition;

    public ScreenSwitchAnimation(Player toBeam, Vector3 beamPosition){
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

        toBeam.content.x = beamPosition.x * Utils.TILEWIDTH;
        toBeam.content.y = beamPosition.y * Utils.TILEHEIGHT;
    }

    @Override
    public void render(SpriteBatch batch) {

        Color colorBefore = batch.getColor();
        batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, opacity));
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, 1.0f));
    }
}
