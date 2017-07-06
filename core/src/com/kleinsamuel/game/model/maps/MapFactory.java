package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.npcs.InteractiveNPC;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.entities.npcs.NPCData;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.model.items.ItemData;
import com.kleinsamuel.game.model.items.ItemFactory;
import com.kleinsamuel.game.model.maps.interactive.ConfirmationInteraction;
import com.kleinsamuel.game.model.maps.interactive.MerchantInteraction;
import com.kleinsamuel.game.model.maps.interactive.MessageInteraction;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

import java.util.ArrayList;

/**
 * Created by sam on 07.06.17.
 */

public class MapFactory {

    private static TmxMapLoader mapLoader = new TmxMapLoader();

    public static String START_HOUSE = "maps/start_house/uf_start_house.tmx";
    public static String START_LAWN = "maps/start_lawn/uf_start_lawn.tmx";
    public static String START_FOREST = "maps/start_forest/uf_start_forest.tmx";
    public static String START_RIVER = "maps/start_river/uf_start_river.tmx";

    public static String TOWN_1 = "maps/town_1/uf_town_1.tmx";

    public static String UNDERGROUND_TREE = "maps/underground_tree/uf_underground_tree.tmx";

    public static String DUNGEON_FOREST_START = "maps/dungeon_forest_start/uf_dungeon_forest_start.tmx";
    public static String DUNGEON_FOREST_START_UG_1 = "maps/dungeon_forest_start/uf_dungeon_forest_start_ug_1.tmx";
    public static String DUNGEON_RIVER_START_UG_1 = "maps/dungeon_river_start/uf_dungeon_river_start_ug_1.tmx";
    public static String DUNGEON_TOWN_1 = "maps/dungeon_town_1/uf_dungeon_town_1.tmx";

    public static MapSection getMapSectionForIdentifier(String mapSectionIdentifier){
        return new MapSection(mapSectionIdentifier, mapLoader.load(mapSectionIdentifier), getBeamableTilesForIdentfier(mapSectionIdentifier), getInteractiveTilesForIdentifier(mapSectionIdentifier), getItemsForIdentifier(mapSectionIdentifier), getInteractiveNpcsForIdentifier(mapSectionIdentifier));
    }

