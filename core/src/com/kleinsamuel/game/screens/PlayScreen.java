package com.kleinsamuel.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.GameClass;
import com.kleinsamuel.game.hud.HUD;
import com.kleinsamuel.game.hud.PopupWindow;
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
import com.kleinsamuel.game.model.entities.npcs.InteractiveNPC;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.entities.npcs.NPCData;
import com.kleinsamuel.game.model.entities.npcs.NPCFactory;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.model.items.ItemData;
import com.kleinsamuel.game.model.maps.InteractiveTile;
import com.kleinsamuel.game.model.maps.MapFactory;
import com.kleinsamuel.game.model.maps.MapSection;
import com.kleinsamuel.game.model.maps.interactive.Interactive;
import com.kleinsamuel.game.model.miscellaneous.chat.ChatFactory;
import com.kleinsamuel.game.model.miscellaneous.chat.ChatMessage;
import com.kleinsamuel.game.model.miscellaneous.chat.ChatWindowBig;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sam on 29.05.17.
 */

public class PlayScreen implements Screen{

    public static final int V_WIDTH = 1920/3;
    public static final int V_HEIGHT = 1080/3;

    public GameClass game;
    private static PlayScreen playScreen;

    public SpriteBatch batch;

    public OrthographicCamera gameCam;
    private Viewport gamePort;

    public HUD hud;
    public Bag bag;
    public Stats stats;
    public Lexicon lexicon;
    public ChatWindowBig chatWindowBig;
    public ChatFactory chatFactory;

    public MapSection currentMapSection;

    public Player player;
    public Tilemarker tilemarker;

    public PopupWindow popupWindow;

    BitmapFont font;

    public static boolean ACCEPT_INPUT = true;

    public CopyOnWriteArrayList<Animation> animations;

    public Sound button_click, drink_potion, level_up, hit_player, hit_enemy, coin_toss, cash_register, error_beep, notification;

    public PlayScreen(GameClass game){
        this.game = game;

        /* reset main menu screens */
        game.startScreen.isAlreadyCreated = false;
        game.startScreen.logInScreen.isAlreadyCreated = false;
        game.startScreen.signUpScreen.isAlreadyCreated = false;

        game.playScreen = this;
        playScreen = this;

        Gdx.input.setInputProcessor(new MyInputProcessor(this));

        batch = new SpriteBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, gameCam);

        gameCam.position.set(500, 500, 0);
        gameCam.zoom = Utils.ZOOM_FACTOR;

        player = new Player(this, new SpriteSheet(Assets.manager.get(Assets.chara_24, Texture.class), 4, 3));
        if(player.content.ID == -1) {
            player.content.ID = 1;
            player.content.NAME = game.userName;
        }

        currentMapSection = MapFactory.getMapSectionForIdentifier(player.content.mapIdentifier);
        game.items.addAll(currentMapSection.items);

        game.sendInitialInfo(player);

        tilemarker = new Tilemarker();
        animations = new CopyOnWriteArrayList<Animation>();

        chatFactory = new ChatFactory(this);

        bag = new Bag(this);
        hud = new HUD(this);
        stats = new Stats(this);
        lexicon = new Lexicon(this);
        chatWindowBig = new ChatWindowBig(this);

        font = new BitmapFont();

