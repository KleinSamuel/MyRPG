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
import com.kleinsamuel.game.hud.bag.Bag;
import com.kleinsamuel.game.hud.HUD;
import com.kleinsamuel.game.hud.Tilemarker;
import com.kleinsamuel.game.hud.lexicon.Lexicon;
import com.kleinsamuel.game.hud.stats.Stats;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.entities.npcs.NPCData;
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

    public static final int V_WIDTH = 1920/3;
    public static final int V_HEIGHT = 1080/3;

    public GameClass game;

    public SpriteBatch batch;

    public OrthographicCamera gameCam;
    private Viewport gamePort;

    public HUD hud;
    public Bag bag;
    public Stats stats;
    public Lexicon lexicon;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    public MapRepresentation mapRepresentation;

    int mapPixelWidth;
    int mapPixelHeight;

    public Player player;
    public Tilemarker tilemarker;

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

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map_test/uf_map_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        mapRepresentation = new MapRepresentation(map, new HashSet<Integer>(Arrays.asList(-1)));

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
        hud = new HUD();
        stats = new Stats(this);
        lexicon = new Lexicon(this);

        NPCData data = new NPCData(1, 1, 48, 48, 1, 10, 10);
        NPC npc = new NPC(48, 48, new SpriteSheet(Assets.manager.get(Assets.bug_small, Texture.class), 1, 4), data);
        game.npcs.put(1, npc);

        NPCData data2 = new NPCData(2, 2, 72, 48, 1, 10, 10);
        NPC npc2 = new NPC(48, 48, new SpriteSheet(Assets.manager.get(Assets.bird_crow, Texture.class), 1, 4), data2);
        game.npcs.put(2, npc2);

        NPCData data3 = new NPCData(3, 3, 48, 72, 1, 10, 10);
        NPC npc3 = new NPC(48, 48, new SpriteSheet(Assets.manager.get(Assets.elemental_water, Texture.class), 1, 4), data3);
        npc3.setSize(34, 34);
        game.npcs.put(3, npc3);

        NPCData data4 = new NPCData(4, 4, 168, 72, 1, 10, 10);
        NPC npc4 = new NPC(48, 48, new SpriteSheet(Assets.manager.get(Assets.eloa_war, Texture.class), 1, 4), data4);
        npc4.setSize(42, 42);
        npc4.setAnimationSpeed(300);
        game.npcs.put(4, npc4);

        game.otherPlayers = new HashMap<String, OtherPlayer>();

        font = new BitmapFont();

        DebugMessageFactory.printInfoMessage("SCREEN SIZE: "+Gdx.graphics.getWidth()+":"+Gdx.graphics.getHeight());

    }

    private void updateMainBars(){
        hud.healthbar.setHealth(player.content.current_health, player.content.health);
        hud.manabar.setMana(player.content.current_mana, player.content.mana);
    }

    private void updateNPCs(){
        for(NPC npc : game.npcs.values()){
            npc.update();
        }
    }

    public void update(float delta){

        tilemarker.update();
        updateNPCs();
        player.update();
        game.updateServer_Position(delta, player);

        updateMainBars();

        checkIfCameraIsInBounds();
        gameCam.update();

        mapRenderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        /* clear map */
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* render map */
        mapRenderer.render();
        batch.setProjectionMatrix(gameCam.combined);

        batch.begin();

        /* render images */
        tilemarker.render(batch);
        renderOtherPlayers(batch);
        renderNPCs(batch);
        player.render(batch);

        /* render names and healthbars */
        renderOtherPlayersAfter(batch);
        renderNPCsAfter(batch);
        player.renderAfter(batch);
        batch.end();

        /* render HUD */
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        hud.batch.begin();
        hud.render();
        if(bag.SHOW_BAG){
            bag.render(hud.batch);
        }else if(stats.SHOW_STATS){
            stats.render(hud.batch);
        }else if(lexicon.SHOW_LEXICON){
            lexicon.render(hud.batch);
        }
        hud.batch.end();

    }

    private void renderOtherPlayers(SpriteBatch batch){
        for(OtherPlayer op : game.otherPlayers.values()){
            op.render(batch);
        }
    }

    private void renderOtherPlayersAfter(SpriteBatch batch){
        for(OtherPlayer op : game.otherPlayers.values()){
            op.renderAfter(batch);
        }
    }

    private void renderNPCs(SpriteBatch batch){
        for(NPC npc : game.npcs.values()){
            npc.render(batch);
        }
    }

    private void renderNPCsAfter(SpriteBatch batch){
        for(NPC npc : game.npcs.values()){
            npc.renderAfter(batch);
        }
    }

    /**
     * Check if camera is out of bounds of map
     */
    private void checkIfCameraIsInBounds(){
        /* if cam is out of left side */
        if(gameCam.position.x < (PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR) {
            gameCam.position.x = (PlayScreen.V_WIDTH / 2) * Utils.ZOOM_FACTOR;
        }
        /* if cam is out of right side */
        if(gameCam.position.x > (mapRepresentation.map2D.length*Utils.TILEWIDTH)-(PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR){
            gameCam.position.x = (mapRepresentation.map2D.length*Utils.TILEWIDTH)-(PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of down side */
        if(gameCam.position.y < (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR){
            gameCam.position.y = (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of up side */
        if(gameCam.position.y > (mapRepresentation.map2D[0].length*Utils.TILEHEIGHT)-(PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR){
            gameCam.position.y = (mapRepresentation.map2D[0].length*Utils.TILEHEIGHT)-(PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR;
        }
    }

    public boolean checkIfTileIsClickable(int arrayX, int arrayY){
        return !mapRepresentation.walkableTiles.contains(mapRepresentation.map2D[arrayX][arrayY]);
    }

    public void addOtherPlayer(String id){
        DebugMessageFactory.printNormalMessage("ADDED NEW PLAYER WITH ID: "+id);
        game.otherPlayers.put(id, new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.playerSprite, Texture.class), 4, 3), "Unknown", 256, 1280));
    }

    public void addOtherPlayer(String id, String name, int entityX, int entityY, int xMove, int yMove, int xPos){

        if(game.otherPlayers == null){
            game.otherPlayers = new HashMap<String, OtherPlayer>();
        }

        OtherPlayer op;

        if(game.otherPlayers.containsKey(id)){
            op = game.otherPlayers.get(id);
        }else{
            op = new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.playerSprite, Texture.class), 4, 3), name, entityX, entityY);
            game.otherPlayers.put(id, op);
        }
        op.update(entityX, entityY, name, xMove, yMove, xPos);
    }

    public void removeOtherPlayer(String id){
        DebugMessageFactory.printNormalMessage("REMOVED PLAYER WITH ID: "+id);
        game.otherPlayers.remove(id);
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
