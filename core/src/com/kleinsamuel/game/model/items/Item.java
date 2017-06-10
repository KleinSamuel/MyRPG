package com.kleinsamuel.game.model.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 31.05.17.
 */

public class Item {

    public ItemData data;

    public Item(ItemData data){
        this.data = data;
    }

    public void render(SpriteBatch batch){
        batch.draw(Assets.manager.get(ItemFactory.getResourceStringForItemId(data.getItem_key()), Texture.class), data.getX(), data.getY(), Utils.TILEWIDTH, Utils.TILEHEIGHT);
    }

}
