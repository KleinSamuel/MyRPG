package com.kleinsamuel.game.model.maps;

/**
 * Some tiles are doors to other areas such as different regions in a map section or different sections.
 * When a player steps on these tiles he get "beamed" to another tile.
 *
 * Created by sam on 07.06.17.
 */

public class BeamableTile {

    public String mapIdentifier;
    public int arrayX;
    public int arrayY;

    public BeamableTile(String mapIdentifier, int arrayX, int arrayY){
        this.mapIdentifier = mapIdentifier;
        this.arrayX = arrayX;
        this.arrayY = arrayY;
    }

    public boolean compare(String ident, int arrayX, int arrayY){
        if(mapIdentifier.equals(ident) && this.arrayX == arrayX && this.arrayY == arrayY){
            return true;
        }
        return false;
    }

}
