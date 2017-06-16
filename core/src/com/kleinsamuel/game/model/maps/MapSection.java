package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kleinsamuel.game.model.entities.npcs.InteractiveNPC;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.util.DebugMessageFactory;

import java.util.ArrayList;

/**
 * The main map is represented by a lot of smaller map section which consist of different tilesets
 * which causes the walkable tiles to change. Therefore this information must be stored.
 *
 * Created by sam on 07.06.17.
 */

public class MapSection {

    public String identifier;

    public TiledMap map;
    public MyOrthogonalTileMapRenderer mapRenderer;

    public MapProperties properties;
    public int mapWidth;
    public int mapHeight;

    public ArrayList<Rectangle> walkableRectangles;

    public ArrayList<BeamableTilePair> beamableTiles;
    public ArrayList<InteractiveTile> interactiveTiles;
    public ArrayList<Item> items;
    public ArrayList<InteractiveNPC> interactiveNpcs;

    public MapSection(String identifier, TiledMap map, ArrayList<BeamableTilePair> beamableTiles, ArrayList<InteractiveTile> interactiveTiles, ArrayList<Item> items, ArrayList<InteractiveNPC> interactiveNpcs){
        this.identifier = identifier;
        this.map = map;
        this.mapRenderer = new MyOrthogonalTileMapRenderer(map);
        this.beamableTiles = beamableTiles;
        this.interactiveTiles = interactiveTiles;
        this.items = items;
        this.interactiveNpcs = interactiveNpcs;

        properties = map.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);

        this.walkableRectangles = new ArrayList<Rectangle>();

        for(MapObject mo : map.getLayers().get("objects_players").getObjects()){
            if(mo instanceof RectangleMapObject){
                Rectangle re = ((RectangleMapObject)mo).getRectangle();
                walkableRectangles.add(re);
            }
        }

        DebugMessageFactory.printInfoMessage("RECTANGLES NPC:");
        MapLayer npcLayer = map.getLayers().get("objects_npcs");
        if(npcLayer != null) {
            for (MapObject mo : map.getLayers().get("objects_npcs").getObjects()) {
                if (mo instanceof RectangleMapObject) {
                    Rectangle re = ((RectangleMapObject) mo).getRectangle();
                    DebugMessageFactory.printInfoMessage(re.toString());
                }
            }
        }

    }
}
