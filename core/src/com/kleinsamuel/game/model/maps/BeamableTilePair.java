package com.kleinsamuel.game.model.maps;

/**
 * Created by sam on 07.06.17.
 */

public class BeamableTilePair {

    private BeamableTile from;
    private BeamableTile to;

    public BeamableTilePair(String from_mapIdentifier, int from_arrayX, int from_arrayY, String to_mapIdentifier, int to_arrayX, int to_arrayY){

        from = new BeamableTile(from_mapIdentifier, from_arrayX, from_arrayY);
        to = new BeamableTile(to_mapIdentifier, to_arrayX, to_arrayY);

    }

    public BeamableTile getComplement(String identifier, int arrayX, int arrayY){
        if(from.compare(identifier, arrayX, arrayY)){
            return to;
        }
        return null;
    }

}
