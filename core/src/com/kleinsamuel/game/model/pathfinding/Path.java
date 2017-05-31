package com.kleinsamuel.game.model.pathfinding;

import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;

/**
 * Created by sam on 30.05.17.
 */

public class Path {

    public LinkedList<Vector3> pathPoints;

    public Path() {
        this.pathPoints = new LinkedList();
    }

    public void removeAllPointsExceptForCurrent() {
        if(pathPoints.size() > 0) {
            Vector3 retain = pathPoints.getLast();
            pathPoints.clear();
            pathPoints.add(retain);
        }
    }
}
