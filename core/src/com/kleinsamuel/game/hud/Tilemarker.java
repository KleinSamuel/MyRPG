package com.kleinsamuel.game.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 30.05.17.
 */

public class Tilemarker {

    private boolean isVisible = false;

    private int entityX, entityY;

    private Texture normal, enemy, current;

    public Tilemarker() {
        normal = Assets.manager.get(Assets.tilemarker_normal, Texture.class);
        enemy = Assets.manager.get(Assets.tilemarker_red, Texture.class);
        current = normal;
    }

    public void update(){

    }

    public void render(SpriteBatch batch){
        if(isVisible){
            batch.draw(current, entityX, entityY, Utils.TILEWIDTH, Utils.TILEHEIGHT);
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getEntityX() {
        return entityX;
    }

    public void setEntityX(int entityX) {
        this.entityX = entityX;
    }

    public int getEntityY() {
        return entityY;
    }

    public void setEntityY(int entityY) {
        this.entityY = entityY;
    }

}
