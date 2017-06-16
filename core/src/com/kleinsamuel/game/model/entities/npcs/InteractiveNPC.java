package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 16.06.17.
 */

public class InteractiveNPC {

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;
    private int currentTextureNum;

    private long animationSpeed;
    private long timestamp;

    private String name;

    private float X;
    private float Y;
    private float WIDTH;
    private float HEIGHT;

    public InteractiveNPC(SpriteSheet spriteSheet, String name, int arrayX, int arrayY){
        this.spriteSheet = spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 0);
        this.currentTextureNum = 0;

        this.name = name;

        this.X = arrayX * Utils.TILEWIDTH;
        this.Y = arrayY * Utils.TILEHEIGHT;
        this.WIDTH = Utils.TILEWIDTH;
        this.HEIGHT = Utils.TILEHEIGHT;

        this.animationSpeed = 400;
        timestamp = System.currentTimeMillis();
    }

    public InteractiveNPC setWidthAndHeigth(float width, float height){
        this.WIDTH = width;
        this.HEIGHT = height;
        return this;
    }

    /**
     * Increases the current texture number to display the next image for animation.
     * If maximum number is reached it return 0 to start from the beginning;
     *
     * @return int next texture number
     */
    public int increaseTextureNum(){
        return (currentTextureNum == spriteSheet.FRAME_COLS-1) ? 0 : ++currentTextureNum;
    }

    /**
     * Sets the next image as current image for animation effect.
     * Only every x millis as specified in variable "animationSpeed"
     */
    public void setNextTexture(){
        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp - timestamp > animationSpeed){
            timestamp = currentTimestamp;
            currentTextureNum = increaseTextureNum();
            currentTexture = spriteSheet.getTextureRegion(0,  currentTextureNum);
        }
    }

    public void update(){
        setNextTexture();
    }

    private void drawName(SpriteBatch batch){
        Utils.font10.getData().setScale(1.0f, 1.0f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.testFont, name);
        Utils.testFont.draw(batch, name, X+Utils.TILEWIDTH/2-dims.x/2, Y+Utils.TILEHEIGHT+10);
    }

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, X, Y, WIDTH, HEIGHT);
    }

    public void renderAfter(SpriteBatch batch){
        drawName(batch);
    }

}
