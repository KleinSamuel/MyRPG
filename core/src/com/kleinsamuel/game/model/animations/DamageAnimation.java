package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 04.06.17.
 */

public class DamageAnimation extends Animation{

    private boolean left;
    private boolean toEnemy;
    private int damage;
    private float x, y;
    private long animationspeed;
    private long duration;

    private long startTimestamp;
    private long timestamp;

    private int counter = 0;
    private double fac;

    public DamageAnimation(boolean left, boolean toEnemy, int damage, float x, float y){
        this.left = left;
        this.toEnemy = toEnemy;
        this.damage = damage;
        if(left){
            x -= Utils.TILEWIDTH/2;
        }
        this.x = x+(Utils.TILEWIDTH/2);
        this.y = y+Utils.TILEHEIGHT;
        this.animationspeed = 40;
        this.duration = 900;
        timestamp = System.currentTimeMillis();
        startTimestamp = timestamp;
        delete = false;
        fac = ((Utils.random.nextInt(20)*1.0)/100.0);
    }

    @Override
    public void update() {
        if(System.currentTimeMillis() - startTimestamp > duration){
            delete = true;
        }else{
            if(System.currentTimeMillis() - timestamp > animationspeed){
                timestamp = System.currentTimeMillis();
                x += (left) ? -1 : 1;
                y += 1-(fac*counter);
                counter++;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Utils.basicFont.getData().setScale(0.2f, 0.2f);
        if(toEnemy){
            Utils.basicFont.setColor(Color.WHITE);
        }else{
            Utils.basicFont.setColor(Color.RED);
        }
        Utils.basicFont.draw(batch, ""+damage, x, y);
    }
}
