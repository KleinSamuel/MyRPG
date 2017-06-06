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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.GameClass;
import com.kleinsamuel.game.hud.HUD;
import com.kleinsamuel.game.hud.Tilemarker;
import com.kleinsamuel.game.hud.bag.Bag;
import com.kleinsamuel.game.hud.lexicon.Lexicon;
import com.kleinsamuel.game.hud.stats.Stats;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.animations.Animation;
import com.kleinsamuel.game.model.animations.AnimationEnum;
import com.kleinsamuel.game.model.animations.AnimationFactory;
import com.kleinsamuel.game.model.animations.DamageAnimation;
import com.kleinsamuel.game.model.animations.EffectAnimation;
import com.kleinsamuel.game.model.data.CharacterFactory;
import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.entities.npcs.NPCData;
import com.kleinsamuel.game.model.pathfinding.MapRepresentation;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

    //public LinkedList<Animation> animations;
    public CopyOnWriteArrayList<Animation> animations;

    public PlayScreen(GameClass game){
        this.game = game;

        game.playScreen = this;

        Gdx.input.setInputProcessor(new MyInputProcessor(this));

        batch = new SpriteBatch();

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

        player = new Player(this, new SpriteSheet(Assets.manager.get(Assets.test, Texture.class), 4, 3));
        game.sendInitialInfo(player);

        tilemarker = new Tilemarker();
        //animations = new LinkedList<Animation>();
        animations = new CopyOnWriteArrayList<Animation>();

        bag = new Bag(this);
        hud = new HUD(this);
        stats = new Stats(this);
        lexicon = new Lexicon(this);

        font = new BitmapFont();

        DebugMessageFactory.printInfoMessage("SCREEN SIZE: "+Gdx.graphics.getWidth()+":"+Gdx.graphics.getHeight());

    }

    private void updateMainBars(){
        hud.healthbar.setHealth(player.content.CURRENT_HEALTH, player.content.MAX_HEALTH);
        hud.manabar.setMana(player.content.CURRENT_MANA, player.content.MAX_MANA);
        hud.experiencebar.setExperience(player.content.CURRENT_EXPERIENCE, CharacterFactory.getNeededXpForLevel(player.content.LEVEL));
    }

    private void updateNPCs(){
        for(NPC npc : game.npcs.values()){
            npc.update();
        }
    }

    private void updateOtherPlayers(){
        for(OtherPlayer op : game.otherPlayers.values()){
            op.update();
        }
    }

    private void updateAnimations(){
        int toDelete = -1;
        int index = 0;
        for(Animation a : animations){
            a.update();
            if(a.delete){
                toDelete = index;
            }
            index++;
        }
        if(toDelete != -1){
            animations.remove(toDelete);
        }
    }

    public void update(float delta){

        tilemarker.update();

        updateNPCs();

        updateOtherPlayers();
        player.update();

        updateAnimations();
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
        renderAnimations(batch);
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

    private void renderAnimations(SpriteBatch batch){
        for(Animation a : animations){
            a.render(batch);
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

    public boolean checkIfClickedOnNPC(int arrayX, int arrayY){
        for(NPC npc : game.npcs.values()){
            Vector3 npcArrayCoord = Utils.getArrayCoordinates(npc.data.x, npc.data.y);
            if(arrayX == npcArrayCoord.x && arrayY == npcArrayCoord.y){
                DebugMessageFactory.printInfoMessage("CLICKED ON NPC!!");
                player.following = npc;
                player.follow(npcArrayCoord, new Vector3(player.content.x, player.content.y, 0));
                return true;
            }
        }
        return false;
    }

    /*
           OTHER PLAYER STUFF
     */

    public void addOtherPlayer(String id){
        DebugMessageFactory.printNormalMessage("ADDED NEW PLAYER WITH ID: "+id);
        addOtherPlayer(id, "Unknown", 0, 0, 0, 0, 0);
    }

    public void updateOtherPlayer(String id, String name, int entityX, int entityY, int xMove, int yMove, int xPos){
        if(game.otherPlayers == null){
            game.otherPlayers = new ConcurrentHashMap<String, OtherPlayer>();
        }

        OtherPlayer toUpdate = game.otherPlayers.get(id);

        if(toUpdate != null){
            toUpdate.update(entityX, entityY, name, xMove, yMove, xPos);
        }
    }

    public void addOtherPlayer(String id, String name, int entityX, int entityY, int xMove, int yMove, int xPos){
        game.otherPlayers.put(id, new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.test, Texture.class), 4, 3), name, entityX, entityY));
    }

    public void setOtherPlayerPoint(String id, int x, int y){
        if(game.otherPlayers.containsKey(id)){
            game.otherPlayers.get(id).moveTo = new Vector3(x, y, 0);
        }
    }

    public void removeOtherPlayer(String id){
        game.otherPlayers.remove(id);
    }

    /*
            NPC STUFF
     */

    public void addNPC(int id, int npc_key, int level, float x, float y, float speed, int current_health, int max_health){
        if(game.npcs == null){
            //game.npcs = new HashMap<Integer, NPC>();
            game.npcs = new ConcurrentHashMap<Integer, NPC>();
        }

        NPCData data = new NPCData(id, npc_key, level, x, y, speed, current_health, max_health);

        if(game.npcs.containsKey(id)) {
            game.npcs.get(id).data = data;
        }else{
            game.npcs.put(id, new NPC(new SpriteSheet(Assets.manager.get(Assets.elemental_water, Texture.class), 1, 4), data));
        }
    }

    public void updateNPCPosition(int id, float x, float y){
        NPC npc = game.npcs.get(id);
        if(npc == null){
            DebugMessageFactory.printErrorMessage("ERROR NPC "+id+" DOES NOT EXIST BUT WANTS TO BE POSITION UPDATED");
            return;
        }
        npc.data.x = x;
        npc.data.y = y;
    }

    public void updateNPCPoint(int id, float x, float y){
        NPC npc = game.npcs.get(id);
        if(npc == null){
            DebugMessageFactory.printErrorMessage("ERROR NPC "+id+" DOES NOT EXIST BUT WANTS TO BE POINT UPDATED");
            return;
        }
        npc.data.moveTo = new Vector3(x, y, 0);
    }

    public void damageNpc(int id, int amount) {
        game.npcs.get(id).data.current_health -= amount;
        game.damageNpc(id, amount);
    }

    public void killNpc(int id){
        game.npcs.remove(id);
        game.killNpc(id);
    }

    public void damageToOtherPlayer(String id, int amount){

        OtherPlayer op = game.otherPlayers.get(id);

        if(op != null){
            op.currentHealth -= amount;
            animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, op.entityX, op.entityY));
            animations.add(new DamageAnimation(false, true, amount, op.entityX, op.entityY));
        }
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
