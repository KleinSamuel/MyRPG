package com.kleinsamuel.game.model.maps.interactive;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kleinsamuel.game.model.entities.Player;

/**
 * Created by sam on 15.06.17.
 */

public abstract class Interactive {

    public boolean IS_TRIGGERED;
    public boolean GOT_NEEDED_ITEM;
    public int NEEDED_ITEM_IDENTIFIER;

    public void trigger(){
        IS_TRIGGERED = true;
    }

    public void removeTrigger(){
        IS_TRIGGERED = false;
    }

    public void render(SpriteBatch batch){}

    public void checkIfOwnsNeededItem(Player p){
        if(NEEDED_ITEM_IDENTIFIER != -1) {
            if(p.content.getBag().containsKey(NEEDED_ITEM_IDENTIFIER)){
                GOT_NEEDED_ITEM = true;
                p.content.removeFromBag(NEEDED_ITEM_IDENTIFIER);
            }else{
                GOT_NEEDED_ITEM = false;
            }
        }
    }

    /**
     * Returns true if window can be closed
     *
     * @param screenX
     * @param screenY
     * @return
     */
    public boolean handleClick(float screenX, float screenY){
        return false;
    }

}
