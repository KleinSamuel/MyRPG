package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 02.06.17.
 */

public class NPC {

    private PlayScreen playScreen;

    public NPCData data;

    private boolean IN_PLAYER_RANGE;

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

    private float PERC_HEALTH;
    private float HEALTHBAR_PADDING = 0.5f;
    private float HEALTHBAR_HEIGHT = 3;

    public NPC(PlayScreen playScreen, SpriteSheet spriteSheet, NPCData data){

        this.playScreen = playScreen;

        this.spriteSheet =  spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 0);
        this.data = data;

        this.xMove = 0;
        this.yMove = 0;

        Vector3 widthAndHeight = NPCFactory.getWidthAndHeight(data.npc_key);
        this.WIDTH = (int)widthAndHeight.x;
        this.HEIGHT = (int)widthAndHeight.y;

        this.animationSpeed = 500;
        currentTextureNum = 0;
        timestamp = System.currentTimeMillis();
        Vector3 offset = computePositionOffset();
        offsetX = offset.x;
        offsetY = offset.y;

        PERC_HEALTH = (1.0f*data.current_health)/(1.0f*data.max_health);
    }

    public void setMove(Vector3 p){
        xMove = (int)p.x;
        yMove = (int)p.y;
    }

    public void move() {
        data.x += xMove*data.speed;
        data.y += yMove*data.speed;
    }

    /**
     * Return x and y directions needed to get to given point.
     * Returns null if given Point is reached
     */
    public Vector3 walkToPoint(Vector3 pointOnMap) {

        if(data.x == pointOnMap.x && data.y == pointOnMap.y) {
            return null;
        }

        int xMove = 0;
        int yMove = 0;

        if(data.x != pointOnMap.x) {
            if(data.x < pointOnMap.x) {
                xMove = 1;
            }else if(data.x > pointOnMap.x) {
                xMove = -1;
            }
            return new Vector3(xMove, yMove, 0);
        }

        if(data.y < pointOnMap.y) {
            yMove = 1;
        }else if(data.y > pointOnMap.y) {
            yMove = -1;
        }

        return new Vector3(xMove, yMove, 0);
    }

   /* public void updateOldCoordinates() {
        if(data.x % Utils.TILEWIDTH == 0 && data.x % Utils.TILEHEIGHT == 0) {
            oldDirX = (int)data.x/Utils.TILEWIDTH;
            oldDirY = (int)data.y/Utils.TILEHEIGHT;
        }
    }*/

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

    public void update(Player player){

        if(!Utils.inRange(player.content.x, player.content.y, data.x, data.y)){
            IN_PLAYER_RANGE = false;
            return;
        }else{
            IN_PLAYER_RANGE = true;
        }

        if(data.moveTo != null) {
            Vector3 p = walkToPoint(data.moveTo);
            if (p != null) {
                setMove(p);
            } else {
                setMove(new Vector3(0, 0, 0));
            }
            move();
        }
        setNextTexture();
    }

    // TODO add specific NPC NAME
    public void drawName(SpriteBatch batch) {
        playScreen.nameFont.getData().setScale(0.5f, 0.5f);
        playScreen.nameFont.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(playScreen.nameFont, data.name);
        playScreen.nameFont.draw(batch, data.name, data.x+Utils.TILEWIDTH/2-dims.x/2, data.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+5);
    }

    public void drawLevel(SpriteBatch batch){
        playScreen.nameFont.getData().setScale(0.4f, 0.4f);
        playScreen.nameFont.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(playScreen.nameFont, "Lvl. "+data.level);
        playScreen.nameFont.draw(batch, "Lvl. "+data.level, data.x+Utils.TILEWIDTH/2-dims.x/2, data.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+10);
    }

    public void drawSmallHealthbar(SpriteBatch batch){

        PERC_HEALTH = (1.0f*data.current_health)/(1.0f*data.max_health);
        if(PERC_HEALTH < 0.0f){
            PERC_HEALTH = 0.0f;
        }

        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), data.x, data.y + Utils.TILEHEIGHT, Utils.TILEWIDTH, HEALTHBAR_HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), data.x+HEALTHBAR_PADDING, data.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, Utils.TILEWIDTH-2*HEALTHBAR_PADDING, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), data.x+HEALTHBAR_PADDING, data.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, (Utils.TILEWIDTH-2*HEALTHBAR_PADDING)*PERC_HEALTH, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);

    }

    boolean up = true;
    float offset = 1;
    int upCounter = 0;

    public void render(SpriteBatch batch){

        if(!IN_PLAYER_RANGE){
            return;
        }

        if(xMove != 0 || yMove != 0){
            if(up){
                offset = 0.3f;
            }else{
                offset = -0.3f;
            }
            if(upCounter == 2){
                up = !up;
                upCounter = 0;
            }
            upCounter++;
        }else{
            offset = 0;
        }

        batch.draw(currentTexture, data.x-offsetX, data.y-offsetY+offset, WIDTH, HEIGHT);
    }

    public void renderAfter(SpriteBatch batch){
        if(!IN_PLAYER_RANGE){
            return;
        }
        drawSmallHealthbar(batch);
        drawName(batch);
        drawLevel(batch);
    }

}
