package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by sam on 04.06.17.
 */

public abstract class Animation {

    public boolean delete;
    public boolean isHUD;

    public abstract void update();

    public abstract void render(SpriteBatch batch);

}
