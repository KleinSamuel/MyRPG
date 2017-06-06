package com.kleinsamuel.game.model.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.HashSet;

/**
 * The main map is represented by a lot of smaller map section which consist of different tilesets
 * which causes the walkable tiles to change. Therefore this information must be stored.
 *
 * Created by sam on 07.06.17.
 */

public class MapSection {

    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;

    /* integer representation of tiles which can not be walked on */
    public HashSet<Integer> notWalkableTiles;

    /* integer representation of all tiles in all layers */
    public int[][][] map3D;

    public MapSection(TiledMap map, HashSet<Integer> notWalkableTiles){
        this.map = map;
        this.notWalkableTiles = notWalkableTiles;
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        computeMap3D();
    }

    private void computeMap3D(){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int width = tileLayer.getWidth();
        int height = tileLayer.getHeight();

        map3D = new int[map.getLayers().getCount()][width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int layer = 0; layer < map.getLayers().getCount(); layer++){
                    TiledMapTileLayer tmpLayer = (TiledMapTileLayer) map.getLayers().get(layer);
                    if(tmpLayer != null){
                        TiledMapTileLayer.Cell tmpCell = tmpLayer.getCell(i, j);
                        if(tmpCell != null){
                            map3D[layer][i][j] = tmpCell.getTile().getId();
                        }
                    }
                }
            }
        }
    }

}
