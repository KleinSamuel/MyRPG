package com.kleinsamuel.game.model.pathfinding;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashSet;

/**
 * Created by sam on 30.05.17.
 */

public class MapRepresentation {

    public int[][] map2D;
    public HashSet<Integer> walkableTiles;

    public MapRepresentation(TiledMap map, HashSet<Integer> walkableTiles){

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int width = tileLayer.getWidth();
        int height = tileLayer.getHeight();

        map2D = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map2D[i][j] = tileLayer.getCell(i, j).getTile().getId();
            }
        }

        this.walkableTiles = walkableTiles;

    }

}
