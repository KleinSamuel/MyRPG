package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

/**
 * Created by sam on 07.06.17.
 */

public class MapFactory {

    private static TmxMapLoader mapLoader = new TmxMapLoader();

    /* left stairs: x=12,y=22 ; right stairs: x=50, y=21 ; door: x=48,49 y=8 */
    public static String START_HOUSE = "maps/start_house/uf_start_house.tmx";
    /* house exit: x=11 ; y=21 */
    public static String START_LAWN = "maps/start_lawn/uf_start_lawn.tmx";

    public static MapSection getMapSectionForIdentifier(String mapSectionIdentifier){
        return new MapSection(mapSectionIdentifier, mapLoader.load(mapSectionIdentifier), getBeamableTilesForIdentfier(mapSectionIdentifier));
    }

    public static ArrayList<BeamableTilePair> getBeamableTilesForIdentfier(String mapSectionIdentifier){
        ArrayList<BeamableTilePair> outputList = new ArrayList<BeamableTilePair>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new BeamableTilePair(START_HOUSE, 12, 22, START_HOUSE, 50, 21));
            outputList.add(new BeamableTilePair(START_HOUSE, 50, 21, START_HOUSE, 12, 22));
            outputList.add(new BeamableTilePair(START_HOUSE, 48, 8, START_LAWN, 11, 20));
        }else if(mapSectionIdentifier.equals(START_LAWN)){
            //outputList.add(new BeamableTile(48, 8, 11, 21));
        }
        return outputList;
    }

}
