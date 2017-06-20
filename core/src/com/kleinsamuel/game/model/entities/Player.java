package com.kleinsamuel.game.model.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.animations.AnimationEnum;
import com.kleinsamuel.game.model.animations.AnimationFactory;
import com.kleinsamuel.game.model.animations.DamageAnimation;
import com.kleinsamuel.game.model.animations.EffectAnimation;
import com.kleinsamuel.game.model.animations.ScreenSwitchAnimation;
import com.kleinsamuel.game.model.data.CharacterFactory;
import com.kleinsamuel.game.model.data.UserContent;
import com.kleinsamuel.game.model.data.UserMultiplier;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.model.maps.BeamableTile;
import com.kleinsamuel.game.model.maps.BeamableTilePair;
import com.kleinsamuel.game.model.maps.InteractiveTile;
import com.kleinsamuel.game.model.pathfinding.AStarPathFinder;
import com.kleinsamuel.game.model.pathfinding.Path;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.LinkedList;

/**
 * Created by sam on 29.05.17.
 */

public class Player {

    public static final float SPEED = 1.0f;

    public int xMove;
    public int yMove;

    public PlayScreen playScreen;

    public UserContent content;
    public UserMultiplier multiplier;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;

    public Path pathToWalk;
    private AStarPathFinder pathFinder;

    public Vector3 oldMoveTo;
    public Vector3 moveTo;

    public NPC following;
    public boolean isAttacking;
    public long lastAttackTimestamp;

    /* used to not catch player in beam loop */
    public boolean isAbleToBeam = true;

    private Sound walkingSound;
    private boolean isWalkingSound = false;

    public Player(PlayScreen playScreen, SpriteSheet spriteSheet){

        pathToWalk = new Path();
        xMove = 0;
        yMove = 0;
        pathFinder = new AStarPathFinder(this);

        this.playScreen = playScreen;
        this.spriteSheet = spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 1);

        loadContent();
        loadMultiplier();

        //walkingSound = Assets.manager.get(Assets.footsteps, Sound.class);