    public static ArrayList<BeamableTilePair> getBeamableTilesForIdentfier(String mapSectionIdentifier){
        ArrayList<BeamableTilePair> outputList = new ArrayList<BeamableTilePair>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new BeamableTilePair(START_HOUSE, 11, 16, START_HOUSE, 32, 15));
            outputList.add(new BeamableTilePair(START_HOUSE, 32, 15, START_HOUSE, 11, 16));
            outputList.add(new BeamableTilePair(START_HOUSE, 30, 3, START_LAWN, 21, 17));
            outputList.add(new BeamableTilePair(START_HOUSE, 31, 3, START_LAWN, 21, 17));
        }else if(mapSectionIdentifier.equals(START_LAWN)){
            outputList.add(new BeamableTilePair(START_LAWN, 79, 5, UNDERGROUND_TREE, 39, 20));
            outputList.add(new BeamableTilePair(START_LAWN, 128, 31, UNDERGROUND_TREE, 78, 24));
            outputList.add(new BeamableTilePair(START_LAWN, 116, 3, START_FOREST, 86, 65));
            outputList.add(new BeamableTilePair(START_LAWN, 117, 3, START_FOREST, 87, 65));
            outputList.add(new BeamableTilePair(START_LAWN, 138, 22, START_RIVER, 4, 14));
            outputList.add(new BeamableTilePair(START_LAWN, 138, 21, START_RIVER, 4, 13));
        }else if(mapSectionIdentifier.equals(START_FOREST)){
            outputList.add(new BeamableTilePair(START_FOREST, 86, 66, START_LAWN, 116, 4));
            outputList.add(new BeamableTilePair(START_FOREST, 87, 66, START_LAWN, 117, 4));
            outputList.add(new BeamableTilePair(START_FOREST, 6, 23, DUNGEON_FOREST_START, 6, 34));
            outputList.add(new BeamableTilePair(START_FOREST, 7, 23, DUNGEON_FOREST_START, 6, 34));
        }else if(mapSectionIdentifier.equals(START_RIVER)) {
            outputList.add(new BeamableTilePair(START_RIVER, 3, 14, START_LAWN, 137, 22));
            outputList.add(new BeamableTilePair(START_RIVER, 3, 13, START_LAWN, 137, 21));
            outputList.add(new BeamableTilePair(START_RIVER, 70, 26, TOWN_1, 2, 26));
            outputList.add(new BeamableTilePair(START_RIVER, 70, 25, TOWN_1, 2, 25));
        }else if(mapSectionIdentifier.equals(TOWN_1)){
            outputList.add(new BeamableTilePair(TOWN_1, 1, 26, START_RIVER, 69, 26));
            outputList.add(new BeamableTilePair(TOWN_1, 1, 25, START_RIVER, 69, 25));
            outputList.add(new BeamableTilePair(TOWN_1, 24, 31, DUNGEON_TOWN_1, 45, 25));
        }else if(mapSectionIdentifier.equals(UNDERGROUND_TREE)){
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 40, 20, START_LAWN, 79, 6));
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 77, 24, START_LAWN, 128, 30));
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 60, 0, DUNGEON_FOREST_START, 71, 35));
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 61, 0, DUNGEON_FOREST_START, 72, 35));
        }else if(mapSectionIdentifier.equals(DUNGEON_FOREST_START)){
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 71, 36, UNDERGROUND_TREE, 60, 1));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 72, 36, UNDERGROUND_TREE, 61, 1));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 5, 34, START_FOREST, 5, 23));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 5, 35, START_FOREST, 5, 23));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 40, 22, DUNGEON_FOREST_START_UG_1, 45, 25));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START, 40, 21, DUNGEON_FOREST_START_UG_1, 45, 24));
        }else if(mapSectionIdentifier.equals(DUNGEON_FOREST_START_UG_1)){
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START_UG_1, 44, 25, DUNGEON_FOREST_START, 39, 22));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START_UG_1, 44, 24, DUNGEON_FOREST_START, 39, 21));
            outputList.add(new BeamableTilePair(DUNGEON_FOREST_START_UG_1, 76, 25, DUNGEON_RIVER_START_UG_1, 3, 25));
        }else if(mapSectionIdentifier.equals(DUNGEON_RIVER_START_UG_1)){
            outputList.add(new BeamableTilePair(DUNGEON_RIVER_START_UG_1, 2, 25, DUNGEON_FOREST_START_UG_1, 75, 25));
            outputList.add(new BeamableTilePair(DUNGEON_RIVER_START_UG_1, 76, 25, DUNGEON_TOWN_1, 3, 25));
        }else if(mapSectionIdentifier.equals(DUNGEON_TOWN_1)){
            outputList.add(new BeamableTilePair(DUNGEON_TOWN_1, 2, 25, DUNGEON_RIVER_START_UG_1, 75, 25));
            outputList.add(new BeamableTilePair(DUNGEON_TOWN_1, 44, 25, TOWN_1, 24, 30));
            outputList.add(new BeamableTilePair(DUNGEON_TOWN_1, 44, 24, TOWN_1, 24, 30));
        }
        return outputList;
    }

    public static ArrayList<InteractiveTile> getInteractiveTilesForIdentifier(String mapSectionIdentifier){
        ArrayList<InteractiveTile> outputList = new ArrayList<InteractiveTile>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new InteractiveTile(5, 20, new MessageInteraction(1000, 0, 1)));
            outputList.add(new InteractiveTile(4, 20, new ConfirmationInteraction(2)));
        }else if(mapSectionIdentifier.equals(START_LAWN)){
            outputList.add(new InteractiveTile(130, 26, new MerchantInteraction(PlayScreen.getPlayscreen(), 0)));
        }else if(mapSectionIdentifier.equals(UNDERGROUND_TREE)){

        }
        return outputList;
    }

    public static ArrayList<Item> getItemsForIdentifier(String mapSectionIdentifier){
        ArrayList<Item> outputList = new ArrayList<Item>();

        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new Item(new ItemData(1000, 1000, 5* Utils.TILEWIDTH, 5*Utils.TILEHEIGHT, 1, "")));
        }else if(mapSectionIdentifier.equals(START_LAWN)){

        }else if(mapSectionIdentifier.equals(UNDERGROUND_TREE)){

        }

        return outputList;
    }

    public static ArrayList<InteractiveNPC> getInteractiveNpcsForIdentifier(String mapSectionIdentifier){
        ArrayList<InteractiveNPC> outputList = new ArrayList<InteractiveNPC>();

        if(mapSectionIdentifier.equals(START_LAWN)){
            outputList.add(new InteractiveNPC(new SpriteSheet(Assets.manager.get(Assets.merchant_1, Texture.class), 1, 4), "MERCHANT", 130, 26).setWidthAndHeigth(Utils.TILEWIDTH+10, Utils.TILEHEIGHT+10));
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
        if(mapIdentifierDetail.equals(START_FOREST)){
            return "start_forest";
        }
        if(mapIdentifierDetail.equals(START_RIVER)){
            return "start_river";
        }
        if(mapIdentifierDetail.equals(TOWN_1)){
            return "town_1";
        }
        if(mapIdentifierDetail.equals(UNDERGROUND_TREE)){
            return "underground_tree";
        }
        if(mapIdentifierDetail.equals(DUNGEON_FOREST_START)){
            return "dungeon_forest_start";
        }
        if(mapIdentifierDetail.equals(DUNGEON_FOREST_START_UG_1)){
            return "dungeon_forest_start_ug_1";
        }
        if(mapIdentifierDetail.equals(DUNGEON_RIVER_START_UG_1)){
            return "dungeon_river_start_ug_1";
        }
        if(mapIdentifierDetail.equals(DUNGEON_TOWN_1)){
            return "dungeon_town_1";
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
        if(mapIdentifierSmall.equals("start_forest")){
            return START_FOREST;
        }
        if(mapIdentifierSmall.equals("start_river")){
            return START_RIVER;
        }
        if(mapIdentifierSmall.equals("town_1")){
            return TOWN_1;
        }
        if(mapIdentifierSmall.equals("underground_tree")){
            return UNDERGROUND_TREE;
        }
        if(mapIdentifierSmall.equals("dungeon_forest_start")){
            return DUNGEON_FOREST_START;
        }
        if(mapIdentifierSmall.equals("dungeon_forest_start_ug_1")){
            return DUNGEON_FOREST_START_UG_1;
        }
        if(mapIdentifierSmall.equals("dungeon_river_start_ug_1")){
            return DUNGEON_RIVER_START_UG_1;
        }
        if(mapIdentifierSmall.equals("dungeon_town_1")){
            return DUNGEON_TOWN_1;
        }
        return null;
    }

}
