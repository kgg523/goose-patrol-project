package com.charvas.main;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapHelper {
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TiledMapHelper(GameScreen inputGameScreen) {
        gameScreen = inputGameScreen;
    }

    public OrthogonalTiledMapRenderer setUpMap() {
        tiledMap = new TmxMapLoader().load("map.tmx");
        parseMapObjects(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
            }
        }
    }

}
