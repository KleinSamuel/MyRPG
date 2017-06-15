package com.kleinsamuel.game.model.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 31.05.17.
 */

public class Item {

    public ItemData data;
    private float width, height;

    public Item(ItemData data){
        this.data = data;
        Vector3 widthAndHeight = ItemFactory.getWidthAndHeight(data.getItem_key());
        this.width = widthAndHeight.x;
        this.height = widthAndHeight.y;
    }

    public Item(ItemData data, float width, float height){
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(data.getItem_key()), Texture.class), data.getX(), data.getY(), width, height);
    }

}
