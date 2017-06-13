package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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

    public MapSection(String identifier, TiledMap map, ArrayList<BeamableTilePair> beamableTiles){
        this.identifier = identifier;
        this.map = map;
        this.mapRenderer = new MyOrthogonalTileMapRenderer(map);
        this.beamableTiles = beamableTiles;

        properties = map.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);

        this.walkableRectangles = new ArrayList<Rectangle>();

        for(MapObject mo : map.getLayers().get("objects").getObjects()){
            if(mo instanceof RectangleMapObject){
                Rectangle re = ((RectangleMapObject)mo).getRectangle();
                walkableRectangles.add(re);
            }
        }

        DebugMessageFactory.printInfoMessage("RECTANGLES NPC:");
        for(MapObject mo : map.getLayers().get("objects_npc").getObjects()){
            if(mo instanceof RectangleMapObject){
                Rectangle re = ((RectangleMapObject)mo).getRectangle();
                DebugMessageFactory.printInfoMessage(re.toString());
            }
        }

    }
}