        button_click = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_01.wav"));
        drink_potion = Gdx.audio.newSound(Gdx.files.internal("sounds/drink_sound_48k.wav"));
        level_up = Gdx.audio.newSound(Gdx.files.internal("sounds/yey.wav"));
        hit_player = Gdx.audio.newSound(Gdx.files.internal("sounds/hit_player.wav"));
        hit_enemy = Gdx.audio.newSound(Gdx.files.internal("sounds/hit_enemy.wav"));
        coin_toss = Gdx.audio.newSound(Gdx.files.internal("sounds/coin_toss.wav"));
        cash_register = Gdx.audio.newSound(Gdx.files.internal("sounds/cha_ching.mp3"));
        error_beep = Gdx.audio.newSound(Gdx.files.internal("sounds/error_blop.wav"));
        notification = Gdx.audio.newSound(Gdx.files.internal("sounds/notification.wav"));

    }

    public static PlayScreen getPlayscreen(){
        return playScreen;
    }

    private void updateMainBars(){
        hud.healthbar.setHealth(player.content.CURRENT_HEALTH, player.content.MAX_HEALTH);
        hud.manabar.setMana(player.content.CURRENT_MANA, player.content.MAX_MANA);
        hud.experiencebar.setExperience(player.content.CURRENT_EXPERIENCE, CharacterFactory.getNeededXpForLevel(player.content.LEVEL));
    }

    private void updateNPCs(){
        for(NPC npc : game.npcs.values()){
            npc.update(player);
        }
    }

    private void updateOtherPlayers(){
        for(OtherPlayer op : game.otherPlayers.values()){
            op.update(player);
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

        for(InteractiveNPC interactiveNpc : currentMapSection.interactiveNpcs){
            interactiveNpc.update();
        }

        updateOtherPlayers();
        player.update();

        updateAnimations();
        updateMainBars();

        checkIfCameraIsInBounds();
        gameCam.update();

        currentMapSection.mapRenderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        /* clear map */
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* render map */
        currentMapSection.mapRenderer.render();
        batch.setProjectionMatrix(gameCam.combined);

        batch.begin();

        /* render images */
        tilemarker.render(batch);
        renderItems(batch);
        renderOtherPlayers(batch);
        renderNPCs(batch);
        player.render(batch);

        for(InteractiveNPC interactiveNPC : currentMapSection.interactiveNpcs){
            interactiveNPC.render(batch);
        }

        /* render names and healthbars */
        renderOtherPlayersAfter(batch);
        renderNPCsAfter(batch);
        player.renderAfter(batch);

        for(InteractiveNPC interactiveNPC : currentMapSection.interactiveNpcs){
            interactiveNPC.renderAfter(batch);
        }

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
        }else if(chatWindowBig.SHOW_CHAT){
            chatWindowBig.render(hud.batch);
        }

        for(InteractiveTile interactiveTile : currentMapSection.interactiveTiles){
            interactiveTile.interactive.render(hud.batch);
        }

        renderAnimationsHud(hud.batch);

        if(popupWindow != null){
            popupWindow.render(hud.batch);
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

    private void renderItems(SpriteBatch batch){
        for(Item item : game.items){
            item.render(batch);
        }
    }

    private void renderAnimations(SpriteBatch batch){
        for(Animation a : animations){
            if(!a.isHUD) {
                a.render(batch);
            }
        }
    }

    private void renderAnimationsHud(SpriteBatch batch){
        for(Animation a : animations){
            if(a.isHUD) {
                a.render(batch);
            }
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
        if(gameCam.position.x > (currentMapSection.mapWidth*Utils.TILEWIDTH)-(PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR){
            gameCam.position.x = (currentMapSection.mapWidth*Utils.TILEWIDTH)-(PlayScreen.V_WIDTH/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of down side */
        if(gameCam.position.y < (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR){
            gameCam.position.y = (PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR;
        }
        /* if cam is out of up side */
        if(gameCam.position.y > (currentMapSection.mapHeight*Utils.TILEHEIGHT)-(PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR){
            gameCam.position.y = (currentMapSection.mapHeight*Utils.TILEHEIGHT)-(PlayScreen.V_HEIGHT/2)*Utils.ZOOM_FACTOR;
        }
    }

    public boolean checkIfTileIsClickable(int arrayX, int arrayY){

        for(Rectangle re : currentMapSection.walkableRectangles){
            if(re.contains(arrayX*Utils.TILEWIDTH+(Utils.TILEWIDTH/2), arrayY*Utils.TILEHEIGHT+(Utils.TILEHEIGHT/2))){
                return true;
            }
        }
        return false;
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

    public void updateOtherPlayer(String id, String name, int entityX, int entityY){
        if(game.otherPlayers == null){
            game.otherPlayers = new ConcurrentHashMap<String, OtherPlayer>();
        }

        OtherPlayer toUpdate = game.otherPlayers.get(id);

        if(toUpdate != null){
            toUpdate.update(entityX, entityY, name);
        }
    }

    public void addOtherPlayer(String id, String name, int level, int entityX, int entityY, int currentHealth, int maxHealth){
        game.otherPlayers.put(id, new OtherPlayer(new SpriteSheet(Assets.manager.get(Assets.chara_24, Texture.class), 4, 3), name, level, entityX, entityY, currentHealth, maxHealth));

        DebugMessageFactory.printInfoMessage("ADD OTHER PLAYER: "+name);

        if(chatFactory != null && chatWindowBig != null) {
            chatFactory.getMessagesMap().put(name, new TreeMap<Integer, ChatMessage>());
            chatWindowBig.updateMessages();
            DebugMessageFactory.printInfoMessage("NOT NULL");
        }else{
            DebugMessageFactory.printInfoMessage("FACTORY NULL ? "+(chatFactory==null));
            DebugMessageFactory.printInfoMessage("WINDOW NULL? "+(chatWindowBig==null));
        }
    }

    public void setOtherPlayerPoint(String id, int x, int y){
        if(game.otherPlayers.containsKey(id)){
            game.otherPlayers.get(id).moveTo = new Vector3(x, y, 0);
        }
    }

    public void playerLevelUp(String id, int level) {
        OtherPlayer op = game.otherPlayers.get(id);
        if(op != null){
            op.setLevel(level);
        }
    }

    public void playerHealthUpdate(String id, int currentHealth, int maxHealth){
        OtherPlayer op = game.otherPlayers.get(id);
        if(op != null){
            op.setCurrentHealth(currentHealth);
            op.setMaxHealth(maxHealth);
        }
    }

    public void removeOtherPlayer(String id){
        chatFactory.getMessagesMap().remove(game.otherPlayers.get(id).name);
        chatWindowBig.updateMessages();
        game.otherPlayers.remove(id);
    }

    /*
            NPC STUFF
     */

    public void addNPC(int id, int npc_key, int level, float x, float y, String mapIdentifier, float speed, int current_health, int max_health){
        if(game.npcs == null){
            game.npcs = new ConcurrentHashMap<Integer, NPC>();
        }

        if (!mapIdentifier.equals(MapFactory.parseIdentifierFromDetailToSmall(player.content.mapIdentifier))) {
            return;
        }

        NPCData data = new NPCData(id, npc_key, level, x, y, speed, current_health, max_health);

        if (game.npcs.containsKey(id)) {
            game.npcs.get(id).data = data;
        } else {
            game.npcs.put(id, new NPC(new SpriteSheet(Assets.manager.get(NPCFactory.getResourceStringForId(npc_key), Texture.class), 1, 4), data));
        }
    }

    public void updateNPCPosition(int id, float x, float y){
        NPC npc = game.npcs.get(id);
        if(npc == null){
            return;
        }
        npc.data.x = x;
        npc.data.y = y;
    }

    public void updateNPCPoint(int id, float x, float y){
        NPC npc = game.npcs.get(id);
        if(npc == null){
            return;
        }
        npc.data.moveTo = new Vector3(x, y, 0);
    }

    public void damageNpc(String fromId, int toId, int amount, boolean sendToServer) {
        NPC npc = game.npcs.get(toId);
        if(npc != null) {
            npc.data.current_health -= amount;

            /* if player caused damage then update other players and server */
            if(sendToServer) {
                game.damageNpc(toId, amount);
            }
            /* if is update for damage from other player to npc then paly animation and sound */
            else{
                OtherPlayer damageCauser = game.otherPlayers.get(fromId);
                boolean left = true;
                if(damageCauser != null){
                    if(damageCauser.entityX < npc.data.x){
                        left = false;
                    }else if(damageCauser.entityY > npc.data.y){
                        left = false;
                    }
                }
                animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, npc.data.x, npc.data.y));
                animations.add(new DamageAnimation(left, true, amount, npc.data.x, npc.data.y));
                //Assets.manager.get(Assets.hit_player, Sound.class).play();
            }
        }
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
            //Assets.manager.get(Assets.hit_enemy, Sound.class).play();
        }
    }

    /*
            ITEM STUFF
     */
    public void addItem(int item_key, int x, int y, int amount, String ownerId){
        game.items.add(new Item(new ItemData(0, item_key, x, y, amount, ownerId)));
    }

    public void removeItem(int item_key, int x, int y){
        for(Item item : game.items){
            if(item.data.getItem_key() == item_key && item.data.getX() == x && item.data.getY() == y){
                game.items.remove(item);
                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        Assets.dispose();
        player.content.writeToFile();
    }
}
