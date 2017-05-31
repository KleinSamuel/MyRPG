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

    private String name;
    private int level;
    private int entityX;
    private int entityY;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private int xMove;
    private int yMove;
    private int xPos;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;

    public OtherPlayer(SpriteSheet spriteSheet, String name, int x, int y){
        this.spriteSheet = spriteSheet;
        currentTexture = spriteSheet.getTextureRegion(0, 1);
        this.name = name;
        this.entityX = x;
        this.entityY = y;
        xMove = 0;
        yMove = 0;
        xPos = 1;
    }

    public void update(int x, int y, String name, int xMove, int yMove, int xPos){
        this.entityX = x;
        this.entityY = y;
        this.name = name;
        this.xMove = xMove;
        this.yMove = yMove;
        this.xPos = xPos;

        setCurrentImage(xMove, yMove, xPos);
    }

    void setCurrentImage(int x, int y, int xPos) {
        if (y == -1) {
            currentTexture = spriteSheet.getTextureRegion(0, xPos);
        } else if (y == 1) {
            currentTexture = spriteSheet.getTextureRegion(3, xPos);
        } else if (x == -1) {
            currentTexture = spriteSheet.getTextureRegion(1, xPos);
        } else if (x == 1) {
            currentTexture = spriteSheet.getTextureRegion(2, xPos);
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

    public void drawName(SpriteBatch batch) {
        Utils.basicFont.getData().setScale(0.4f, 0.5f);
        Utils.basicFont.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.basicFont, name);
        Utils.basicFont.draw(batch, name, entityX+Utils.TILEWIDTH/2-dims.x/2, entityY+77);
    }

    private int HEALTHBAR_PADDING = 2;
    private int HEALTHBAR_HEIGHT = 7;
    private int PERC_HEALTH;

    public void drawSmallHealthbar(SpriteBatch batch){

        if(currentHealth == 0 || maxHealth == 0){
            PERC_HEALTH = 1;
        }else{
            PERC_HEALTH = currentHealth/maxHealth;
        }

        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), entityX, entityY + currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), HEALTHBAR_HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), entityX+HEALTHBAR_PADDING, entityY+currentTexture.getRegionHeight()+HEALTHBAR_PADDING, (currentTexture.getRegionWidth()-2*HEALTHBAR_PADDING)*PERC_HEALTH, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), entityX+HEALTHBAR_PADDING, entityY+currentTexture.getRegionHeight()+HEALTHBAR_PADDING, currentTexture.getRegionWidth()-2*HEALTHBAR_PADDING, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);

    }

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, entityX, entityY);
    }

    public void renderAfter(SpriteBatch batch){
        drawName(batch);
        drawSmallHealthbar(batch);
    }

}
