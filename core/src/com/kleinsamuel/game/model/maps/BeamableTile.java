package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.math.Vector3;

/**
 * Some tiles are doors to other areas such as different regions in a map section or different sections.
 * When a player steps on these tiles he get "beamed" to another tile.
 *
 * Created by sam on 07.06.17.
 */

public class BeamableTile {

    public int arrayX_1;
    public int arrayY_1;

    public int arrayX_2;
    public int arrayY_2;

    public BeamableTile(int arrayX_1, int arrayY_1, int arrayX_2, int arrayY_2){
        this.arrayX_1 = arrayX_1;
        this.arrayY_1 = arrayY_1;
        this.arrayX_2 = arrayX_2;
        this.arrayY_2 = arrayY_2;
    }

    public Vector3 getComplement(int arrayX, int arrayY){
        if(arrayX == arrayX_1 && arrayY == arrayY_1){
            return new Vector3(arrayX_2, arrayY_2, 0);
        }
        if(arrayX == arrayX_2 && arrayY == arrayY_2){
            return new Vector3(arrayX_1, arrayY_1, 0);
        }
        return null;
    }

}
