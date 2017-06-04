package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 03.06.17.
 */

public class EffectAnimation extends Animation{

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;
    private int currentTextureIndex;
    private long speed;
    private float x, y;
    private float width = Utils.TILEWIDTH;
    private float height = Utils.TILEHEIGHT;

    private long timestamp;

    public EffectAnimation(SpriteSheet spriteSheet, long speed, float x, float y){
        this.spriteSheet = spriteSheet;
        this.currentTextureIndex = 0;
        this.speed = speed;
        this.x = x;
        this.y = y;
        currentTexture = spriteSheet.getTextureRegion(0, currentTextureIndex);
        this.timestamp = System.currentTimeMillis();
        delete = false;
    }

    @Override
    public void update(){
        if(System.currentTimeMillis()-timestamp > speed){
            timestamp = System.currentTimeMillis();
            currentTextureIndex++;
            if(currentTextureIndex < spriteSheet.FRAME_COLS) {
                currentTexture = spriteSheet.getTextureRegion(0, currentTextureIndex);
            }else{
                delete = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch){
        batch.draw(currentTexture, x, y, width, height);
    }

}
