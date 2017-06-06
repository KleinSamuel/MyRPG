package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by sam on 07.06.17.
 */

public class MapFactory {

    private static TmxMapLoader mapLoader = new TmxMapLoader();

    /*
        left stairs: x=12,y=22 ; right stairs: x=50, y=21 ; door: x=48,49 y=8
     */
    public static String HOUSE_START = "maps/house_start/uf_map_starthaus.tmx";

    public static MapSection getMapSectionForIdentifier(String mapSectionIdentifier){
        return new MapSection(mapLoader.load(mapSectionIdentifier), getNotWalkableTilesForIdentifier(mapSectionIdentifier));
    }

    public static HashSet<Integer> getNotWalkableTilesForIdentifier(String mapSectionIdentifier){
        if(mapSectionIdentifier.equals(HOUSE_START)){
            return new HashSet<Integer>(Arrays.asList(15,22,92,95,96,104,14,23,110,81,82,36,37,38,57,58,59,44,45,46,65,66,67,164,127,35,33,34,143,76,128,120,175,165,41,42,43,203,196,197,198,199,40,201,32,84,39,93,94,95,89,100,92,47,127,112,202,203,204,196,198,90,791,194));
        }
        return null;
    }

}
