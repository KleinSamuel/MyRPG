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
    public static String UNDERGROUND_TREE = "maps/underground_tree/uf_underground_tree.tmx";


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
            outputList.add(new BeamableTilePair(START_LAWN, 79, 5, UNDERGROUND_TREE, 25, 20));
            outputList.add(new BeamableTilePair(START_LAWN, 128, 31, UNDERGROUND_TREE, 63, 24));
        }else if(mapSectionIdentifier.equals(UNDERGROUND_TREE)){
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 25, 20, START_LAWN, 79, 5));
            outputList.add(new BeamableTilePair(UNDERGROUND_TREE, 62, 24, START_LAWN, 128, 30));
        }
        return outputList;
    }

    public static ArrayList<InteractiveTile> getInteractiveTilesForIdentifier(String mapSectionIdentifier){
        ArrayList<InteractiveTile> outputList = new ArrayList<InteractiveTile>();
        if(mapSectionIdentifier.equals(START_HOUSE)){
            outputList.add(new InteractiveTile(5, 20, new MessageInteraction(1000, 0, 1)));
            outputList.add(new InteractiveTile(4, 20, new ConfirmationInteraction(2)));
        }else if(mapSectionIdentifier.equals(START_LAWN)){
            outputList.add(new InteractiveTile(130, 26, new MerchantInteraction(0)));
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
        if(mapIdentifierDetail.equals(UNDERGROUND_TREE)){
            return "underground_tree";
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
        if(mapIdentifierSmall.equals("underground_tree")){
            return UNDERGROUND_TREE;
        }
        return null;
    }

}
