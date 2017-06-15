package com.kleinsamuel.game.model.maps;

import com.kleinsamuel.game.model.maps.interactive.Interactive;

/**
 * Created by sam on 15.06.17.
 */

public class InteractiveTile {

    public float arrayX;
    public float arrayY;

    public Interactive interactive;

    public InteractiveTile(float arrayX, float arrayY, Interactive interactive){
        this.arrayX = arrayX;
        this.arrayY = arrayY;
        this.interactive = interactive;
    }

    /**
     * Returns true when window can be closed
     *
     * @param screenX
     * @param screenY
     * @return
     */
    public boolean handleClick(float screenX, float screenY){
        if(interactive.handleClick(screenX, screenY)){
            interactive.removeTrigger();
            return true;
        }
        return false;
    }

    public boolean checkIfTriggered(float currentArrayX, float currentArrayY){
        if(Math.abs(currentArrayX-arrayX) == 0 && Math.abs(currentArrayY-arrayY) <= 1 || Math.abs(currentArrayX-arrayX) <= 1 && Math.abs(currentArrayY-arrayY) == 0 ){
            interactive.trigger();
            return true;
        }
        return false;
    }

}
