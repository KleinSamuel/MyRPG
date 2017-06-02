package com.kleinsamuel.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by sam on 29.05.17.
 */

public class SpriteSheet {

    public int FRAME_COLS;
    public int FRAME_ROWS;

    Animation<TextureRegion> animation;
    Texture texture;
    TextureRegion[][] textureRegions;

    float stateTime;

    public SpriteSheet(Texture texture, int rows, int cols){
        this.FRAME_ROWS = rows;
        this.FRAME_COLS = cols;
        this.texture = texture;
        create();
    }

    public void create(){

        textureRegions = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);

        TextureRegion[] frames = new TextureRegion[FRAME_ROWS * FRAME_COLS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++){
            for (int j = 0; j < FRAME_COLS; j++){
                frames[index++] = textureRegions[i][j];
            }
        }

        animation = new Animation<TextureRegion>(0.025f, frames);
        stateTime = 0f;
    }

    public TextureRegion getTextureRegion(int x, int y){
        return textureRegions[x][y];
    }

    public void render(){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
    }

    public void dispose(){
        texture.dispose();
    }

}
