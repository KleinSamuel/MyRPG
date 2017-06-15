package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.model.items.ItemData;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.model.maps.interactive.MessageInteraction;
import com.kleinsamuel.game.util.Utils;

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
        return new MapSection(mapSectionIdentifier, mapLoader.load(mapSectionIdentifier), getBeamableTilesForIdentfier(mapSectionIdentifier), getInteractiveTilesForIdentifier(mapSectionIdentifier), getItemsForIdentifier(mapSectionIdentifier));
    }

    public static ArrayList<BeamableTilePair> getBeamableTilesForIdentfier(String mapSectionIdentifier){
        ArrayList<BeamableTilePair> outputList = new ArrayList<BeamableTilePair>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new BeamableTilePair(START_HOUSE, 11, 16, START_HOUSE, 32, 15));
            outputList.add(new BeamableTilePair(START_HOUSE, 32, 15, START_HOUSE, 11, 16));
            outputList.add(new BeamableTilePair(START_HOUSE, 30, 3, START_LAWN, 21, 17));
            outputList.add(new BeamableTilePair(START_HOUSE, 31, 3, START_LAWN, 21, 17));
        }else if(mapSectionIdentifier.equals(START_LAWN)){
            //outputList.add(new BeamableTile(48, 8, 11, 21));
        }
        return outputList;
    }

    public static ArrayList<InteractiveTile> getInteractiveTilesForIdentifier(String mapSectionIdentifier){
        ArrayList<InteractiveTile> outputList = new ArrayList<InteractiveTile>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new InteractiveTile(5, 20, new MessageInteraction(1000, 0, 1)));
        }else if(mapSectionIdentifier.equals(START_LAWN)){

        }
        return outputList;
    }

    public static ArrayList<Item> getItemsForIdentifier(String mapSectionIdentifier){
        ArrayList<Item> outputList = new ArrayList<Item>();

        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new Item(new ItemData(1000, 1000, 5* Utils.TILEWIDTH, 5*Utils.TILEHEIGHT, 1, "")));
        }else if(mapSectionIdentifier.equals(START_LAWN)){

        }

        return outputList;
    }

    public static String parseIdentifierFromDetailToSmall(String mapIdentifierDetail){
        if(mapIdentifierDetail.equals(START_HOUSE)){
            return "start_house";
        }
        if(mapIdentifierDetail.equals(START_LAWN)){
            return "start_lawn";
        }
        return null;
    }

    public static String parseIdentifierFromSmallToDetail(String mapIdentifierSmall){
        if(mapIdentifierSmall.equals("start_house")){
            return START_HOUSE;
        }
        if(mapIdentifierSmall.equals("start_lawn")){
            return START_LAWN;
        }
        return null;
    }

}
