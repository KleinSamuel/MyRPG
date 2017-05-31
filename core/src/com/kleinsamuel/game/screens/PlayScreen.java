package com.kleinsamuel.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.GameClass;
import com.kleinsamuel.game.hud.Bag;
import com.kleinsamuel.game.hud.HUD;
import com.kleinsamuel.game.hud.Tilemarker;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.pathfinding.MapRepresentation;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by sam on 29.05.17.
 */

public class PlayScreen implements Screen{

    public static final int V_WIDTH = 1920/2;
    public static final int V_HEIGHT = 1080/2;

    public GameClass game;

    public SpriteBatch batch;

    public OrthographicCamera gameCam;
    private Viewport gamePort;

    public HUD hud;
    public Bag bag;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    public MapRepresentation mapRepresentation;

    int mapPixelWidth;
    int mapPixelHeight;

    public Player player;
    public Tilemarker tilemarker;

    public HashMap<String, OtherPlayer> otherPlayers;

    BitmapFont font;

    private boolean wasClickedDown = false;

    public PlayScreen(GameClass game){
        this.game = game;

        game.playScreen = this;

        Gdx.input.setInputProcessor(new MyInputProcessor(this));

        batch = new SpriteBatch();

        Assets.load();
        Assets.manager.finishLoading();
        Assets.manager.update();

        gameCam = new OrthographicCamera();
        //gamePort = new ScreenViewport(gameCam);
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, gameCam);

        hud = new HUD();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("testmap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        mapRepresentation = new MapRepresentation(map, new HashSet<Integer>(Arrays.asList(301)));

        MapProperties prop = map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        DebugMessageFactory.printInfoMessage("MAP SIZE: "+mapPixelWidth+"-"+mapPixelHeight);

        gameCam.position.set(500, 500, 0);
        gameCam.zoom = Utils.ZOOM_FACTOR;

        player = new Player(this, 256, 1280, new SpriteSheet(Assets.manager.get(Assets.playerSprite, Texture.class), 4, 3));
        game.sendInitialInfo(player);
        tilemarker = new Tilemarker();
        bag = new Bag(this);

        otherPlayers = new HashMap<String, OtherPlayer>();

        font = new BitmapFont();

        DebugMessageFactory.printInfoMessage("SCREEN SIZE: "+Gdx.graphics.getWidth()+":"+Gdx.graphics.getHeight());

    }

    public void update(float delta){
        //handleInput(delta);

        tilemarker.update();
        player.update();
        game.updateServer_Position(delta, player);
        hud.healthbar.setHealth(player.content.current_health, player.content.health);
        hud.manabar.setMana(player.content.current_mana, player.content.mana);

        checkIfCameraIsInBounds();
        gameCam.update();

        mapRenderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();
        batch.setProjectionMatrix(gameCam.combined);

        batch.begin();

        /* render images */
        tilemarker.render(batch);
        player.render(batch);
        renderOtherPlayers(batch);

        /* render names and healthbars */
        player.renderAfter(batch);
        renderOtherPlayersAfter(batch);

        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        hud.batch.begin();
        hud.render();
        if(bag.DRAW_BAG){
            bag.render(hud.batch);
        }
        hud.batch.end();

    }

    private void renderOtherPlayers(SpriteBatch batch){
        for(OtherPlayer op : otherPlayers.values()){
            op.render(batch);
        }
    }

    private void renderOtherPlayersAfter(SpriteBatch batch){
        for(OtherPlayer op : otherPlayers.values()){
            op.renderAfter(batch);
        }
    }

    /**
     * Check if camera is out of bounds of map
     */
    private void checkIfCameraIsInBounds(){
        /* if cam is out of left side */
        if(gameCam.position.x < (PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR){
            gameCam.position.x = (PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of right side */
        /*if(gameCam.position.x > mapPixelWidth-(Gdx.graphics.getWidth()/2)){
            gameCam.position.x = mapPixelWidth-(Gdx.graphics.getWidth()/2);
        }*/
        /* if cam is out of down side */
        if(gameCam.position.y < (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR){
            gameCam.position.y = (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of up side */
        /*if(gameCam.position.y > mapPixelHeight-(Gdx.graphics.getHeight()/2)){
            gameCam.position.y = mapPixelHeight-(Gdx.graphics.getHeight()/2);
        }*/
    }

    public boolean checkIfTileIsClickable(int arrayX, int arrayY){
        return mapRepresentation.walkableTiles.contains(mapRepresentation.map2D[arrayX][arrayY]);
    }

    public void addOtherPlayer(String id){
        DebugMessageFactory.printNormalMessage("ADDED NEW PLAYER WITH ID: "+id);
        otherPlayers.put(id, new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.playerSprite, Texture.class), 4, 3), "Unknown", 256, 1280));
    }

    public void addOtherPlayer(String id, String name, int entityX, int entityY, int xMove, int yMove, int xPos){

        if(otherPlayers == null){
            otherPlayers = new HashMap<String, OtherPlayer>();
        }

        OtherPlayer op;

        if(otherPlayers.containsKey(id)){
            op = otherPlayers.get(id);
        }else{
            op = new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.playerSprite, Texture.class), 4, 3), name, entityX, entityY);
            this.otherPlayers.put(id, op);
        }
        op.update(entityX, entityY, name, xMove, yMove, xPos);
    }

    public void removeOtherPlayer(String id){
        DebugMessageFactory.printNormalMessage("REMOVED PLAYER WITH ID: "+id);
        otherPlayers.remove(id);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {
        DebugMessageFactory.printNormalMessage("PAUSE PLAY CLASS");
    }

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        DebugMessageFactory.printNormalMessage("DISPOSE PLAY CLASS");
        batch.dispose();
        player.content.writeToFile();
    }
}
