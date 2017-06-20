package com.kleinsamuel.game.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 30.05.17.
 */

public class OtherPlayer {

    private boolean IN_PLAYER_RANGE;

    public String name;
    private int level;
    public int entityX;
    public int entityY;
    private float SPEED = 1.0f;
    public int currentHealth;
    public int maxHealth;
    private int currentMana;
    private int maxMana;
    private int xMove;
    private int yMove;
    private int xPos;

    public Vector3 moveTo;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;

    public OtherPlayer(SpriteSheet spriteSheet, String name, int level, int x, int y, int currentHealth, int maxHealth){
        this.spriteSheet = spriteSheet;
        currentTexture = spriteSheet.getTextureRegion(0, 1);
        this.name = name;
        this.level = level;
        this.entityX = x;
        this.entityY = y;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        xMove = 0;
        yMove = 0;
        xPos = 1;
    }

    public void update(int x, int y, String name){
        this.entityX = x;
        this.entityY = y;
        this.name = name;
    }

    public void update(Player player){

        if(!Utils.inRange(player.content.x, player.content.y, entityX, entityY)){
            IN_PLAYER_RANGE = false;
            return;
        }else{
            IN_PLAYER_RANGE = true;
        }

        if(moveTo != null) {
            Vector3 p = walkToPoint(moveTo);
            if(p != null){
                setMove(p);
            }else{
                setMove(new Vector3(0, 0, 0));
            }
            move();
        }
    }

    public void setMove(Vector3 p) {
        xMove = (int)p.x;
        yMove = (int)p.y;
    }

    int op = 1;
    int slow = 0;
    int oldDirX;
    int oldDirY;
    int prevDirection = 0;

    public void move() {

        entityX += xMove * SPEED;
        entityY += yMove * SPEED;

        if (slow++ >= 7) {
            if (xMove == 0 && yMove == 0) {
                slow = 7;
                setCurrentImage(0, 0, 1);

            } else {

                slow = 0;
                if (op == -1 && xPos <= 0) {
                    op = 1;
                } else if (op == 1 && xPos >= 2) {
                    op = -1;
                }
                xPos = (xPos + op);
                setCurrentImage(xMove, yMove, xPos);

                oldDirX = xMove;
                oldDirY = yMove;

            }
        }
    }

    /**
     * Return x and y directions needed to get to given point.
     * Returns null if given Point is reached
     */
    public Vector3 walkToPoint(Vector3 pointOnMap) {

        if(entityX == pointOnMap.x && entityY == pointOnMap.y) {
            return null;
        }

        int xMove = 0;
        int yMove = 0;

        if(entityX != pointOnMap.x) {
            if(entityX < pointOnMap.x) {
                xMove = 1;
            }else if(entityX > pointOnMap.x) {
                xMove = -1;
            }
            return new Vector3(xMove, yMove, 0);
        }

        if(entityY < pointOnMap.y) {
            yMove = 1;
        }else if(entityY > pointOnMap.y) {
            yMove = -1;
        }

        return new Vector3(xMove, yMove, 0);
    }

    void setCurrentImage(int x, int y, int xPos) {
        if (y == -1) {
            currentTexture = spriteSheet.getTextureRegion(0, xPos);
            prevDirection = 0;
        } else if (y == 1) {
            currentTexture = spriteSheet.getTextureRegion(3, xPos);
            prevDirection = 3;
        } else if (x == -1) {
            currentTexture = spriteSheet.getTextureRegion(1, xPos);
            prevDirection = 1;
        } else if (x == 1) {
            currentTexture = spriteSheet.getTextureRegion(2, xPos);
            prevDirection = 2;
        }else{
            currentTexture = spriteSheet.getTextureRegion(prevDirection, xPos);
        }

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    private float HEALTHBAR_PADDING = 0.5f;
    private float HEALTHBAR_HEIGHT = 3;
    private float PERC_HEALTH;

    public void drawSmallHealthbar(SpriteBatch batch){

        PERC_HEALTH = (1.0f*currentHealth)/(1.0f*maxHealth);
        PERC_HEALTH = 1.0f;

        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), entityX, entityY + Utils.TILEHEIGHT, Utils.TILEWIDTH, HEALTHBAR_HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), entityX+HEALTHBAR_PADDING, entityY+Utils.TILEHEIGHT+HEALTHBAR_PADDING, Utils.TILEWIDTH-2*HEALTHBAR_PADDING, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), entityX+HEALTHBAR_PADDING, entityY+Utils.TILEHEIGHT+HEALTHBAR_PADDING, (Utils.TILEWIDTH-2*HEALTHBAR_PADDING)*PERC_HEALTH, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
    }

    public void drawName(SpriteBatch batch) {
        Utils.font10.getData().setScale(0.5f, 0.5f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, name);
        Utils.font10.draw(batch, name, entityX+Utils.TILEWIDTH/2-dims.x/2, entityY+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+5);
    }

    public void drawLevel(SpriteBatch batch){
        Utils.font10.getData().setScale(0.4f, 0.4f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, "Lvl. "+level);
        Utils.font10.draw(batch, "Lvl. "+level, entityX+Utils.TILEWIDTH/2-dims.x/2, entityY+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+10);
    }

    public void render(SpriteBatch batch){
        if(!IN_PLAYER_RANGE){
            return;
        }
        batch.draw(currentTexture, entityX-5, entityY-4, Utils.TILEWIDTH+10, Utils.TILEHEIGHT+8);
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
