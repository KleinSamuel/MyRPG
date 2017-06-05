package com.kleinsamuel.game.model.entities;

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
import com.kleinsamuel.game.model.data.CharacterFactory;
import com.kleinsamuel.game.model.data.UserContent;
import com.kleinsamuel.game.model.entities.npcs.NPC;
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

    private int damage;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;

    public Path pathToWalk;
    private AStarPathFinder pathFinder;

    public Vector3 oldMoveTo;
    public Vector3 moveTo;

    public NPC following;
    public boolean isAttacking;
    public long attackSpeed = 500;
    public long lastAttackTimestamp;

    public Player(PlayScreen playScreen, SpriteSheet spriteSheet){

        pathToWalk = new Path();
        xMove = 0;
        yMove = 0;
        pathFinder = new AStarPathFinder(playScreen.mapRepresentation.walkableTiles, playScreen.mapRepresentation.map2D, this);

        this.playScreen = playScreen;
        this.spriteSheet = spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 1);

        loadContent();

        damage = 10;

        if(content.id == -1){
            DebugMessageFactory.printInfoMessage("FIRST STARTUP!");
        }else{
            DebugMessageFactory.printInfoMessage("NOT FIRST STARTUP!");
        }

        DebugMessageFactory.printInfoMessage("BAG SIZE: "+content.getBag().size());

    }

    private void loadContent() {
        this.content = UserContent.readFromFile();
    }

    public void checkIfDead(){
        if(content.current_health <= 0){
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

                if(directlyAfter) {
                    directlyAfter = false;
                }

            } else {

                directlyAfter = true;

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
        if(content.experience+amount >= CharacterFactory.getNeededXpForLevel(content.level)){
            content.level += 1;
            content.experience = 0;
        }else{
            content.experience += amount;
        }
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

            if(System.currentTimeMillis()-lastAttackTimestamp > attackSpeed){
                lastAttackTimestamp = System.currentTimeMillis();
                boolean left = following.data.x < content.x;

                if(damage >= following.data.current_health){
                    playScreen.killNpc(following.data.id);
                    addExperience(CharacterFactory.getGainedXpForLevel(following.data.level));
                    following = null;
                    return;
                }else {
                    playScreen.damageNpc(following.data.id, damage);
                }

                playScreen.animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, following.data.x, following.data.y));
                playScreen.animations.add(new DamageAnimation(left, true, damage, following.data.x, following.data.y));
            }
        }
    }

    /*
    TODO add player death
     */
    public void getDamage(int amount){
        if(content.current_health < amount){
            content.current_health = 0;
        }else {
            content.current_health -= amount;
        }
        playScreen.animations.add(new EffectAnimation(AnimationFactory.getSpriteSheet(AnimationEnum.SLASH_SINGLE), 150, content.x, content.y));
        playScreen.animations.add(new DamageAnimation(false, true, amount, content.x, content.y));
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



    public void updateOldCoordinates() {
        if(content.x % Utils.TILEWIDTH == 0 && content.x % Utils.TILEHEIGHT == 0) {
            oldDirX = (int)content.x/Utils.TILEWIDTH;
            oldDirY = (int)content.y/Utils.TILEHEIGHT;
        }
    }

    public void drawName(SpriteBatch batch) {
        Utils.testFont.getData().setScale(0.4f, 0.3f);
        Utils.testFont.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.testFont, content.name);
        Utils.testFont.draw(batch, content.name, content.x+Utils.TILEWIDTH/2-dims.x/2, content.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+5);
    }

    public void drawLevel(SpriteBatch batch){
        Utils.testFont.getData().setScale(0.3f, 0.2f);
        Utils.testFont.setColor(Color.BLACK);
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.testFont, "Lvl. "+content.level);
        Utils.testFont.draw(batch, "Lvl. "+content.level, content.x+Utils.TILEWIDTH/2-dims.x/2, content.y+Utils.TILEHEIGHT+HEALTHBAR_HEIGHT+10);
    }

    private float HEALTHBAR_PADDING = 0.5f;
    private float HEALTHBAR_HEIGHT = 3;
    private float PERC_HEALTH;

    public void drawSmallHealthbar(SpriteBatch batch){

        PERC_HEALTH = (1.0f*content.current_health)/(1.0f*content.health);

        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), content.x, content.y + Utils.TILEHEIGHT, Utils.TILEWIDTH, HEALTHBAR_HEIGHT);
        batch.draw(Assets.manager.get(Assets.rectangle_gray, Texture.class), content.x+HEALTHBAR_PADDING, content.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, Utils.TILEWIDTH-2*HEALTHBAR_PADDING, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);
        batch.draw(Assets.manager.get(Assets.rectangle_red, Texture.class), content.x+HEALTHBAR_PADDING, content.y+Utils.TILEHEIGHT+HEALTHBAR_PADDING, (Utils.TILEWIDTH-2*HEALTHBAR_PADDING)*PERC_HEALTH, HEALTHBAR_HEIGHT-2*HEALTHBAR_PADDING);

    }

    public void update(){

        //checkIfDead();

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

        updateOldCoordinates();

        playScreen.gameCam.position.set(content.x+(currentTexture.getRegionWidth()/2), content.y, 0);
    }

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, content.x, content.y, Utils.TILEWIDTH, Utils.TILEHEIGHT);
    }

    public void renderAfter(SpriteBatch batch){
        drawSmallHealthbar(batch);
        drawName(batch);
        drawLevel(batch);
    }

}
