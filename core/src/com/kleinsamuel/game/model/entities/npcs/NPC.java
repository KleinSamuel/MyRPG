package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.model.pathfinding.AStarPathFinder;
import com.kleinsamuel.game.model.pathfinding.Path;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.util.Utils;

import java.util.LinkedList;

/**
 * Created by sam on 02.06.17.
 */

public class NPC {

    public NPCData data;

    public final int OBSERVABLE_RANGE = 2;
    public final int OBSERVABLE_FACTOR = 1;
    public int toFollow_id;

    public int xMove;
    public int yMove;

    private int WIDTH;
    private int HEIGHT;
    private float offsetX;
    private float offsetY;

    private SpriteSheet spriteSheet;
    private TextureRegion currentTexture;
    private int currentTextureNum;

    public long animationSpeed;
    private long timestamp;

    public int oldArrayX;
    public int oldArrayY;

    /* only used on server side */
    public Path pathToWalk;
    private AStarPathFinder pathFinder;

    public NPC(SpriteSheet spriteSheet, NPCData data){

        this.spriteSheet =  spriteSheet;
        this.currentTexture = spriteSheet.getTextureRegion(0, 0);
        this.data = data;

        this.pathToWalk = new Path();
        this.pathFinder = new AStarPathFinder(null, null);

        this.xMove = 0;
        this.yMove = 0;
        this.WIDTH = Utils.TILEWIDTH;
        this.HEIGHT = Utils.TILEHEIGHT;
        this.animationSpeed = 500;
        currentTextureNum = 0;
        timestamp = System.currentTimeMillis();
        Vector3 offset = computePositionOffset();
        offsetX = offset.x;
        offsetY = offset.y;
    }

    public void setMove(Vector3 p){
        xMove = (int)p.x;
        yMove = (int)p.y;
    }

    public void move() {
        data.x += xMove*data.speed;
        data.y += yMove*data.speed;
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

        if(data.x == pointOnMap.x && data.y == pointOnMap.y) {
            return null;
        }

        int xMove = 0;
        int yMove = 0;

        if(data.x != pointOnMap.x) {
            if(data.x < pointOnMap.x) {
                xMove = 1;
            }else if(data.x > pointOnMap.x) {
                xMove = -1;
            }
            return new Vector3(xMove, yMove, 0);
        }

        if(data.y < pointOnMap.y) {
            yMove = 1;
        }else if(data.y > pointOnMap.y) {
            yMove = -1;
        }

        return new Vector3(xMove, yMove, 0);
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

   /* public void updateOldCoordinates() {
        if(data.x % Utils.TILEWIDTH == 0 && data.x % Utils.TILEHEIGHT == 0) {
            oldDirX = (int)data.x/Utils.TILEWIDTH;
            oldDirY = (int)data.y/Utils.TILEHEIGHT;
        }
    }*/

    /**
     * Compute offset if image should be displayed larger or smaller than tile.
     *
     * @return Vector3 x = xOffset, y = yOffset
     */
    public Vector3 computePositionOffset(){
        return new Vector3((WIDTH-Utils.TILEWIDTH)/2, (HEIGHT-Utils.TILEHEIGHT)/2, 0);
    }

    /**
     * Set the animation speed.
     * Time in milliseconds between transition of images.
     *
     * @param speed
     */
    public void setAnimationSpeed(long speed){
        this.animationSpeed = speed;
    }

    /**
     * Change size of image which will be displayed with the new size.
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height){
        WIDTH = width;
        HEIGHT = height;
        Vector3 offset = computePositionOffset();
        offsetX = offset.x;
        offsetY = offset.y;
    }

    /**
     * Increases the current texture number to display the next image for animation.
     * If maximum number is reached it return 0 to start from the beginning;
     *
     * @return int next texture number
     */
    public int increaseTextureNum(){
        return (currentTextureNum == spriteSheet.FRAME_COLS-1) ? 0 : ++currentTextureNum;
    }

    /**
     * Sets the next image as current image for animation effect.
     * Only every x millis as specified in variable "animationSpeed"
     */
    public void setNextTexture(){
        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp - timestamp > animationSpeed){
            timestamp = currentTimestamp;
            currentTextureNum = increaseTextureNum();
            currentTexture = spriteSheet.getTextureRegion(0,  currentTextureNum);
        }
    }

    public void update(){

        if(data.moveTo != null) {
            Vector3 p = walkToPoint(data.moveTo);
            if (p != null) {
                setMove(p);
            } else {
                setMove(new Vector3(0, 0, 0));
            }
            move();
        }

        setNextTexture();
    }

    public void render(SpriteBatch batch){
        batch.draw(currentTexture, data.x-offsetX, data.y-offsetY, WIDTH, HEIGHT);
    }

    public void renderAfter(SpriteBatch batch){
        // TODO draw name, level and health
    }

}
