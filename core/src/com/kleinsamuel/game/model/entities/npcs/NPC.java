package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 02.06.17.
 */

public class NPC {

    public NPCData data;
    public final int OBSERVABLE_RANGE = 2;
    public final int OBSERVABLE_FACTOR = 1;
    public int toFollow_id;

    public int xMove;
    public int yMove;

    private int WIDTH;
    private int HEIGHT;
    private float offsetX;
    private float offsetY;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;
    private int currentTextureNum;

    public long animationSpeed;
    private long timestamp;

    public int oldArrayX;
    public int oldArrayY;

    public NPC(int currentX, int currentY, SpriteSheet spriteSheet, NPCData data){

        this.spriteSheet =  spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 0);
        this.data = data;

        this.xMove = 0;
        this.yMove = 0;
        this.WIDTH = Utils.TILEWIDTH;
        this.HEIGHT = Utils.TILEHEIGHT;
        this.animationSpeed = 500;
        currentTextureNum = 0;
        timestamp = System.currentTimeMillis();
        Vector3 offset = computePositionOffset();
        offsetX = offset.x;
        offsetY = offset.y;
    }

    public void setMove(Vector3 p){
        xMove = (int)p.x;
        yMove = (int)p.y;
    }

    /**
     * Compute offset if image should be displayed larger or smaller than tile.
     *
     * @return Vector3 x = xOffset, y = yOffset
     */
    public Vector3 computePositionOffset(){
        return new Vector3((WIDTH-Utils.TILEWIDTH)/2, (HEIGHT-Utils.TILEHEIGHT)/2, 0);
    }

    /**
     * Set the animation speed.
     * Time in milliseconds between transition of images.
     *
     * @param speed
     */
    public void setAnimationSpeed(long speed){
        this.animationSpeed = speed;
    }

    /**
     * Change size of image which will be displayed with the new size.
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height){
        WIDTH = width;
        HEIGHT = height;
        Vector3 offset = computePositionOffset();
        offsetX = offset.x;
        offsetY = offset.y;
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

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, data.getX()-offsetX, data.getY()-offsetY, WIDTH, HEIGHT);
    }

    public void renderAfter(SpriteBatch batch){
        // TODO draw name, level and health
    }

}