        if(content.ID == -1){
            DebugMessageFactory.printInfoMessage("FIRST STARTUP!");
        }else{
            DebugMessageFactory.printInfoMessage("NOT FIRST STARTUP!");
        }
    }

    private void loadContent() {
        this.content = UserContent.readFromFile();
    }

    private void loadMultiplier(){
        this.multiplier = UserMultiplier.readFromFile();
    }

    public void checkIfDead(){
        if(content.CURRENT_HEALTH <= 0){
            content.x = 24;
            content.y = 24;
            following = null;
            DebugMessageFactory.printInfoMessage("YOU JUST DIED!");
        }
    }

    public void setMove(Vector3 p) {
        xMove = (int)p.x;
        yMove = (int)p.y;
    }

    int op = 1;
    int slow = 0;
    public int xPos = 1;
    int oldDirX;
    int oldDirY;
    int prevDirection;
    boolean directlyAfter;

    public void move() {

        content.x += xMove * SPEED;
        content.y += yMove * SPEED;

		/* if player reached target */
		if(pathToWalk.pathPoints.size() == 0 && xMove == 0 && yMove == 0 && following == null) {
			playScreen.tilemarker.setVisible(false);
		}else {
			playScreen.tilemarker.setVisible(true);
		}

        if (slow++ >= 7) {
            if (xMove == 0 && yMove == 0) {
                slow = 7;
                setCurrentImage(0, 0, 1);
                //walkingSound.pause();
                //isWalkingSound = false;

                if(directlyAfter) {
                    directlyAfter = false;
                }

            } else {

                directlyAfter = true;

                /*if(!isWalkingSound){
                    walkingSound.loop();
                    walkingSound.play(0.4f);
                    isWalkingSound = true;
                }*/

                slow = 0;
                if (op == -1 && xPos <= 0) {
                    op = 1;
                } else if (op == 1 && xPos >= 2) {
                    op = -1;
                }
                xPos = (xPos + op);
                setCurrentImage(xMove, yMove, xPos);

                oldDirX = xMove;
                oldDirY = yMove;

            }
        }
    }

    void setCurrentImage(int x, int y, int xPos) {
        if (y == -1) {
            currentTexture = spriteSheet.getTextureRegion(0, xPos);
            prevDirection = 0;
        } else if (y == 1) {
            currentTexture = spriteSheet.getTextureRegion(3, xPos);
            prevDirection = 3;
        } else if (x == -1) {
            currentTexture = spriteSheet.getTextureRegion(1, xPos);
            prevDirection = 1;
        } else if (x == 1) {
            currentTexture = spriteSheet.getTextureRegion(2, xPos);
            prevDirection = 2;
        } else {
            currentTexture = spriteSheet.getTextureRegion(prevDirection, xPos);
        }
    }

    public void follow(Vector3 p, Vector3 oldArray) {
        createSmartPathTo(p, oldArray);

        if(pathToWalk.pathPoints.size() >= 1) {
            pathToWalk.pathPoints.removeLast();
        }
        if(pathToWalk.pathPoints.size() >= 1) {
            pathToWalk.pathPoints.removeFirst();
        }
        if (pathToWalk.pathPoints.size() >= 1) {
            pathToWalk.pathPoints.removeFirst();
        }
    }

    public void createSmartPathTo(Vector3 target, Vector3 old) {

        Vector3 adjPosTarget = target;
        Vector3 adjPosChaser = Utils.getArrayCoordinates(old.x, old.y);
        if(xMove > 0){
            adjPosChaser.x += 1;
        }
        if(yMove > 0 ){
            adjPosChaser.y += 1;
        }

        LinkedList<Vector3> path = pathFinder.findPath((int)adjPosChaser.x, (int)adjPosChaser.y, (int)adjPosTarget.x, (int)adjPosTarget.y);

        if(path == null) {
            pathToWalk.pathPoints.clear();
        }else {
            for(Vector3 p : path) {
                p.set(p.x*Utils.TILEWIDTH, p.y*Utils.TILEHEIGHT, 0);
            }
            pathToWalk.pathPoints = path;
        }
    }

    /**
     * Returns direction to next way point on path.
     *
     */
    public Vector3 walkOnPath() {

        Vector3 output = new Vector3(0, 0, 0);

        if(pathToWalk.pathPoints.size() == 0) {
            return output;
        }

        Vector3 currentWayPoint = pathToWalk.pathPoints.getLast();
        Vector3 dirs = walkToPoint(currentWayPoint);

        moveTo = currentWayPoint;
        if(moveTo != oldMoveTo){
            oldMoveTo = moveTo;
            playScreen.game.playerMovedPoint(this);
        }

        if(dirs == null) {
            pathToWalk.pathPoints.removeLast();
            return output;
        }

        return dirs;
    }

    /**
     * Return x and y directions needed to get to given point.
     * Returns null if given Point is reached
     */
    public Vector3 walkToPoint(Vector3 pointOnMap) {

        if(content.x == pointOnMap.x && content.y == pointOnMap.y) {
            return null;
        }

        int xMove = 0;
        int yMove = 0;

        if(content.x != pointOnMap.x) {
            if(content.x < pointOnMap.x) {
                xMove = 1;
            }else if(content.x > pointOnMap.x) {
                xMove = -1;
            }
            return new Vector3(xMove, yMove, 0);
        }

        if(content.y < pointOnMap.y) {
            yMove = 1;
        }else if(content.y > pointOnMap.y) {
            yMove = -1;
        }

        return new Vector3(xMove, yMove, 0);
    }

    private void addExperience(int amount){
        if(content.CURRENT_EXPERIENCE +amount >= CharacterFactory.getNeededXpForLevel(content.LEVEL)){
            levelUp();
        }else{
            content.CURRENT_EXPERIENCE += amount;
        }
    }

    private void levelUp(){
        playScreen.level_up.play();
        content.LEVEL += 1;
        content.CURRENT_EXPERIENCE = 0;
        content.MAX_HEALTH = CharacterFactory.getHealthForLevelLinear(content.LEVEL, multiplier.MULTIPLIER_HEALTH);
        content.CURRENT_HEALTH = content.MAX_HEALTH;
        content.MAX_MANA = CharacterFactory.getManaForLevelLinear(content.LEVEL, multiplier.MULTIPLIER_MANA);
        content.CURRENT_MANA = content.MAX_MANA;
        content.VALUE_ATTACK = (int)(multiplier.MULTIPLIER_ATTACK * CharacterFactory.getDamageForLevel(content.LEVEL));
        content.VALUE_DEFENSE = CharacterFactory.getDefenseForLevel(content.LEVEL);

        playScreen.game.updateServer_Level(this);
        playScreen.game.updateServer_Health(this);
    }

    public void checkIfInAttackRange(){
        if(following == null){
            isAttacking = false;
            return;
        }

        if(Math.abs(following.data.x - content.x) == 0 && Math.abs(following.data.y - content.y) == Utils.TILEHEIGHT){
            isAttacking = true;
        }else if(Math.abs(following.data.x - content.x) == Utils.TILEWIDTH && Math.abs(following.data.y - content.y) == 0){
            isAttacking = true;
        }else{
            isAttacking = false;
        }
    }

    public void attack(){
        if(isAttacking){

            /* change direction to enemy */
            if(content.y < following.data.y){
                setCurrentImage(0,1, 1);
            }else if(content.y > following.data.y){
                setCurrentImage(0, -1, 1);
            }else if(content.x < following.data.x){
                setCurrentImage(1, 0, 1);
            }else if(content.x > following.data.x){
                setCurrentImage(-1, 0, 1);
            }

            if(System.currentTimeMillis()-lastAttackTimestamp > content.VALUE_ATTACK_SPEED){
                lastAttackTimestamp = System.currentTimeMillis();
                boolean left = following.data.x < content.x;

                playScreen.animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, following.data.x, following.data.y));
                playScreen.animations.add(new DamageAnimation(left, true, (int)content.VALUE_ATTACK, following.data.x, following.data.y));
                playScreen.hit_player.play();

                if(content.VALUE_ATTACK >= following.data.current_health){
                    playScreen.killNpc(following.data.id);
                    addExperience(CharacterFactory.getGainedXpForLevel(following.data.level));
                    following = null;
                }else {
                    playScreen.damageNpc("", following.data.id, (int)content.VALUE_ATTACK, true);
                }
            }
        }
    }

    /*
    TODO add player death
     */
    public void getDamage(int fromId, int amount){

        /* get direction of hitter */
        NPC hitter = playScreen.game.npcs.get(fromId);

        if(hitter == null){
            return;
        }

        boolean left = true;
        if(hitter.data.x < content.x || hitter.data.y > content.y){
            left = false;
        }

        Vector3 hitterArrayPos = Utils.getArrayCoordinates(hitter.data.x, hitter.data.y);
        Vector3 playerArrayPos = Utils.getArrayCoordinates(content.x, content.y);

        if(Math.abs(hitterArrayPos.x - playerArrayPos.x) > 1 || Math.abs(hitterArrayPos.y - playerArrayPos.y) > 1){
            return;
        }

        if(content.CURRENT_HEALTH < amount){
            content.CURRENT_HEALTH = 0;
        }else {
            content.CURRENT_HEALTH -= amount;
        }

        playScreen.animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, content.x, content.y));
        playScreen.animations.add(new DamageAnimation(left, false, amount, content.x, content.y));
        playScreen.hit_enemy.play();

        playScreen.game.updateServer_Health(this);
    }

    public boolean checkIfPointIsEndpointOfCurrentPath(Vector3 v){
        if(pathToWalk.pathPoints.size() == 0){
            return false;
        }
        if(v.x*Utils.TILEWIDTH == pathToWalk.pathPoints.getFirst().x && v.y* Utils.TILEHEIGHT == pathToWalk.pathPoints.getFirst().y){
            return true;
        }
        return false;
    }

    public void checkIfSteppedOnBeamableTile(){
        Vector3 arrayCoords = Utils.getArrayCoordinates(content.x, content.y);

        for(BeamableTilePair bt : playScreen.currentMapSection.beamableTiles){
            BeamableTile beamTo = bt.getComplement(playScreen.currentMapSection.identifier, (int)arrayCoords.x, (int)arrayCoords.y);
            if(beamTo != null){
                if(isAbleToBeam) {
                    isAbleToBeam = false;
                    playScreen.animations.add(new ScreenSwitchAnimation(this, beamTo));
                }
                return;
            }
        }
        isAbleToBeam = true;
    }

    public void checkIfSteppedOnItem(){
        for(Item item : playScreen.game.items){
            if(item.data.getOwnerId().equals("") || item.data.getOwnerId().equals(playScreen.game.clientID)){
                Vector3 arrayCoordItem = Utils.getArrayCoordinates(item.data.getX(), item.data.getY());
                Vector3 arrayCoordPlayer = Utils.getArrayCoordinates(content.x, content.y);
                if(arrayCoordItem.x == arrayCoordPlayer.x && arrayCoordItem.y == arrayCoordPlayer.y) {
                    content.putInBag(item.data.getItem_key());
                    playScreen.game.itemPicked(item);
                    playScreen.removeItem(item.data.getItem_key(), item.data.getX(), item.data.getY());
                    break;
                }
            }
        }
    }

    public void updateOldCoordinates() {
        if(content.x % Utils.TILEWIDTH == 0 && content.x % Utils.TILEHEIGHT == 0) {
            oldDirX = (int)content.x/Utils.TILEWIDTH;
            oldDirY = (int)content.y/Utils.TILEHEIGHT;
        }
    }

    public void drawName(SpriteBatch batch) {
        Utils.font10.getData().setScale(0.5f, 0.5f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, content.NAME);
        Utils.font10.draw(batch, content.NAME, content.x+Utils.TILEWIDTH/2-dims.x/2, content.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+5);
    }

    public void drawLevel(SpriteBatch batch){
        Utils.font10.getData().setScale(0.4f, 0.4f);
        Utils.font10.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.font10, "Lvl. "+content.LEVEL);
        Utils.font10.draw(batch, "Lvl. "+content.LEVEL, content.x+Utils.TILEWIDTH/2-dims.x/2, content.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+10);
    }

    private float HEALTHBAR_PADDING = 0.5f;
    private float HEALTHBAR_HEIGHT = 3;
    private float PERC_HEALTH;

    public void drawSmallHealthbar(SpriteBatch batch){

        PERC_HEALTH = (1.0f*content.CURRENT_HEALTH)/(1.0f*content.MAX_HEALTH);

        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), content.x, content.y + Utils.TILEHEIGHT, Utils.TILEWIDTH, HEALTHBAR_HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), content.x+HEALTHBAR_PADDING, content.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, Utils.TILEWIDTH-2*HEALTHBAR_PADDING, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), content.x+HEALTHBAR_PADDING, content.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, (Utils.TILEWIDTH-2*HEALTHBAR_PADDING)*PERC_HEALTH, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);

    }

    public void update(){

        //checkIfDead();

        checkIfSteppedOnBeamableTile();

        checkIfSteppedOnItem();

        if(following != null){
            follow(Utils.getArrayCoordinates(following.data.x, following.data.y), new Vector3(content.x, content.y, 0));
            playScreen.tilemarker.setToEnemy();
            playScreen.tilemarker.setVisible(true);
            playScreen.tilemarker.setEntityX(following.data.x);
            playScreen.tilemarker.setEntityY(following.data.y);
        }

        setMove(walkOnPath());

        /* fix move to */
        if(moveTo != null && xMove == 0 && yMove == 0){
            Vector3 newDirs = new Vector3(0, 0, 0);
            if(content.x < moveTo.x){
                newDirs.x = 1;
            }else if(content.x > moveTo.x){
                newDirs.x = -1;
            }else if(content.y < moveTo.y){
                newDirs.y = 1;
            }else if(content.y > moveTo.y){
                newDirs.y = -1;
            }
            setMove(newDirs);
        }

        move();

        checkIfInAttackRange();
        attack();

        playScreen.gameCam.position.set(content.x+(currentTexture.getRegionWidth()/2), content.y, 0);
    }

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, content.x-5, content.y-4, Utils.TILEWIDTH+10, Utils.TILEHEIGHT+8);
    }

    public void renderAfter(SpriteBatch batch){
        drawSmallHealthbar(batch);
        drawName(batch);
        drawLevel(batch);
    }

}
